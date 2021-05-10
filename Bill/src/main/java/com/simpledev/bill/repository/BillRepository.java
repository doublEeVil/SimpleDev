package com.simpledev.bill.repository;

import com.simpledev.bill.entity.Bill;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BillRepository {
    // List<Bill> findBill(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("keyword") String keyword);

    List<Bill> findBill(Map<String, Object> params);

    void save(Bill bill);

    void update(Bill bill);

    Bill getOne(long id);
}