package com.techsoft.parking.authentication.dto.response;

import com.techsoft.parking.authentication.domain.ApplicationUser;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class ApplicationUserDTO {
    private UUID id;
    private String fullName;
    private String username;
    private String email;
    private Boolean enabled;
    private List<String> roles;

    public ApplicationUserDTO(ApplicationUser applicationUser) {
        id = applicationUser.getId();
        fullName = applicationUser.getFullName();
        username = applicationUser.getUsername();
        email = applicationUser.getEmail();
        enabled = applicationUser.getEnabled();

        roles = applicationUser.getRolesString();
    }
}
