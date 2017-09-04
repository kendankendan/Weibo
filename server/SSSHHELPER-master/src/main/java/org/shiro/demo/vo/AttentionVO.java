package org.shiro.demo.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Attention;
import org.shiro.demo.service.IAccountService;
import org.shiro.demo.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;


public class AttentionVO {

    private String attentionId;
    
	private Integer userId;
	
    
	private Integer attentionUserId;
	
    
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

	public AttentionVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AttentionVO(String attentionId, Integer userId, Integer attentionUserId, Long attentionTime) {
		super();
		this.attentionId = attentionId;
		this.userId = userId;
		this.attentionUserId = attentionUserId;
		this.attentionTime = attentionTime;
	}

	public AttentionVO(Attention attention) {
		super();
		this.attentionId = attention.getAttentionId();
		this.userId = attention.getUserId();
		this.attentionUserId = attention.getAttentionUserId();
		this.attentionTime = attention.getAttentionTime();
	}
	/**实体转化为显示层实体类
	 * @param 分页
	 * @return
	 */
	public static Map<String, Object> attention2attentionVO(Pagination<Attention> pagination) {
		List<Attention> recordList = pagination.getRecordList();
		List<AttentionVO> VOList = new ArrayList<AttentionVO>();
		Map<String, Object> map = new HashMap<>();
		for (Attention Attention : recordList) {
			VOList.add(new AttentionVO(Attention));
		}
		map.put("rows", VOList);
		map.put("total", pagination.getRecordCount());
		return map;
	}
	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static List<AttentionVO> attention2attentionVO(List<Attention> recordList){
		List<AttentionVO> VOList = new ArrayList<AttentionVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(Attention item : recordList){
			VOList.add(new AttentionVO(item));
		}
		return VOList;
	}
	
}
