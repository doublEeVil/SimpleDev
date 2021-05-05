package com.simpledev.bill.vo;

import com.simpledev.bill.constants.BillConstants;
import com.simpledev.bill.entity.Bill;
import lombok.Data;

import java.util.List;

@Data
public class QueryVO {
    private double rcvSum;
    private double useSum;
    private List<Bill> billList;

    public QueryVO(List<Bill> list) {
        for (Bill bill : list) {
            if (bill.getRcvType().equals(BillConstants.RCV_TYPE_RCV)) {
                rcvSum += bill.getAmt();
            } else if (bill.getRcvType().equals(BillConstants.RCV_TYPE_USE)) {
                useSum += bill.getAmt();
            }
        }
        billList = list;
    }
}
