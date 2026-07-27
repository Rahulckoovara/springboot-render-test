package com.example.demoPostGre.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data

@Table(name="table_1")
public class person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long mark;
    @NotBlank(message = "Employee name is required")
    @Size(min = 3, max = 30,
            message = "Name should be between 3 and 30 characters")
    private String name;

    @Email(message="Email format should be proper")
    @NotBlank(message = "Email is required")
    private String email;
    //image
    private String imageType;
    private String imageName;
    @Lob
    @Column(columnDefinition = "BYTEA")
    private byte[] imageData;
}
