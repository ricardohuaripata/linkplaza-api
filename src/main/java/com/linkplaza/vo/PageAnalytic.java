package com.linkplaza.vo;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageAnalytic {
    private Long totalViews;
    private Long totalUniqueViews;
    private Map<String, VisitCount> timeseries;

}
