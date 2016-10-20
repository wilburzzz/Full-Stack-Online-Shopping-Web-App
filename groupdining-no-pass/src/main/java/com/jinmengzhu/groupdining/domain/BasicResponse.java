package com.jinmengzhu.groupdining.domain;

import java.io.Serializable;


public class BasicResponse<T> implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private boolean success = true;
	
	private T object;
	
	private String message = "";

	public BasicResponse() {
		this.success = true;
		this.object = null;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/*public static MustParaDto checkLogin(String mustParaDto) {
		MustParaDto mustDto = new Gson().fromJson(mustParaDto, MustParaDto.class);
		if (mustDto.getAdminId() == null) {
			throw new ParamNullException("adminId");
		}
		return mustDto;
	}*/
}
