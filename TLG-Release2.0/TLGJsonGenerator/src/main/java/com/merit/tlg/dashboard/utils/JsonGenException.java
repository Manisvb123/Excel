package com.merit.tlg.dashboard.utils;

public class JsonGenException extends Exception {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final ErrorCode code;
    
    public JsonGenException(ErrorCode code) {
        super();
        this.code = code;
    }
    
    public JsonGenException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }
    
    public JsonGenException(Throwable cause, ErrorCode code) {
        super(cause);
        this.code = code;
    }
    
    public JsonGenException(String message, Throwable cause, ErrorCode code) {
        super(message, cause);
        this.code = code;
    }
    
    public ErrorCode getCode() {
        return this.code;
    }

}
