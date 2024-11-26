package id.beecolony.routeanalyze.config;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.beecolony.routeanalyze.auth.WebAuthProvider;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private WebAuthProvider authProvider;

	public AuthenticationManager authenticationManager(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
		authenticationManagerBuilder.authenticationProvider(authProvider);

		return authenticationManagerBuilder.build();
	}

	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) 
			throws Exception {

		httpSecurity
		.authorizeHttpRequests(
			auth -> auth
					.requestMatchers("/login").permitAll()
					.requestMatchers("/logout").permitAll()
					.requestMatchers("/webjars/**").permitAll()
					.requestMatchers("/vendor/**").permitAll()
					.requestMatchers("/assets/**").permitAll()
					.requestMatchers("/error").permitAll()
					.anyRequest().authenticated()
				)
				.formLogin(fl ->{
					fl
						.loginPage("/login")
						.defaultSuccessUrl("/",true)
						.failureHandler((request, response, exception) -> {

							/* remove att session before */
							Enumeration<String> attNames = request.getSession().getAttributeNames();
							while(attNames.hasMoreElements()){
								String att = attNames.nextElement();
								if(att.contains("error")){
									request.getSession().removeAttribute(att);
								}
							}

							ObjectMapper om = new ObjectMapper();

							@SuppressWarnings("unchecked")
							HashMap<String, String> values = om.readValue(exception.getMessage(), HashMap.class);

							for (Entry<String, String> value : values.entrySet()) {
								request.getSession().setAttribute(value.getKey(), value.getValue());
							}

							response.sendRedirect("/login?error=true");
						})
						.permitAll();
				})
				.logout(lg ->{
					lg
					.addLogoutHandler((request, response, authentication) -> {
						try {
							response.sendRedirect("/login");
						} catch (IOException e) {
							e.printStackTrace();
						}
					})
					.permitAll();
				} );
				// .sessionManagement(sm -> {
				// 	sm
				// 	.invalidSessionUrl("/login?sesi=failed")
				// 	.maximumSessions(1)
				// 	.expiredUrl("/login?sesi=expired")
				// 	.and()
				// 	.sessionFixation().migrateSession();
				// });
		
		// httpSecurity.cors();
		httpSecurity.csrf(c -> c.disable());
		httpSecurity.anonymous(anom -> anom.disable());

		httpSecurity.addFilterAt((request, response, chain) ->{
			System.out.println("This is Filter on going");

			chain.doFilter(request, response);
		} ,UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
	}
	
	// @Bean
	// public WebMvcConfigurer webMvcConfigurer() {
	// 	return new WebMvcConfigurer() {
	// 		@Override
	// 		public void addCorsMappings(CorsRegistry registry) {
	// 			registry.addMapping("/**")
	// 					.allowedOrigins("http://localhost:4200")
	// 					.allowedMethods(HttpMethod.GET.toString(),
	// 									HttpMethod.POST.toString(),
	// 									HttpMethod.PUT.toString(),
	// 									HttpMethod.DELETE.toString());
				
	// 			WebMvcConfigurer.super.addCorsMappings(registry);
	// 		}
	// 	};		
	// }
	
}
