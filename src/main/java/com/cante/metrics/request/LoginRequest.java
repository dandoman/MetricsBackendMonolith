package com.cante.metrics.request;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties
public class LoginRequest {
	private String email;
	private String password;
}
