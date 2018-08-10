package com.school.biz.domain.entity.customer;

import java.util.Date;

import lombok.Data;

@Data
public class Customer {
    private Long id;

    private String openId;

    private String nickname;

    private boolean isSubscribed;

    private Date subscribedTime;

    private String name;

    private String phone;

    private String addr;

    private String email;

    private String avatar;

    private String sex;

    private Integer version;

    private String creator;

    private Date createdTime;

    private String modifier;

    private Date modifiedTime;

    private Boolean isDeleted;
}