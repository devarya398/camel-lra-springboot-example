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
public class ServiceTwo {

	private static final Logger logger = LoggerFactory.getLogger(ServiceTwo.class);

	private static final int MINIMUM_DELAY_MS = 500;
	private static final int MAXIMUM_DELAY_MS = 1000;

	private static final int MINUMUM_RANDOM = 1;
	private static final int MAXIMUM_RANDOM = 10;
	private static final int TEST_RANDOM = 5;

	private static final Random random = new SecureRandom();

	public Object performOperation(Exchange exchange) {
		logger.info("serviceTwo --> incoming exchange --> request payload --> {}",
				exchange.getIn().getBody(Order.class));
		logger.info("serviceTwo --> incoming exchange --> LRA ID --> {}",
				exchange.getIn().getHeader("Long-Running-Action"));
		delay();
		if (getRandomNumber() >= TEST_RANDOM) {
			String result = "ServiceTwo~" + UUID.randomUUID().toString();
			logger.info("Operation performed from ServiceTwo: {}", result);
			return exchange.getIn().getBody();
		} else {
			throw new RuntimeException("ServiceTwo operation failed");
		}
	}

	public void cancelOperation(Exchange exchange) {
		logger.info("cancelOperation --> serviceTwo --> incoming exchange --> request payload --> {}",
				exchange.getIn().getBody(String.class));
		logger.info("Operation Canceled from ServiceTwo: {}", exchange.getIn().getHeader("Long-Running-Action"));
	}

	public void completeOperation() {
		logger.info("Operation completed from ServiceTwo");
	}

	private long getRandomNumber() {
		long result = (long) random.nextInt(MAXIMUM_RANDOM - MINUMUM_RANDOM) + (long) MINUMUM_RANDOM;
		logger.debug("Random result:{}", result);
		return result;
	}

	private void delay() {
		try {
			long delay = (long) random.nextInt(MAXIMUM_DELAY_MS - MINIMUM_DELAY_MS) + (long) MINIMUM_DELAY_MS;
			logger.debug("ServiceTwo Delay: {}", delay);
			TimeUnit.MILLISECONDS.sleep(delay);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
	}
}
