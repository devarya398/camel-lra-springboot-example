package com.camel_lra_springboot_example.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private UUID id;

    private String orderStatus;

    private LineItem lineItem;

    private UUID cartId;

    private UUID guestId;

    private boolean spaIncluded;

    private Spa spa;
}
