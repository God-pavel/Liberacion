package com.kpi.demo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDTO {

    private String username;
    private String password;
    private String login;
    private String avatar;
}