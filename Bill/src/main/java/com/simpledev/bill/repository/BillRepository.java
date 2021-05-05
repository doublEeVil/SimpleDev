package com.simpledev.bill.repository;

import com.simpledev.bill.entity.Bill;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BillRepository {
    List<Bill> findBill(String startDate, String endDate, String keyword);
}