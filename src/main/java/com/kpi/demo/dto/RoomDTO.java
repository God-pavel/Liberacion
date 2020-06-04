package com.kpi.demo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RoomDTO {

    private String name;
    private String avatar;
    private LocalDateTime end;
    private long challenge;
}