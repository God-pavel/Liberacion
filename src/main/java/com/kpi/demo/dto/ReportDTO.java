package com.kpi.demo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReportDTO {
    private LocalDateTime datetime;
    private long reportee;
    private String comment;
    private String image;
}
