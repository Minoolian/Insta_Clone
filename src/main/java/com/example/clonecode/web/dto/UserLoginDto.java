package com.example.clonecode.web.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserLoginDto {

    @Size(min=2, max=100, message = "이메일 글자수를 맞춰 작성해주세요")
    @NotBlank(message = "이메일을 입력해 주세요")
    @Email(message = "이메일 형식에 맞지 않습니다")
    private String email;

    @NotBlank
    private String password;

    private String phone;

    @Size(min=1, max=30)
    @NotBlank
    private String name;
}
