package com.lagou.eduorderboot.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "pay_order") //支付订单信息表
public class PayOrder implements Serializable {
    private static final long serialVersionUID = 777308790778683330L;

    /**
     * 主键
     */
    @Id
    private Long id;
    private String order_no;
    private String user_id;
    private String product_id;
    private String product_name;
    private Double amount;
    private Integer count;
    private String currency;
    private String channel;
    private Integer status;
    private Integer channel_status;
    private Integer order_type;
    private Integer source;
    private String client_ip;
    private String buy_id;
    private String out_trade_no;
    private Date created_time;
    private Date updated_time;
    private Date pay_time;
    private String extra;
    private String goods_order_no;
    private Integer platform;
    private Integer wx_type;
}
