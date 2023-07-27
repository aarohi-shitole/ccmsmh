package com.techvg.covid.care.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public String ICMR_EXCHANGE_NAME = "patient-info.exchange";
	public String ICMR_ROUTING_KEY_NAME = "patient-info";
	public String ICMR_INCOMING_QUEUE_NAME = "local.icmr.patient.info.queue";
	public String ICMR_DEAD_LETTER_QUEUE_NAME = "local.icmr.patient.info.queue.dlq";

	// ----------------------------------END ICMR-----------------------

	public static String DLQ_FACILITY_EXCHANGE_NAME = "facility-info-dlq.exchange";
	public static String FACILITY_EXCHANGE_NAME = "facility-info.exchange";
	public static String FACILITY_ROUTING_KEY_NAME = "facility-info";

	@Value("${ODAS.Queue.facility.info}")
	public String FACILITY_INCOMING_QUEUE_NAME;
	
	public static String BED_EXCHANGE_NAME = "bed-info.exchange";
	public static String BED_ROUTING_KEY_NAME = "bed-info";
	@Value("${ODAS.Queue.bed.info}")
	public String BED_INFO_INCOMING_QUEUE_NAME;
	
	@Value("${ODAS.Queue.bed.occupancy}")
	public String BED_OCCUPANCY_INCOMING_QUEUE_NAME;

	@Value("${ODAS.Queue.oxygen.info}")
	public String OXYGEN_INFRA_INCOMING_QUEUE_NAME;
	
	@Value("${ODAS.Queue.oxygen.consumption}")
	public String OXYGEN_CONSUMPATIONS_INCOMING_QUEUE_NAME;

	@Value("${ODAS.dlq}")
	public String ODAS_DEAD_LETTER_QUEUE_NAME;

	// ----------------------------------END ODAS----------------------------------

	// ----------------------------------------------------------------------------
	
	@Bean
	DirectExchange deadLetterExchange() {
		return new DirectExchange(FACILITY_EXCHANGE_NAME);
	}
	
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(DLQ_FACILITY_EXCHANGE_NAME);
	}

	@Bean
	Queue dlq() {
		return QueueBuilder.durable(ODAS_DEAD_LETTER_QUEUE_NAME).build();
	}

	@Bean
	Queue queue() {
		return QueueBuilder.durable(FACILITY_INCOMING_QUEUE_NAME).withArgument("x-dead-letter-exchange", FACILITY_EXCHANGE_NAME)
				.withArgument("x-dead-letter-routing-key", FACILITY_ROUTING_KEY_NAME).build();
	}

	@Bean
	Binding DLQbinding() {
		return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with(FACILITY_ROUTING_KEY_NAME);
	}

	@Bean
	Binding binding() {
		return BindingBuilder.bind(queue()).to(exchange()).with(FACILITY_INCOMING_QUEUE_NAME);
	}
	
	
	

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
	// -----------------------------------------------------------------------------------

}
