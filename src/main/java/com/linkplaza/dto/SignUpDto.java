package com.linkplaza.dto;

import javax.validation.constraints.NotBlank;

import com.linkplaza.annotation.ValidEmail;
import com.linkplaza.annotation.ValidPassword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    @NotBlank
    @ValidEmail
    private String email;

    @NotBlank
    @ValidPassword
    private String password;
}
