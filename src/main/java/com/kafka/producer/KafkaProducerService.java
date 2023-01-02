package com.kafka.producer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kafka.model.StationEvent;

@Service
@EnableKafka
public class KafkaProducerService {
	private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);

	RestTemplate restTemplate;

	private String apiKeyJcdecaux = "62daf64c598eca3cfc79d294de042005880f6269";

	private String urlApiJcdecaux = "https://api.jcdecaux.com/vls/v1/stations?apiKey=";
	String kafkaTopic = "topics";
	public static final String CLIENT_ID = "SampleConsumer";

	public Map<String, Object> producerConfig() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, CLIENT_ID);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return props;
	}

	public void send() {
		StationEvent[] stations;
		ProducerFactory<String, StationEvent> producerFactory = new DefaultKafkaProducerFactory(producerConfig());
		KafkaTemplate KafkaTemplate = new KafkaTemplate<String, StationEvent>(producerFactory);
		restTemplate = new RestTemplate();
		stations = restTemplate.getForObject(urlApiJcdecaux + apiKeyJcdecaux, StationEvent[].class);

		 while (true) {

		log.info("Nbre de stations JCDecaux récuperer et envoyer au Consumer = "
				+ Objects.requireNonNull(stations).length);

		for (StationEvent station : stations) {

			KafkaTemplate.send(kafkaTopic, station);
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 }

	}

}
