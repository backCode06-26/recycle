package com.team.recycle.domain.dto;

import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Data
public class MemberDTO {
    private String email;
    private String password;
}
