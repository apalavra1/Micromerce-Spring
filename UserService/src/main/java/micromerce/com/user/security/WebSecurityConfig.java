package micromerce.com.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	
	  @Override
	  protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable().authorizeRequests()
	    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	    .antMatchers("/").hasAuthority("ADMIN")
	    .antMatchers("/users").hasAuthority("ADMIN")
	    .antMatchers("/users/*").hasAuthority("ADMIN")
	    .antMatchers("/user").permitAll()
	    .antMatchers(HttpMethod.POST, "/login").permitAll()
	    .antMatchers(HttpMethod.POST, "/register").permitAll()
	    .antMatchers(HttpMethod.POST, "/logout").hasAnyAuthority("ADMIN", "USER")
	    .anyRequest().authenticated()
	    .and()
	    // We filter the api/login requests
	    .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
	            UsernamePasswordAuthenticationFilter.class)
	    // And filter other requests to check the presence of JWT in header
	    .addFilterBefore(new JWTAuthenticationFilter(),
	                UsernamePasswordAuthenticationFilter.class);
	  }
	
	  /*@Override
	  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    // Create a default account
		  auth.inMemoryAuthentication()
	    .withUser("admin")
	    .password("password")
	    .roles("ADMIN");
	    
	  }*/
	  
	  @Autowired
	  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	       auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	  }
}
