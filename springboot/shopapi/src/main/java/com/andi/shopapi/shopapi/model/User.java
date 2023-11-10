package com.andi.shopapi.shopapi.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class User {

    @Id
    private String id;

    @NotBlank(message = "UserName is mandatory")
    private String userName;

    @NotBlank(message = "FirstName is mandatory")
    private String firstName;
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    private String email;

    private Boolean isRegistered;
    private Instant createdDate;
    private Instant registeredDate;
    private Instant lastActivitiesDate;
    private Instant updatedDate;
    private UserRole userRole;
    private UserStatus userStatus;
}
