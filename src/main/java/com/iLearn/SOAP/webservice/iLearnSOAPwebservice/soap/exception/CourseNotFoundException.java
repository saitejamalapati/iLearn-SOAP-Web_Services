package com.iLearn.SOAP.webservice.iLearnSOAPwebservice.soap.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CUSTOM , customFaultCode = "{http://iLearn.com/courses}001_COURSE_NOT_FOUND")
public class CourseNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3518170101751491969L;

	public CourseNotFoundException(String message) {
		super(message);
	}
}