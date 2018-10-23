package com.cx.gal.tickeTooli.exceptions;

/**
 * Created by Galn on 05/02/2018.
 */
public class CxHTTPClientException extends TickeToolException {

    private int statusCode = 0;

    public CxHTTPClientException(int statusCode, String s) {
        super(s);
        this.statusCode = statusCode;
    }

    public CxHTTPClientException() {
        super();
    }

    public CxHTTPClientException(String message) {
        super(message);
    }

    public CxHTTPClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public CxHTTPClientException(Throwable cause) {
        super(cause);
    }

    public int getStatusCode() {
        return this.statusCode;
    }


}
