package io.anuragksinha.kafkaservice.config;

import java.util.HashMap;
import java.util.Map;

import io.anuragksinha.kafkaservice.Message;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfig {
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	public Map<String,Object> producerConfig(){
		Map<String,Object> pros= new HashMap<String,Object>();
		pros.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
		pros.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		pros.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return pros;
	}
	@Bean
	public ProducerFactory<String, Message> producerFactory(){
		return new DefaultKafkaProducerFactory<>(producerConfig());
	}

	/**
	 * To Send messages
	 * @return
	 */
	@Bean
	public KafkaTemplate<String,Message> kafkaTemplate(ProducerFactory<String,Message> producerFactory){
		return new KafkaTemplate<>(producerFactory);
	}

}
