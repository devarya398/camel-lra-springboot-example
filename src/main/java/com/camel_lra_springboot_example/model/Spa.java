package com.camel_lra_springboot_example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Spa {

  private String spaType;

  private Double totalPrice;

  private String status;
}
