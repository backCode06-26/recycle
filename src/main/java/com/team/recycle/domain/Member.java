package com.team.recycle.domain;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class Member {
    private Long id;
    private String email;
    private String password;
    private Date joinDate;
}
