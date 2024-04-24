package com.team.recycle.domain;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Member {
    private Long id;
    private String email;
    private String password;
    private Timestamp joinDate;
}
