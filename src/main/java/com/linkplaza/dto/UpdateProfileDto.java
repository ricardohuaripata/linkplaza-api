package com.linkplaza.dto;

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
public class UpdateProfileDto {
    @Size(max = 64)
    private String profileTitle;

    @Size(max = 1024)
    private String profileBio;

    @Size(max = 6400)
    private String profilePictureUrl;
}
