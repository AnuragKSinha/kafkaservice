package io.anuragksinha.kafkaservice.config;

import java.util.HashMap;
import java.util.Map;

import io.anuragksinha.kafkaservice.Message;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConsumerConfig {
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	public Map<String,Object> consumerConfig(){
		Map<String,Object> pros= new HashMap<String,Object>();
		pros.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
		/*pros.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		pros.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);*/
		return pros;
	}
	@Bean
	public ConsumerFactory<String, Message> consumerFactory(){
		JsonDeserializer<Message> jsonDeserializer=new JsonDeserializer();
		jsonDeserializer.addTrustedPackages("io.anuragksinha.kafkaservice");
		return new DefaultKafkaConsumerFactory<>(consumerConfig(),
				new StringDeserializer(),
				jsonDeserializer
				);
	}

	/**
	 * This listener receives all the messages from all topics
	 * and all partitions on a single thread
	 * @param consumerFactory
	 * @return
	 */
	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,Message>>
	factory(ConsumerFactory<String,Message> consumerFactory){
		ConcurrentKafkaListenerContainerFactory<String,Message> factory= new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		return factory;
	}
}
