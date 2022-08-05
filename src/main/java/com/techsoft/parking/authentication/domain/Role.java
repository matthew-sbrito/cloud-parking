package com.techsoft.parking.authentication.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class Role implements GrantedAuthority {
    private static final long serialVersionUID = -6469391897738445679L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String authority;
}
