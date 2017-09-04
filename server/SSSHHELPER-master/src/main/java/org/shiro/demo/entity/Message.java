package org.shiro.demo.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "weibo_message")
public class Message {
	public static final Integer message_unread = 1;
	public static final Integer message_read = 0;
        @Id 
        @Column(name = "messageId")
        private String messageId;
        
        @Column(name = "acceptUserId")
        private Integer acceptUserId;
        
        @Column(name = "sendUserId")
        private Integer sendUserId;
        
        @Column(name = "messageContent")
        private String messageContent;
        
        @Column(name = "weiboId")
        private String weiboId;
        
        @Column(name = "messageUnRead")
        private Integer messageUnRead;
        
        @Column(name = "messageTime")
        private Long messageTime;

		public String getMessageId() {
			return messageId;
		}

		public void setMessageId(String messageId) {
			this.messageId = messageId;
		}

		public Integer getAcceptUserId() {
			return acceptUserId;
		}

		public void setAcceptUserId(Integer acceptUserId) {
			this.acceptUserId = acceptUserId;
		}

		public Integer getSendUserId() {
			return sendUserId;
		}

		public void setSendUserId(Integer sendUserId) {
			this.sendUserId = sendUserId;
		}

		public String getMessageContent() {
			return messageContent;
		}

		public void setMessageContent(String messageContent) {
			this.messageContent = messageContent;
		}

		public String getWeiboId() {
			return weiboId;
		}

		public void setWeiboId(String weiboId) {
			this.weiboId = weiboId;
		}

		public Integer getMessageUnRead() {
			return messageUnRead;
		}

		public void setMessageUnRead(Integer messageUnRead) {
			this.messageUnRead = messageUnRead;
		}

		public Long getMessageTime() {
			return messageTime;
		}

		public void setMessageTime(Long messageTime) {
			this.messageTime = messageTime;
		}

		public Message() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Message(Integer acceptUserId, Integer sendUserId, String messageContent, String weiboId) {
			super();
			this.messageId = UUID.randomUUID().toString().replace("-", "");
			this.acceptUserId = acceptUserId;
			this.sendUserId = sendUserId;
			this.messageContent = messageContent;
			this.weiboId = weiboId;
			this.messageTime = System.currentTimeMillis()/1000;
			this.messageUnRead = message_unread;
		}
        
}
