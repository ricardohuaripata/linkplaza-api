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
public class ChangeEmailDto {
    @NotBlank
    @Size(max = 256)
    @Pattern(regexp = AppConstants.EMAIL_PATTERN, message = "Invalid email address.")
    private String email;
}
