package com.recycle.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDAO {
    String email;
    Timestamp joinDate;
    int gameScore;
}
