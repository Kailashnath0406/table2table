package com.table2table.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String flatNumber;
    private String floor;
    private String role;
}
