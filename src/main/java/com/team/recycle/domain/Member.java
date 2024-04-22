package com.recycle.domain;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Member {
    Long id;
    String email;
    String password;
    Timestamp joinDate;
}
