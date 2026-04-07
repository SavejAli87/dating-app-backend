package com.dta.Dating_App.controller;

import com.dta.Dating_App.services.RazorpayService;
import com.razorpay.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/razorpay")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RazorpayController {

    private final RazorpayService razorpayService;

    // ✅ CREATE ORDER
    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(
            @RequestParam Long userId,
            @RequestParam String plan
    ) throws Exception {

        // 🔥 Better approach (dynamic instead of hardcoding)
        int amount = switch (plan.toUpperCase()) {
            case "GOLD" -> 199;
            case "PREMIUM" -> 499;
            case "FREE" -> 0;
            default -> throw new RuntimeException("Invalid plan");
        };

        // ✅ FIX: method name corrected
        Order order = razorpayService.createOrder(userId, plan.toUpperCase(), amount);

        // 🔥 Return clean JSON instead of toString()
        return ResponseEntity.ok(new Object() {
            public final String orderId = order.get("id");
            public final int amountValue = order.get("amount");
            public final String currency = order.get("currency");
        });
    }

    // ✅ VERIFY PAYMENT (Frontend success call)
    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(
            @RequestParam String orderId,
            @RequestParam String paymentId,
            @RequestParam String signature
    ) throws Exception {

        razorpayService.markSuccess(orderId, paymentId, signature);

        return ResponseEntity.ok("Payment Verified & Subscription Activated");
    }

    // ✅ WEBHOOK (Backup verification)
    @PostMapping("/webhook")
    public ResponseEntity<String> webhook(
            @RequestBody String payload,
            @RequestHeader("X-Razorpay-Signature") String signature
    ) throws Exception {

        razorpayService.handleWebhook(payload, signature);

        return ResponseEntity.ok("Webhook processed");
    }
}