package com.prikshit.bfhl.controller;

import com.prikshit.bfhl.model.*;
import com.prikshit.bfhl.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BfhlController {

    private final MathService mathService;
    private final AIService aiService;

    @Value("${official.email}")
    private String email;

    public BfhlController(MathService mathService, AIService aiService) {
        this.mathService = mathService;
        this.aiService = aiService;
    }

    // HEALTH ENDPOINT
    @GetMapping("/health")
    public ApiResponse health() {
        return new ApiResponse(true, email, null);
    }

    // MAIN ENDPOINT
    @PostMapping("/bfhl")
    public ResponseEntity<?> process(@RequestBody(required = false) BfhlRequest req) {

        // Missing body
        if (req == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse());
        }

        int count = 0;
        if (req.fibonacci != null) count++;
        if (req.prime != null) count++;
        if (req.lcm != null) count++;
        if (req.hcf != null) count++;
        if (req.AI != null) count++;

        // Exactly one key required
        if (count != 1) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse());
        }

        Object result;

        try {
            if (req.fibonacci != null)
                result = mathService.fibonacci(req.fibonacci);

            else if (req.prime != null) {
                if (req.prime.isEmpty())
                    return ResponseEntity.badRequest().body(new ErrorResponse());
                result = mathService.primes(req.prime);
            }

            else if (req.lcm != null) {
                if (req.lcm.isEmpty())
                    return ResponseEntity.badRequest().body(new ErrorResponse());
                result = mathService.lcm(req.lcm);
            }

            else if (req.hcf != null) {
                if (req.hcf.isEmpty())
                    return ResponseEntity.badRequest().body(new ErrorResponse());
                result = mathService.hcf(req.hcf);
            }

            else
                result = aiService.askAI(req.AI);

        } catch (IllegalArgumentException e) {
            // Validation errors
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse());

        } catch (Exception e) {
            // Unexpected errors
            return ResponseEntity
                    .status(500)
                    .body(new ErrorResponse());
        }

        return ResponseEntity.ok(
                new ApiResponse(true, email, result)
        );
    }
}
