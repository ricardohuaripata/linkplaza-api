package com.linkplaza.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
public class ClaimUsernameDto {
    @NotBlank
    @Size(min = 2, max = 64)
    @Pattern(regexp = "^[a-zA-Z0-9_.]+$", message = "Usernames may only contain letters, numbers, underscores and periods.")
    private String username;
}
