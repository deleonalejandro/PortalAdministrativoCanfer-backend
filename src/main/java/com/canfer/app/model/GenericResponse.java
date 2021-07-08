package com.canfer.app.model;


public class GenericResponse {
	
	private String desc;
	private String error;
	private Boolean status;
	
	
	public GenericResponse(String desc, Boolean status) {
		super();
		this.desc = desc;
		this.status = status;
	}


	public GenericResponse(String desc, String error, Boolean status) {
		super();
		this.desc = desc;
		this.error = error;
		this.status = status;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public String getError() {
		return error;
	}


	public void setError(String error) {
		this.error = error;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	

}
