package com.andi.shopapi.shopapi.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class OrderUser {

    @Id
    private String id;
    @DBRef
    @NotBlank(message = "UserID is mandatory")
    private User userId;
    private Instant createdDate;
    private Instant updatedDate;

}
