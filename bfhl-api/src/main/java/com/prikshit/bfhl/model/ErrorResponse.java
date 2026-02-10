package com.prikshit.bfhl.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "is_success" })
public class ErrorResponse {

    public boolean is_success;

    public ErrorResponse() {
        this.is_success = false;
    }
}
