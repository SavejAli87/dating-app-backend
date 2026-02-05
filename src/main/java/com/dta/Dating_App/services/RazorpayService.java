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

    public Order CreateOrder(Long userId, String plan, int amount) throws Exception {

        RazorpayClient client  = new RazorpayClient(key, secret);

        JSONObject options = new JSONObject();
        options.put("amount", amount * 100);
        options.put("currency", "INR");
        options.put("receipt", "txn_" + System.currentTimeMillis());

        Order order = client.orders.create(options);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        // save payment row
        Payment payment = Payment.builder()
                .razorpayOrderId(order.get("id"))
                .user(user)
                .plan(plan)
                .amount(amount)
                .status("Created")
                .createdAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        return order;
    }

 // markSuccess

    @Transactional
    public void markSuccess(String orderId,
                            String paymentId,
                            String signature) throws Exception {

        //  Verify payment
        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", orderId);
        options.put("razorpay_payment_id", paymentId);
        options.put("razorpay_signature", signature);

        boolean isValid = Utils.verifyPaymentSignature(options, secret);

        if (!isValid) {
            throw new RuntimeException("Invalid Razorpay signature");
        }

        Payment payment = paymentRepository
                .findByRazorpayOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        //  Prevent double processing
        if ("SUCCESS".equals(payment.getStatus())) {
            return;
        }

        payment.setStatus("SUCCESS");
        payment.setRazorpayPaymentId(paymentId);

        paymentRepository.save(payment);

        subscriptionService.activate(
                payment.getUser().getId(),
                payment.getPlan()
        );
    }

    // handleWebhook

    public void handleWebhook(String payload, String signature) throws Exception {

        boolean isValid = Utils.verifyWebhookSignature(
                payload,
                signature,
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

            markSuccess(orderId, paymentId, signature);
        }
    }



}
