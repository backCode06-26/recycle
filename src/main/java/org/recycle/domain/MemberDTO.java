package org.recycle.domain;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Data
public class MemberDTO {

    @NotBlank(message = "이메일을 입력하세요")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요")
    private String password;

    public void encryptPassword() {
        try {
            // SHA-256 해시 함수를 사용하여 비밀번호를 암호화합니다.
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(this.password.getBytes(StandardCharsets.UTF_8));

            // 암호화된 해시를 16진수로 변환하여 비밀번호로 설정합니다.
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            this.password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(); // 암호화 알고리즘을 찾을 수 없는 경우 에러를 출력합니다.
        }
    }
}
