package com.team.recycle.domain.dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class UserDAO {
    private String email;
    private Date joinData;
    private int gameScore;
}
