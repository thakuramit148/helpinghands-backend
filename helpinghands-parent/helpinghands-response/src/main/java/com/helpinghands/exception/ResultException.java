package com.helpinghands.exception;

import com.helpinghands.response.Result;

public class ResultException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	Result<Object> resultExecption;

	public ResultException(Result<Object> resultExecption) {
		super(resultExecption.getMessage());
		this.resultExecption = resultExecption;
	}

	public Result<Object> getResultExecption() {
		return resultExecption;
	}

}
