package org.bahmni.insurance.auth;

import org.bahmni.insurance.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("org.possible.imisconnect")
@EnableWebMvc
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	private AuthenticationEntryPoint authEntryPoint;

	private AppProperties properties;
	
	@Autowired
	public SecurityConfig(AppProperties properties) {
		this.properties = properties;
	}

	/*@Autowired
	private AuthenticationFilter authenticationFilter;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(authenticationFilter);
	}*/
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic()
		.authenticationEntryPoint(authEntryPoint);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.inMemoryAuthentication()
		.withUser(properties.imisConnectUser)
		.password("{noop}"+properties.imisConnectPassword)
		.roles("USER");
	}

}
