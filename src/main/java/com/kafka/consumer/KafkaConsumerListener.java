package com.kafka.consumer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.kafka.model.StationEvent;

public class KafkaConsumerListener extends Thread {
	private static final Logger log = LoggerFactory.getLogger(KafkaConsumerListener.class);
	private KafkaConsumer<String, StationEvent> consumer;

	private String topic;
	public static final String KAFKA_SERVER_URL = "localhost";
	public static final int KAFKA_SERVER_PORT = 9092;
	public static final String CLIENT_ID = "SampleConsumer";
	private static final Map<String, StationEvent> mapOfStationEvent = new HashMap<>();

	public void SampleConsumer() {
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, CLIENT_ID);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.IntegerDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, ErrorHandlingDeserializer.class);
		props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, ErrorHandlingDeserializer.class);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.kafka.model.StationEvent");
		props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		consumer = new KafkaConsumer<String, StationEvent>(props);
	}

	@Override
	public void run() {
		SampleConsumer();
		this.topic = "topics";
		consumer.subscribe(Arrays.asList(this.topic));
		consumer.poll(0);
		consumer.seekToBeginning(consumer.assignment());
		ConsumerRecords<String, StationEvent> records = consumer.poll(1000);
		for (ConsumerRecord<String, StationEvent> station : records) {

			String name = station.value().getName() + station.value().getContractName();
			int available_bike_stands = station.value().getAvailableBikeStands();
			if (!mapOfStationEvent.containsKey(name)) {
				mapOfStationEvent.put(name, station.value());
			}

			StationEvent city_station = mapOfStationEvent.get(name);
			int count_diff = available_bike_stands - city_station.getAvailableBikeStands();
			if (count_diff != 0) {
				mapOfStationEvent.put(name, station.value());
				if (count_diff > 0) {

					System.out.println(count_diff + " Support(s) à vélos disponibles ou occupés *** STATION : "
							+ station.value().getContractName() + " **** ADRESSE : " + station.value().getAddress());

				}
			}
			System.out.println(
					"Received message: (" + station.key() + ", " + station.value() + ") at offset " + station.offset());
		}
	}

}
