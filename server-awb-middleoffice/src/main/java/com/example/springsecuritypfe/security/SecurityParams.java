package com.example.springsecuritypfe.security;

public class SecurityParams {
	
	public static final String JWT_HEADER_NAME="Authorization";
    public static final String SECRET="keepitsimple";
    public static final long EXPIRATION=5*60*1000;
    public static final String HEADER_PREFIX="Bearer ";

}
