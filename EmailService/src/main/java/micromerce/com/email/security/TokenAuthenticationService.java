package micromerce.com.email.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collection;
import java.util.Date;

import static java.util.Collections.emptyList;

import java.sql.Timestamp;
import java.text.ParseException;

@Component
public class TokenAuthenticationService {
	
	
  static final long EXPIRATIONTIME = 864_000_000; // 10 dana
  static final String SECRET = "ThisIsASecret";
  static final String TOKEN_PREFIX = "Bearer";
  static final String HEADER_STRING = "Authorization";

  static Authentication getAuthentication(HttpServletRequest request){
	Cookie cookies[] = request.getCookies();
	Cookie cookie = new Cookie("naziv", null);
	if(cookies != null)
	{
		for(int i = 0; i < cookies.length; i++)
		{
			if(cookies[i].getName().equals("Authorization"))
			{
				cookie = cookies[i];
				break;
			}
	    }
	}
	String token = cookie.getValue();
	String novi = "Authorization=" + token;
	System.out.println(novi);
    if (token != null) 
    {
      // parse the token.
      

      String user = Jwts.parser()
    		  .setSigningKey(SECRET)
    		  .parseClaimsJws(" "+ token)
    		  .getBody()
    		  .getSubject();
      System.out.println(user);
      
      String role= Jwts.parser()
				.setSigningKey(SECRET)
				.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
				.getBody()
				.get("authority").toString();
      System.out.println(role);
      
      Collection<GrantedAuthority> authority = new HashSet<GrantedAuthority>();
      authority.add(new SimpleGrantedAuthority(role));
      
      
      

      /*return user != null ?
          new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
          null;*/
      if(user != null)
    	  return new UsernamePasswordAuthenticationToken(user, null, authority);
      else
    	  return null;
 
    }
    return null;
  }
}
