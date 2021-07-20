package com.ctrip.xpipe.monitor;

import com.ctrip.xpipe.spring.AbstractProfile;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author wenchao.meng
 *
 *         Aug 11, 2016
 */
@Configuration
@Profile(AbstractProfile.PROFILE_NAME_PRODUCTION)
public class CatConfig {

	public static final String CAT_ENABLED_KEY = "cat.client.enabled";
	
	private static final boolean catEnabled = Boolean.parseBoolean(System.getProperty(CAT_ENABLED_KEY, "true"));
	
	public static boolean isCatenabled() {
		return catEnabled;
	}

	@Bean
	public FilterRegistrationBean catFilter() {
		
		FilterRegistrationBean bean = new FilterRegistrationBean();

		return bean;
	}

	@Bean
	public ServletListenerRegistrationBean catListener() {
		
		ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();

		return bean;
	}
}
