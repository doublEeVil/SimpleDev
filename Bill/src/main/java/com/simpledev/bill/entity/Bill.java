package com.simpledev.bill.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "com.simpledev.bill")
@Data
public class Bill implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "date_time")
    private LocalDateTime dateTime;  // 时间
    @Column(name = "rcv_type")
    private String rcvType; // 收支类型 支出和收入
    @Column(name = "amt")
    private double amt;      // 金额
    @Column(name = "use_type")
    private String useType; // 使用类型
    @Column(name = "remark")
    private String remark;  // 额外信息
}
