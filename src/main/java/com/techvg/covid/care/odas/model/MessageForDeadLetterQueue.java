package com.techvg.covid.care.odas.model;

import com.techvg.covid.care.domain.enumeration.MessageType;

public class MessageForDeadLetterQueue {

	private MessageType messageType;
	private Object messageData;
	private int messageRetryCount;
	private int messageStatus;

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public Object getMessageData() {
		return messageData;
	}

	public void setMessageData(Object messageData) {
		this.messageData = messageData;
	}

	public int getMessageRetryCount() {
		return messageRetryCount;
	}

	public void setMessageRetryCount(int messageRetryCount) {
		this.messageRetryCount = messageRetryCount;
	}

	public int getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(int messageStatus) {
		this.messageStatus = messageStatus;
	}

}
