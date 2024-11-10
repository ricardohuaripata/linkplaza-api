package com.linkplaza.dto;

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
public class UpdatePageDto {
    @Size(max = 64)
    @Pattern(regexp = "^[a-zA-Z0-9_.]+$", message = "URL may only contain letters, numbers, underscores and periods.")
    private String url;

    @Size(max = 64)
    private String title;

    @Size(max = 1024)
    private String bio;

    @Size(max = 6400)
    private String pictureUrl;
}
