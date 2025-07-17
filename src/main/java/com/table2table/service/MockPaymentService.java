package com.table2table.service;


public interface MockPaymentService {
    boolean processPayment(Long userId, Double amount);
    boolean initiateRefund(Long userId, Double amount);
}
