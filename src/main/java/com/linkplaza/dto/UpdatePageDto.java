package com.linkplaza.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.linkplaza.annotation.NoDotAtEdges;
import com.linkplaza.annotation.ValidCharacterPattern;
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

    @Pattern(regexp = AppConstants.HEX_COLOR_PATTERN, message = "Color must be a valid hexadecimal value (e.g. #RRGGBB or #RGB)")
    private String backgroundColor;

    @Pattern(regexp = AppConstants.HEX_COLOR_PATTERN, message = "Color must be a valid hexadecimal value (e.g. #RRGGBB or #RGB)")
    private String fontColor;

    @Pattern(regexp = AppConstants.HEX_COLOR_PATTERN, message = "Color must be a valid hexadecimal value (e.g. #RRGGBB or #RGB)")
    private String buttonBackgroundColor;

    @Pattern(regexp = AppConstants.HEX_COLOR_PATTERN, message = "Color must be a valid hexadecimal value (e.g. #RRGGBB or #RGB)")
    private String buttonFontColor;

    private Boolean buttonRounded;

}
