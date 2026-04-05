package com.finance.backend.dashboard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3 , message = "Name must contain atleast 3 letters" )
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid Email Format")
    @Column(unique = true)
    private String email;


    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    private Role role;
}