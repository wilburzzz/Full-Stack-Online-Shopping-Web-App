package com.jinmengzhu.groupdining.exception;

public class ParamNullException extends RuntimeException{

	private static final long serialVersionUID = 3641992311522351847L;

	public ParamNullException(String message) {
		super(message + " is null or invalid type");
	}
}
