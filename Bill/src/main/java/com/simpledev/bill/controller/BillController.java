package com.simpledev.bill.controller;

import com.simpledev.bill.entity.Bill;
import com.simpledev.bill.exception.BillException;
import com.simpledev.bill.service.BillService;
import com.simpledev.bill.vo.QueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController("/com/simpledev/bill")
public class BillController {

    @Autowired
    private BillService billService;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    Pattern pattern1 = Pattern.compile("(\\d\\d\\d\\d-\\d\\d-\\d\\d)");
    Pattern pattern2 = Pattern.compile("(\\d\\d\\d\\d-\\d\\d-\\d\\d).*(\\d\\d:\\d\\d:\\d\\d)");

    @RequestMapping("/bill/query")
    public @ResponseBody
    QueryVO query(@RequestParam("startDate") String startDateStr,
                  @RequestParam("endDate") String endDateStr,
                  @RequestParam(value = "keyword", required = false) String keyword) {
        Matcher matcher = pattern1.matcher(startDateStr);
        if (matcher.find()) {
            startDateStr = matcher.group(1);
        }
        matcher = pattern2.matcher(endDateStr);
        if (matcher.find()) {
            endDateStr = matcher.group(1);
        }
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        return new QueryVO(billService.query(startDate, endDate, keyword));
    }

    @RequestMapping("/bill/save")
    public @ResponseBody
    Bill save(@RequestParam("dateTime") String dateTimeStr,
              @RequestParam(value = "rcvType", required = true) String rcvType,
              @RequestParam("amt") double amt,
              @RequestParam("useType") String useType,
              @RequestParam("remark") String remark,
              @RequestParam(value = "id", required = false) Long id) {
        Matcher matcher = pattern2.matcher(dateTimeStr);
        if (matcher.find()) {
            LocalDateTime dateTime = LocalDateTime.parse(matcher.group(1) + " " + matcher.group(2), dateTimeFormatter);
            Instant instant = dateTime.toInstant(ZoneOffset.UTC);
            dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("GMT+08:00"));
            if (id == null) {
                return billService.save(dateTime, rcvType, amt, useType, remark);
            } else {
                return billService.update(dateTime, rcvType, amt, useType, remark, id);
            }
        } else {
            throw new BillException(BillException.ERROR, "");
        }
    }
}
