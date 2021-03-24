package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "hackingproperties")
public class HackingProperties {

	private int maxAttemps;
	private int attempsTimeout;
	
	public int getMaxAttemps() {
		return maxAttemps;
	}
	public void setMaxAttemps(int maxAttemps) {
		this.maxAttemps = maxAttemps;
	}
	public int getAttempsTimeout() {
		return attempsTimeout;
	}
	public void setAttempsTimeout(int attempsTimeout) {
		this.attempsTimeout = attempsTimeout;
	}
	
}
