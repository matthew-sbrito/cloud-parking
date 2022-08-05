package com.techsoft.parking.authentication.dto.form;

import lombok.Data;

@Data
public class ApplicationUserCreateDTO {
    private String fullName;
    private String username;
    private String email;
    private String secretPassword;
}
