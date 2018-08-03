package micromerce.com.user.security;

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

public class JWTAuthenticationFilter extends GenericFilterBean {

  @Override
  public void doFilter(ServletRequest request,
             ServletResponse response,
             FilterChain filterChain)
      throws IOException, ServletException {
	  HttpServletRequest r = (HttpServletRequest)request;
	  System.out.println(r.getRequestURI());
	  if("OPTIONS".equalsIgnoreCase(((HttpServletRequest) request).getMethod())) {
		    ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
	  }
      else
      {		
		  if(r.getRequestURI().equals("/login") == false)
		  {
			  System.out.println("udje");
			  Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest)request); 
	
			  SecurityContextHolder.getContext()
			  .setAuthentication(authentication);
		  }
		  filterChain.doFilter(request,response);
      }
  }
}
