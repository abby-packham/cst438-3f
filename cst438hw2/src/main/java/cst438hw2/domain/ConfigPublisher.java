package cst438hw2.domain;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;

public class ConfigPublisher {
	@Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("city-reservation");
    }

}
