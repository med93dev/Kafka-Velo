package com.bike.bike;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kafka.consumer.KafkaConsumerListener;
import com.kafka.producer.KafkaProducerService;

@SpringBootApplication
public class BikeApplication implements CommandLineRunner {

	private KafkaProducerService ProducerService;
	private KafkaConsumerListener ConsumerListener;

	public static void main(String[] args) {
		SpringApplication.run(BikeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumerListener = new KafkaConsumerListener();
		ConsumerListener.start();
		ProducerService = new KafkaProducerService();
		ProducerService.send();

	}

}
