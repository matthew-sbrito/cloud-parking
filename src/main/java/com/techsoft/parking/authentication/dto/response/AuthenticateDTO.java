package com.techsoft.parking.authentication.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.Date;

@Data
public class AuthenticateDTO {
    ApplicationUserDTO user;
    String token;
    int expireIn;
    Date createAt;

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
