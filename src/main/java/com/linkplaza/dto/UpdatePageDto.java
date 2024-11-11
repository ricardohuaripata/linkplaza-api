package com.linkplaza.dto;

import javax.validation.constraints.Size;

import com.linkplaza.annotation.NoDotAtEdges;
import com.linkplaza.annotation.ValidCharacterPattern;

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
    @Size(min = 3, max = 64)
    @ValidCharacterPattern
    @NoDotAtEdges
    private String url;

    @Size(max = 64)
    private String title;

    @Size(max = 1024)
    private String bio;

    @Size(max = 6400)
    private String pictureUrl;
}
