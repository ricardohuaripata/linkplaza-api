package com.linkplaza.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.linkplaza.common.AppConstants;

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
public class ResetPasswordDto {

    @NotBlank
    private String token;

    @NotBlank
    @Size(max = 256)
    @Pattern(regexp = AppConstants.PASSWORD_PATTERN, message = "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character.")
    private String password;

}
