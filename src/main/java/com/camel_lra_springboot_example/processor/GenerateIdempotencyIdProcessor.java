package com.camel_lra_springboot_example.processor;

import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.camel_lra_springboot_example.model.Order;
import com.camel_lra_springboot_example.model.OrderStatus;

@Component
public class GenerateIdempotencyIdProcessor implements Processor {

  @Override
  public void process(Exchange exchange) throws Exception {
    exchange.getMessage().setHeader("id", UUID.randomUUID().toString());
    Order order = exchange.getMessage().getBody(Order.class);
    order.setOrderStatus(OrderStatus.INITIATED.getValue());
    exchange.getIn().setBody(order);
  }
}
