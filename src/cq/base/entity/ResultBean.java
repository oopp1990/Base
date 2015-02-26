package cq.base.entity;

import java.io.Serializable;

public class ResultBean extends BaseBean {
	
	private String message;
	
	private boolean result;
	
	public ResultBean()
	{
		
	}
	
	public ResultBean(boolean result) {
		super();
		this.result = result;
	}

	public ResultBean(String message, boolean result) {
		super();
		this.message = message;
		this.result = result;
	}

	
	public Serializable getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	
	public Serializable[] getIds() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
