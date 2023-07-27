package com.techvg.covid.care.odas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.techvg.covid.care.config.RabbitMQConfig;
import com.techvg.covid.care.domain.enumeration.MessageType;
import com.techvg.covid.care.odas.model.BedInfo;
import com.techvg.covid.care.odas.model.BedsOccupancy;
import com.techvg.covid.care.odas.model.FacilityInfo;
import com.techvg.covid.care.odas.model.MessageForDeadLetterQueue;
import com.techvg.covid.care.odas.model.OxygenConsumption;
import com.techvg.covid.care.odas.model.OxygenInfo;

@Component
public class ODASMQCommHelper {

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${ODAS.Queue.oxygen.consumption}")
	public String ODAS_DEAD_LETTER_QUEUE_NAME;

	@Value("${ODAS.Queue.facility.info}")
	private String updateFacilityInfoQueue;

	@Value("${ODAS.Queue.bed.info}")
	private String updateFacilityBedInfoQueue;

	@Value("${ODAS.Queue.bed.occupancy}")
	private String updateFacilityBedOccupancyQueue;

	@Value("${ODAS.Queue.oxygen.info}")
	private String updateFacilityOxygenInfoQueue;

	@Value("${ODAS.Queue.oxygen.consumption}")
	private String updateFacilityOxygenConsumptionQueue;

	private final Logger log = LoggerFactory.getLogger(ODASMQCommHelper.class);

	public void publishFacilityInfo(FacilityInfo facilityInfo) {

		try {

			String hospitaInfo = new Gson().toJson(facilityInfo);

			rabbitTemplate.convertAndSend(this.updateFacilityInfoQueue, facilityInfo);
			log.debug("Send msg = " + hospitaInfo);
		} catch (Exception ex) {
            ex.printStackTrace();
			log.error("publishFacilityInfo ::::::::::" + ex);
			MessageForDeadLetterQueue deadLetterQueue=new MessageForDeadLetterQueue();
			deadLetterQueue.setMessageData(facilityInfo);
			deadLetterQueue.setMessageType(MessageType.FACILITY);
			rabbitTemplate.convertAndSend(this.ODAS_DEAD_LETTER_QUEUE_NAME, deadLetterQueue);
		}

	}

	public void publishFacilityBedsInfo(BedInfo bedInfo) {

		try {

			String bedInfoString = new Gson().toJson(bedInfo);

			rabbitTemplate.convertAndSend(this.updateFacilityBedInfoQueue, bedInfo);
			log.debug("Send msg = " + bedInfoString);
		} catch (Exception ex) {
            ex.printStackTrace();
			log.error("publishFacilityBedsInfo ::::::::::" + ex);
			MessageForDeadLetterQueue deadLetterQueue=new MessageForDeadLetterQueue();
			deadLetterQueue.setMessageData(bedInfo);
			deadLetterQueue.setMessageType(MessageType.BED_INFO);
			rabbitTemplate.convertAndSend(this.ODAS_DEAD_LETTER_QUEUE_NAME, deadLetterQueue);
		}

	}

	public void publishFacilityBedsOccupancy(BedsOccupancy bedOccupancy) {

		try {

			String bedOccupancyString = new Gson().toJson(bedOccupancy);

			rabbitTemplate.convertAndSend(this.updateFacilityBedOccupancyQueue, bedOccupancy);
			log.debug("Send msg = " + bedOccupancyString);

		} catch (Exception ex) {
            ex.printStackTrace();
			log.error("publishFacilityBedsOccupancy ::::::::::" + ex);
			MessageForDeadLetterQueue deadLetterQueue=new MessageForDeadLetterQueue();
			deadLetterQueue.setMessageData(bedOccupancy);
			deadLetterQueue.setMessageType(MessageType.BED_OCCUPANCY);
			rabbitTemplate.convertAndSend(this.ODAS_DEAD_LETTER_QUEUE_NAME, deadLetterQueue);
		}

	}

	public void publishFacilityOxygenInfo(OxygenInfo oxygenInfo) {

		try {

			String bedInfoString = new Gson().toJson(oxygenInfo);

			rabbitTemplate.convertAndSend(this.updateFacilityOxygenInfoQueue, oxygenInfo);
			log.debug("Send msg = " + bedInfoString);

		} catch (Exception ex) {
            ex.printStackTrace();
			log.error("publishFacilityOxygenInfo ::::::::::" + ex);
			MessageForDeadLetterQueue deadLetterQueue=new MessageForDeadLetterQueue();
			deadLetterQueue.setMessageData(oxygenInfo);
			deadLetterQueue.setMessageType(MessageType.OXYGEN_INFRA);
			rabbitTemplate.convertAndSend(this.ODAS_DEAD_LETTER_QUEUE_NAME, deadLetterQueue);
		}

	}

	public void publishFacilityOxygenConsumption(OxygenConsumption oxygenConsumption) {

		try {

			String oxygenConsumptionString = new Gson().toJson(oxygenConsumption);

			rabbitTemplate.convertAndSend(this.updateFacilityOxygenConsumptionQueue, oxygenConsumption);
			log.debug("Send msg = " + oxygenConsumptionString);

		} catch (Exception ex) {
            ex.printStackTrace();
			log.error("publishFacilityOxygenConsumption ::::::::::" + ex);
			MessageForDeadLetterQueue deadLetterQueue=new MessageForDeadLetterQueue();
			deadLetterQueue.setMessageData(oxygenConsumption);
			deadLetterQueue.setMessageType(MessageType.OXYGEN_CONSUMPTIONS);
			rabbitTemplate.convertAndSend(this.ODAS_DEAD_LETTER_QUEUE_NAME, deadLetterQueue);
		}

	}

}
