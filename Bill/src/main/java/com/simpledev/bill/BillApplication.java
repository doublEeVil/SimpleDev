package com.simpledev.bill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(scanBasePackages = "com.simpledev.bill") //异常处理必须要这个
@SpringBootApplication() //异常处理必须要这个
@MapperScan("com.simpledev.bill.repository")
public class BillApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillApplication.class, args);
    }

}
