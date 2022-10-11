package com.javachinna.repo;

import com.javachinna.model.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Payment,Long> {
    List<Payment> findPaymentByCustomerId(String id);

}
