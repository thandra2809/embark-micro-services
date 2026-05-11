package com.ecommerce.user.model;



import lombok.Data;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
public class Address {

    private String street;

    private String city;

    private String state;

    private String country;

    private String zipcode;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
