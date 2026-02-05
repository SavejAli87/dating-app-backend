package com.dta.Dating_App.controller;

import com.dta.Dating_App.repository.PaymentRepository;
import com.dta.Dating_App.services.RazorpayService;
import com.razorpay.Order;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/razorpay")
@RequiredArgsConstructor
public class RazorpayController {

    private final RazorpayService razorpayService;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(
            @RequestParam Long userId,
            @RequestParam String plan
    ) throws Exception {

        int amount = switch (plan.toUpperCase()){
            case "GOLD" -> 199;
            case "PREMIUM" -> 499;
            default -> throw new RuntimeException("Invalid plan");


        };

        Order order = razorpayService.CreateOrder(userId, plan, amount);

        return ResponseEntity.ok(order.toString());
    }

    // Webhook
    @PostMapping("/webhook")
    public ResponseEntity<String> webhook(
            @RequestBody String payload,
            @RequestHeader("X-Razorpay-Signature") String signature
    ) throws Exception {

        razorpayService.handleWebhook(payload, signature);

        return ResponseEntity.ok("OK");
    }



}
