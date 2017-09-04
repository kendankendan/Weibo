package org.shiro.demo.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="weibo_attention")
public class Attention {
    @Id
    @Column(name="attentionId")
	private String attentionId;
    
    @Column(name="userId")
	private Integer userId;
    
    @Column(name="attentionUserId")
	private Integer attentionUserId;
    
    @Column(name="attentionTime")
	private Long attentionTime;

	public String getAttentionId() {
		return attentionId;
	}

	public void setAttentionId(String attentionId) {
		this.attentionId = attentionId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAttentionUserId() {
		return attentionUserId;
	}

	public void setAttentionUserId(Integer attentionUserId) {
		this.attentionUserId = attentionUserId;
	}

	public Long getAttentionTime() {
		return attentionTime;
	}

	public void setAttentionTime(Long attentionTime) {
		this.attentionTime = attentionTime;
	}

	public Attention() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Attention(Integer userId, Integer attentionUserId, Long attentionTime) {
		super();
		this.attentionId = UUID.randomUUID().toString().replace("-", "");
		this.userId = userId;
		this.attentionUserId = attentionUserId;
		this.attentionTime = attentionTime;
	}

	public Attention(String attentionId, Integer userId, Integer attentionUserId, Long attentionTime) {
		super();
		this.attentionId = attentionId;
		this.userId = userId;
		this.attentionUserId = attentionUserId;
		this.attentionTime = attentionTime;
	}
	
	
}
