package io.anuragksinha.kafkaservice;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
	/**
	 * GroupId is added so that if we scale
	 * and if we have more instances of same application
	 * they can read from the same partition or topic
	 * @param data
	 *
	 * The containerFactory() identifies the KafkaListenerContainerFactory to
	 * use to build the Kafka listener container.
	 * If not set, a default container factory is assumed to be available
	 * with a bean name of kafkaListenerContainerFactory unless an explicit
	 * default has been provided through configuration.
	 */
	@KafkaListener(topics = "firstTopic",groupId = "groupId",containerFactory = "factory")
	public void listener(Message data){
		System.out.println("Listener received:: "+data);
	}
}
