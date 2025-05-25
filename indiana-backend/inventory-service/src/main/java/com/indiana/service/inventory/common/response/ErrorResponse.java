package com.indiana.service.inventory.common.response;

public record ErrorResponse(String code, String message, String detail, Integer statusCode) {

}
