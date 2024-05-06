package org.recycle.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
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
