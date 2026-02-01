package org.authentication.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.authentication.domain.enums.UserRole;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "T_USER")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USER", length = 16)
    private Long idUser;

    @Column(name = "NAME", length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false, length = 20)
    private UserRole role;

    @Email
    @Column(name = "EMAIL", unique = true, length = 100, nullable = false)
    private String email;

    @Column(name = "PASSWORD", length = 60, nullable = false)
    private String password;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    public User() {}

    public User(String name, UserRole role, String email, String password) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
