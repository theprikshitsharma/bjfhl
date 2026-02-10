package com.prikshit.bfhl.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "is_success", "official_email", "data" })
public class ApiResponse {

    public boolean is_success;  
    public String official_email;
    public Object data;

    public ApiResponse(boolean success, String email, Object data) {
        this.is_success = success;
        this.official_email = email;
        this.data = data;
    }
}
