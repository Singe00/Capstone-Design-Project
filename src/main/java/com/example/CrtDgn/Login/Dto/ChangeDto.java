package com.example.CrtDgn.Login.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeDto {
    private String email;
    private String password;
    private String newPassword;
    private String checkPassword;
}
