package com.lagou.eduadboot.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="promotion_ad")
public class PromotionAd implements Serializable {
    private static final long serialVersionUID = -29054335318173039L;
    @Id
    private Integer  id;
    /**
     * 广告名
     */
    private String name;
    private Integer spaceId;
    private String keyword;
    private String htmlContent;
    private String text;
    private String link;
    private Date startTime;
    private Date endTime;
    private Date createTime;
    private Date updateTime;
    private Integer status ;
    private Integer priority;
    private String img;
}
