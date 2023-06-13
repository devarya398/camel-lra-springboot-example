package com.camel_lra_springboot_example.services;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.camel_lra_springboot_example.model.Order;

@Service
public class ServiceOne {

	private static final Logger logger = LoggerFactory.getLogger(ServiceOne.class);

	private static final int MINIMUM_DELAY_MS = 500;
	private static final int MAXIMUM_DELAY_MS = 1000;

	private static final Random random = new SecureRandom();

	public Object performOperation(Exchange exchange) {
		logger.info("serviceOne --> incoming exchange --> request payload --> {}",
				exchange.getIn().getBody(Order.class));
		logger.info("serviceOne --> incoming exchange --> LRA ID --> {}",
				exchange.getIn().getHeader("Long-Running-Action"));
		delay();
		String result = "ServiceOne~" + UUID.randomUUID().toString();
		logger.info("Operation performed from ServiceOne: {}", result);
		return exchange.getIn().getBody();
	}

	public void cancelOperation(Exchange exchange) {
		logger.info("cancelOperation --> serviceOne --> incoming exchange --> request payload --> {}",
				exchange.getIn().getBody(String.class));
		logger.info("Operation Canceled from ServiceOne: {}", exchange.getIn().getHeader("Long-Running-Action"));
	}

	public void completeOperation() {
		logger.info("Operation completed from ServiceOne");
	}

	private void delay() {
		try {
			long delay = (long) random.nextInt(MAXIMUM_DELAY_MS - MINIMUM_DELAY_MS) + (long) MINIMUM_DELAY_MS;
			logger.debug("ServiceOne Delay: {}", delay);
			TimeUnit.MILLISECONDS.sleep(delay);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
	}
}
