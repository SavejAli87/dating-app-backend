package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.Payment;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.PaymentRepository;
import com.dta.Dating_App.repository.UserRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RazorpayService {

    @Value("${razorpay.key}")
    private String key;

    @Value("${razorpay.secret}")
    private String secret;

    private final PaymentRepository paymentRepository;
    private final SubscriptionService subscriptionService;
    private final UserRepository userRepository;

    // ✅ CREATE ORDER
    public Order createOrder(Long userId, String plan, int amount) throws Exception {

        RazorpayClient client = new RazorpayClient(key, secret);

        JSONObject options = new JSONObject();
        options.put("amount", amount * 100); // paisa
        options.put("currency", "INR");
        options.put("receipt", "txn_" + System.currentTimeMillis());

        Order order = client.orders.create(options);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //  Save payment in DB
        Payment payment = Payment.builder()
                .razorpayOrderId(order.get("id"))
                .user(user)
                .plan(plan)
                .amount(amount)
                .status("CREATED") // FIX: consistent uppercase
                .createdAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        return order;
    }

    //  VERIFY + MARK SUCCESS
    @Transactional
    public void markSuccess(String orderId,
                            String paymentId,
                            String signature) throws Exception {

        //  STEP 1: Verify Signature
        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", orderId);
        options.put("razorpay_payment_id", paymentId);
        options.put("razorpay_signature", signature);

        boolean isValid = Utils.verifyPaymentSignature(options, secret);

        if (!isValid) {
            throw new RuntimeException("Invalid Razorpay signature");
        }

        //  STEP 2: Fetch Payment
        Payment payment = paymentRepository
                .findByRazorpayOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        //  STEP 3: Prevent duplicate execution
        if ("SUCCESS".equalsIgnoreCase(payment.getStatus())) {
            return;
        }

        //  STEP 4: Update Payment
        payment.setStatus("SUCCESS");
        payment.setRazorpayPaymentId(paymentId);
        payment.setUpdatedAt(LocalDateTime.now()); // ADD THIS FIELD

        paymentRepository.save(payment);

        //  STEP 5: Activate Subscription
        subscriptionService.activatePlan(
                payment.getUser().getUserId(),
                payment.getPlan()
        );
    }

    //  HANDLE FAILURE (NEW ADD - IMPORTANT)
    @Transactional
    public void markFailed(String orderId) {

        Payment payment = paymentRepository
                .findByRazorpayOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus("FAILED");
        payment.setUpdatedAt(LocalDateTime.now());

        paymentRepository.save(payment);
    }

    // ✅ WEBHOOK HANDLER (FIXED)
    public void handleWebhook(String payload, String webhookSignature) throws Exception {

        boolean isValid = Utils.verifyWebhookSignature(
                payload,
                webhookSignature,
                secret
        );

        if (!isValid) {
            throw new RuntimeException("Invalid webhook signature");
        }

        JSONObject json = new JSONObject(payload);
        String event = json.getString("event");

        if ("payment.captured".equals(event)) {

            JSONObject paymentEntity = json
                    .getJSONObject("payload")
                    .getJSONObject("payment")
                    .getJSONObject("entity");

            String orderId = paymentEntity.getString("order_id");
            String paymentId = paymentEntity.getString("id");

            // ⚠️ IMPORTANT: webhook me signature use nahi hota markSuccess ke liye
            // already verified above
            markSuccess(orderId, paymentId, json.getString("event"));
        }

        // 🔥 HANDLE FAILED PAYMENT
        if ("payment.failed".equals(event)) {

            JSONObject paymentEntity = json
                    .getJSONObject("payload")
                    .getJSONObject("payment")
                    .getJSONObject("entity");

            String orderId = paymentEntity.getString("order_id");

            markFailed(orderId);
        }
    }
}