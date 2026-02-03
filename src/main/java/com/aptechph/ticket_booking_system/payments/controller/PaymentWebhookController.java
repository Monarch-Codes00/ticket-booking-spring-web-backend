package com.aptechph.ticket_booking_system.payments.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.InputStream;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/api/webhook")
public class PaymentWebhookController {

    private static final String PAYSTACK_SECRET_KEY = "your-paystack-secret-key"; // Replace with your Paystack secret key

    private final ObjectMapper objectMapper;

    public PaymentWebhookController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping("/paystack")
    public ResponseEntity<String> handlePaystackWebhook(
            @RequestBody String payload,
            @RequestHeader("x-paystack-signature") String signature) {
        try {
            // Verify the signature
            if (!verifyPaystackSignature(payload, signature)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid signature");
            }

            // Parse the payload
            JsonNode event = objectMapper.readTree(payload);
            String eventType = event.get("event").asText();

            // Handle the event
            switch (eventType) {
                case "charge.success":
                    handleSuccessfulCharge(event);
                    break;
                default:
                    System.out.println("Unhandled event type: " + eventType);
            }

            return ResponseEntity.ok("Event received");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing webhook");
        }
    }

    private boolean verifyPaystackSignature(String payload, String signature) {
        try {
            Mac hasher = Mac.getInstance("HmacSHA512");
            hasher.init(new SecretKeySpec(PAYSTACK_SECRET_KEY.getBytes(), "sk_test_675b1ef14256c493b76e4316f6a515ca"));
            byte[] hash = hasher.doFinal(payload.getBytes());
            String computedSignature = Base64.getEncoder().encodeToString(hash);
            return computedSignature.equals(signature);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void handleSuccessfulCharge(JsonNode event) {
        // Extract relevant data from the event
        JsonNode data = event.get("data");
        String reference = data.get("reference").asText();
        String status = data.get("status").asText();
        double amount = data.get("amount").asDouble() / 100; // Convert from kobo to naira

        // Log or process the payment
        System.out.println("Payment successful: Reference=" + reference + ", Amount=" + amount + ", Status=" + status);

      
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(@RequestBody Map<String, String> request) {
        String reference = request.get("reference");
        try {
            // Call Paystack's verify endpoint
            String url = "https://api.paystack.co/transaction/verify/" + reference;
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "sk_test_675b1ef14256c493b76e4316f6a515ca6aa50eee"); // Replace with your secret key

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // Parse the response
                InputStream responseStream = connection.getInputStream();
                String response = new String(responseStream.readAllBytes());
                System.out.println("Verification successful: " + response);
                return ResponseEntity.ok("Payment verified successfully");
            } else {
                System.out.println("Verification failed with response code: " + responseCode);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error verifying payment");
        }
    }
}