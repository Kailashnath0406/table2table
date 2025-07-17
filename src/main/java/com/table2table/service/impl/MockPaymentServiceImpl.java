package com.table2table.service.impl;

import com.table2table.service.MockPaymentService;
import org.springframework.stereotype.Service;

@Service
public class MockPaymentServiceImpl implements MockPaymentService {

    @Override
    public boolean processPayment(Long userId, Double amount) {
        // Logically simulate a payment success (could fail too)
        System.out.println("Processed mock payment of ₹" + amount + " for user ID: " + userId);
        return true; // Always succeeds in mock
    }

    @Override
    public boolean initiateRefund(Long userId, Double amount) {
        System.out.println("Initiated mock refund of ₹" + amount + " to user ID: " + userId);
        return true;
    }
}
