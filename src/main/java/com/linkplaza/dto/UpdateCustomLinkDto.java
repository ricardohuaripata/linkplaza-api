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
public class UpdateCustomLinkDto {
    @Size(max = 6400)
    private String url;

    @Size(max = 128)
    private String title;

    private Boolean active;
}
