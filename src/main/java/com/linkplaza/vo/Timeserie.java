package com.linkplaza.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Timeserie {
    private Long views;
    private Long uniqueViews;
    private String date;
}
