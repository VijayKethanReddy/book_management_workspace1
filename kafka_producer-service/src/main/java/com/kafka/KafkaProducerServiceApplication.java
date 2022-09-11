package com.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author cogjava3180
 * This is KafkaProducerServiceApplication which will start kafka producer service
 *
 */

@SpringBootApplication
public class KafkaProducerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerServiceApplication.class, args);
	}

}
