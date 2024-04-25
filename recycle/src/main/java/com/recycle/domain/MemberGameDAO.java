package com.recycle.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MemberGameDAO {
    String email;
    Timestamp joinDate;
    int gameScore;
}
