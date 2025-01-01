package BodyBuddy.demo.exception;


public class CalendarNotFoundException extends RuntimeException {

	public CalendarNotFoundException(String message) {
		super(message);
	}
}