package micromerce.com.products.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import micromerce.com.products.security.TokenAuthenticationService;

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
		  if("OPTIONS".equalsIgnoreCase(((HttpServletRequest) request).getMethod())) {
			    ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
		  }
		  else
		  {
		  		Authentication authentication = TokenAuthenticationService
			        .getAuthentication((HttpServletRequest)request);
	
			    SecurityContextHolder.getContext()
			        .setAuthentication(authentication);
			    filterChain.doFilter(request,response);
		  }
  }
}
