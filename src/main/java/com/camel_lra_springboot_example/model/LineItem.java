package com.camel_lra_springboot_example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineItem {

    private String firstName;

    private String lastName;

    private Integer confirmationNumber;

    private String tripStatus;

    private Integer lengthOfStay;

    private Double totalPrice;
}
