package com.group1.flight.booking.core;

public enum ErrorCode {
    SUCCESS(200, "Success"),
    FAIL(404, "Fail"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    LOGIN_FAIL(401, "Login Failed"),
    SIGNUP_FAIL(401, "Signup Failed"),
    WRONG_PASSWORD(400, "Old password does not match with current password"),
    NO_SESSION(401, "No user in this session. You must login to create access resource"),
    EXPIRED_CODE(404, "Code is expired"),
    INVALID_CODE(404, "Invalid code"),
    INVALID_EMAIL(400, "Invalid email"),
    INVALID_DATE(400, "Invalid date"),
    INVALID_TOKEN(401, "Invalid Token"),
    VALID_TOKEN(201, "Valid token"),
    ID_NULL(404, "ID is null"),
    NULL_VALUE(404, "Value is null"),
    DATA_NULL(404, "Data is null"),
    BLANK_FIELD(404, "The require field is blank"),
    NOT_FOUND_USER(404, "User does not exist"),
    NOT_FOUND_ROLE(404, "Role does not exist"),
    NOT_FOUND_PERMISSION(404, "Permission does not exist"),
    NOT_FOUND_ACTION(404, "Action does not exist"),
    NOT_FOUND_RESOURCE(404, "Resource does not exist"),
    NOT_FOUND_PRODUCT(404, "Product does not exist"),
    NOT_FOUND_MENU(404, "Menu does not exist"),
    NOT_FOUND_CATEGORY(404, "Category does not exist"),
    NOT_FOUND_MEETING(404, "Meeting does not exist"),
    CALL_API_ERROR(404, "Call external API fail");

    final int value;
    final String message;

    private ErrorCode(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public int getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }
}
