package com.example.modules.system.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ConfirmDto {
    private String uuid;
    private String username;
    private String password;
    private String role;
}