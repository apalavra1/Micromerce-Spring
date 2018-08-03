package micromerce.com.orders.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;


import org.springframework.security.core.Authentication;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JWTAuthenticationFilter extends GenericFilterBean {
	
	private final List<String> allowedOrigins = Arrays.asList("http://localhost:4200");

  @Override
  public void doFilter(ServletRequest request,
             ServletResponse response,
             FilterChain filterChain)
      throws IOException, ServletException {
		  if("OPTIONS".equalsIgnoreCase(((HttpServletRequest) request).getMethod())) {
			    ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
			    String origin = ((HttpServletRequest) request).getHeader("Origin");
	            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
	            ((HttpServletResponse) response).setHeader("Vary", "Origin");

	            // Access-Control-Max-Age
	            ((HttpServletResponse) response).setHeader("Access-Control-Max-Age", "3600");

	            // Access-Control-Allow-Credentials
	            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Credentials", "true");

	            // Access-Control-Allow-Methods
	            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");

	            // Access-Control-Allow-Headers
	            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers",
	                "Origin, X-Requested-With, Content-Type, Accept, " + "X-CSRF-TOKEN");
		  }
		  else
		  {
		  		Authentication authentication = TokenAuthenticationService
			        .getAuthentication((HttpServletRequest)request);
	
			    SecurityContextHolder.getContext()
			        .setAuthentication(authentication);
			    

	            // Access-Control-Allow-Origin
	            
			    
			    
			    filterChain.doFilter(request,response);
		  }
  }
}
