package com.simpledev.bill.service;

import com.simpledev.bill.entity.Bill;
import com.simpledev.bill.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    DateTimeFormatter dayStartFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter dayEndFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59:59");

    public List<Bill> query(LocalDate startDate, LocalDate endDate, String keyword) {
//        List<Bill> billList = billRepository.findAll();
//        return billList.stream().filter(x -> test(x, startDate, endDate, keyword)).sorted((b1,b2) -> b1.getDateTime().isAfter(b2.getDateTime()) ? -1 : 1)
//                .collect(Collectors.toList());
//        if (StringUtils.isEmpty(keyword)) {
//            return billRepository.findBill(startDate, endDate);
//        }
//        return null;
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate.format(dayStartFormatter));
        params.put("endDate", endDate.format(dayEndFormatter));
        params.put("keyword", keyword);

        //return billRepository.findBill(startDate.format(formatter), endDate.format(formatter), keyword);
        return billRepository.findBill(params);
    }

    private static boolean test(Bill x, LocalDate startDate, LocalDate endDate, String keyword) {
        boolean flag =
                (x.getDateTime().toLocalDate().isEqual(startDate) || x.getDateTime().toLocalDate().isAfter(startDate))
                    &&
                (x.getDateTime().toLocalDate().isEqual(endDate) || x.getDateTime().toLocalDate().isBefore(endDate));
        if (flag && !StringUtils.isEmpty(keyword)) {
            if (x.getUseType().contains(keyword)) return true;
            if (!StringUtils.isEmpty(x.getRemark())) {
                if (x.getRemark().contains(keyword)) return true;
            }
            return false;
        }
        return flag;
    }

    public Bill save(LocalDateTime dateTime, String rcvType, double amt, String useType, String remark) {
        Bill bill = new Bill();
        LocalDateTime now = LocalDateTime.now();
        bill.setCreateAt(now);
        bill.setUpdateAt(now);
        bill.setDateTime(dateTime);
        bill.setRcvType(rcvType);
        bill.setAmt(amt);
        bill.setUseType(useType);
        bill.setRemark(remark);
        billRepository.save(bill);
        return bill;
    }

    public Bill update(LocalDateTime dateTime, String rcvType, double amt, String useType, String remark, long id) {
        Bill bill = billRepository.getOne(id);
        LocalDateTime now = LocalDateTime.now();
        bill.setCreateAt(now);
        bill.setUpdateAt(now);
        bill.setDateTime(dateTime);
        bill.setRcvType(rcvType);
        bill.setAmt(amt);
        bill.setUseType(useType);
        bill.setRemark(remark);
        billRepository.update(bill);
        return bill;
    }
}
