package org.shiro.demo.controller.front.interfa;

public class returnDataInterface {

	/**
	 * 状态码
	 * 200成功
	 * 100失败
	 * 
	 */
	private Integer code;
	
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 数据
	 */
	private Object data;

	public returnDataInterface() {
		super();
	}

	public returnDataInterface(Integer code, String description, Object data) {
		super();
		this.code = code;
		this.description = description;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
}
