package org.recycle.domain;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class Game {
    private Long id;
    @Email
    private String email;
    private int gameScore;
}
