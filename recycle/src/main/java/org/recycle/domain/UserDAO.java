package org.recycle.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDAO {
    private String email;
    private Timestamp joinDate;
    private int gameScore;
}
