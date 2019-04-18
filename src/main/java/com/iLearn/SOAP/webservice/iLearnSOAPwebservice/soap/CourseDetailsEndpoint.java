package com.iLearn.SOAP.webservice.iLearnSOAPwebservice.soap;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.iLearn.courses.CourseDetails;
import com.iLearn.courses.DeleteCourseDetailsRequest;
import com.iLearn.courses.DeleteCourseDetailsResponse;
import com.iLearn.courses.GetAllCourseDetailsRequest;
import com.iLearn.courses.GetAllCourseDetailsResponse;
import com.iLearn.courses.GetCourseDetailsRequest;
import com.iLearn.courses.GetCourseDetailsResponse;
import com.iLearn.SOAP.webservice.iLearnSOAPwebservice.soap.bean.Course;
import com.iLearn.SOAP.webservice.iLearnSOAPwebservice.soap.exception.CourseNotFoundException;
import com.iLearn.SOAP.webservice.iLearnSOAPwebservice.soap.service.CourseDetailsService;
import com.iLearn.SOAP.webservice.iLearnSOAPwebservice.soap.service.CourseDetailsService.Status;

@Endpoint
public class CourseDetailsEndpoint {

	@Autowired
	CourseDetailsService service;

	// method
	// input - GetCourseDetailsRequest
	// output - GetCourseDetailsResponse

	// http://iLearn.com/courses
	// GetCourseDetailsRequest
	@PayloadRoot(namespace = "http://iLearn.com/courses", localPart = "GetCourseDetailsRequest")
	@ResponsePayload
	public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {

		Course course = service.findById(request.getId());

		if (course == null)
			throw new CourseNotFoundException("Invalid Course Id " + request.getId());

		return mapCourseDetails(course);
	}

	private GetCourseDetailsResponse mapCourseDetails(Course course) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		response.setCourseDetails(mapCourse(course));
		return response;
	}

	private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
		GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
		for (Course course : courses) {
			CourseDetails mapCourse = mapCourse(course);
			response.getCourseDetails().add(mapCourse);
		}
		return response;
	}

	private CourseDetails mapCourse(Course course) {
		CourseDetails courseDetails = new CourseDetails();

		courseDetails.setId(course.getId());

		courseDetails.setName(course.getName());

		courseDetails.setDescription(course.getDescription());
		return courseDetails;
	}

	@PayloadRoot(namespace = "http://iLearn.com/courses", localPart = "GetAllCourseDetailsRequest")
	@ResponsePayload
	public GetAllCourseDetailsResponse processAllCourseDetailsRequest(
			@RequestPayload GetAllCourseDetailsRequest request) {

		List<Course> courses = service.findAll();

		return mapAllCourseDetails(courses);
	}

	@PayloadRoot(namespace = "http://iLearn.com/courses", localPart = "DeleteCourseDetailsRequest")
	@ResponsePayload
	public DeleteCourseDetailsResponse deleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request) {

		Status status = service.deleteById(request.getId());

		DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
		response.setStatus(mapStatus(status));

		return response;
	}

	private com.iLearn.courses.Status mapStatus(Status status) {
		if (status == Status.FAILURE)
			return com.iLearn.courses.Status.FAILURE;
		return com.iLearn.courses.Status.SUCCESS;
	}
}

