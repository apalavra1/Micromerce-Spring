package micromerce.com.products.security;

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
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable().authorizeRequests()
	    .antMatchers("/").permitAll()
	    .antMatchers(HttpMethod.GET, "/products").hasAnyAuthority("ADMIN", "USER")
	    .antMatchers(HttpMethod.GET, "/products/*").hasAnyAuthority("ADMIN", "USER")
	    .antMatchers("/getProductsByIds").hasAnyAuthority("ADMIN", "USER")
	    .antMatchers("/products").hasAuthority("ADMIN")
	    .antMatchers("/products/*").hasAuthority("ADMIN")
	    .anyRequest().authenticated()
	    .and()
	    .addFilterBefore(new JWTAuthenticationFilter(),
	                UsernamePasswordAuthenticationFilter.class);
	  }
	
}
