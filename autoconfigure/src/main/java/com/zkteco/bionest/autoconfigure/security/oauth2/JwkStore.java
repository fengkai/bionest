package com.zkteco.bionest.autoconfigure.security.oauth2;

public class JwkStore {

	private final String jwksJson;

	public JwkStore(String jwksJson) {
		this.jwksJson = jwksJson;
	}

	public String get() {
		return jwksJson;
	}

}
