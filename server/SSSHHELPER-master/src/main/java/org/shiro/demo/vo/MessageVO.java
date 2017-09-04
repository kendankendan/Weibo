package org.shiro.demo.vo;

import org.shiro.demo.entity.Message;

public class MessageVO {
	    private String messageId;  //评论编号
		
		private Integer sendUserId;      //发送者编号
		
		private Integer acceptUserId;   //接收者编号
		
		private String weiboId;   //微博编号
		
		private String messageContent;//评论内容
		
		private String messageTime;
		
		private Integer messageUnRead;
		/////其他表数据
		
		private String sendUserName;
		
		private String sendUserHead;
		
		 private Integer weiboCollect;//收藏次数

		    private Integer weiboForward;//转发次数

		    private Integer weiboComment;//评论次数

		    private Integer weiboLike;//赞的次数

		    private String weiboTime;//发表时间

		    private String weiboFwContent;//转发内容

		    private String weiboContent;//微博内容

		    private String weiboPhoto;//图片链接地址

		    private String weiboAt;//微博中@的人，用@进行分割

		    private String weiboTopic;//微博中主题

		    private String userName; //用户昵称
		    
		    private String userHead;

			public String getUserHead() {
				return userHead;
			}

			public void setUserHead(String userHead) {
				this.userHead = userHead;
			}

			public String getMessageId() {
				return messageId;
			}

			public void setMessageId(String messageId) {
				this.messageId = messageId;
			}

			public Integer getSendUserId() {
				return sendUserId;
			}

			public void setSendUserId(Integer sendUserId) {
				this.sendUserId = sendUserId;
			}

			public Integer getAcceptUserId() {
				return acceptUserId;
			}

			public void setAcceptUserId(Integer acceptUserId) {
				this.acceptUserId = acceptUserId;
			}

			public String getWeiboId() {
				return weiboId;
			}

			public void setWeiboId(String weiboId) {
				this.weiboId = weiboId;
			}

			public String getMessageContent() {
				return messageContent;
			}

			public void setMessageContent(String messageContent) {
				this.messageContent = messageContent;
			}

			public String getMessageTime() {
				return messageTime;
			}

			public void setMessageTime(String messageTime) {
				this.messageTime = messageTime;
			}

			public Integer getMessageUnRead() {
				return messageUnRead;
			}

			public void setMessageUnRead(Integer messageUnRead) {
				this.messageUnRead = messageUnRead;
			}

			public String getSendUserName() {
				return sendUserName;
			}

			public void setSendUserName(String sendUserName) {
				this.sendUserName = sendUserName;
			}

			public String getSendUserHead() {
				return sendUserHead;
			}

			public void setSendUserHead(String sendUserHead) {
				this.sendUserHead = sendUserHead;
			}

			public Integer getWeiboCollect() {
				return weiboCollect;
			}

			public void setWeiboCollect(Integer weiboCollect) {
				this.weiboCollect = weiboCollect;
			}

			public Integer getWeiboForward() {
				return weiboForward;
			}

			public void setWeiboForward(Integer weiboForward) {
				this.weiboForward = weiboForward;
			}

			public Integer getWeiboComment() {
				return weiboComment;
			}

			public void setWeiboComment(Integer weiboComment) {
				this.weiboComment = weiboComment;
			}

			public Integer getWeiboLike() {
				return weiboLike;
			}

			public void setWeiboLike(Integer weiboLike) {
				this.weiboLike = weiboLike;
			}

			public String getWeiboTime() {
				return weiboTime;
			}

			public void setWeiboTime(String weiboTime) {
				this.weiboTime = weiboTime;
			}

			public String getWeiboFwContent() {
				return weiboFwContent;
			}

			public void setWeiboFwContent(String weiboFwContent) {
				this.weiboFwContent = weiboFwContent;
			}

			public String getWeiboContent() {
				return weiboContent;
			}

			public void setWeiboContent(String weiboContent) {
				this.weiboContent = weiboContent;
			}

			public String getWeiboPhoto() {
				return weiboPhoto;
			}

			public void setWeiboPhoto(String weiboPhoto) {
				this.weiboPhoto = weiboPhoto;
			}

			public String getWeiboAt() {
				return weiboAt;
			}

			public void setWeiboAt(String weiboAt) {
				this.weiboAt = weiboAt;
			}

			public String getWeiboTopic() {
				return weiboTopic;
			}

			public void setWeiboTopic(String weiboTopic) {
				this.weiboTopic = weiboTopic;
			}

			public String getUserName() {
				return userName;
			}

			public void setUserName(String userName) {
				this.userName = userName;
			}

			public MessageVO() {
				super();
				// TODO Auto-generated constructor stub
			}

			public MessageVO(Message message) {
				super();
				this.messageId = message.getMessageId();
				this.sendUserId = message.getSendUserId();
				this.acceptUserId = message.getAcceptUserId();
				this.weiboId = message.getWeiboId();
				this.messageContent = message.getMessageContent();
				this.messageTime =  new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(message.getMessageTime()*1000));
				this.messageUnRead = message.getMessageUnRead();
			}
		    
		    
		
		
}
