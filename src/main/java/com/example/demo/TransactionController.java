package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class TransactionController {

    @PostMapping("/api/antifraud/transaction")
    public Map<String, String> validateTransaction(@RequestBody Map<String, Long> transaction) {
        if (transaction.get("amount") == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        long amount = transaction.get("amount");

        if (amount > 1500) {
            return Map.of("result", "PROHIBITED");
        } else if (amount > 200) {
            return Map.of("result", "MANUAL_PROCESSING");
        } else if (amount > 0) {
            return Map.of("result", "ALLOWED");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
