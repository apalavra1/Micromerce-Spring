package micromerce.com.user;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
	
	@Autowired
	UserRepository ur;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public User register(@RequestBody RegisterDTO dto)
	{
		System.out.println(dto.getName());
		User u = new User();
		if(dto.getPassword().equals(dto.getRepeat()))
		{
			u.setName(dto.getName());
			u.setUsername(dto.getUsername());
			u.setPassword(passwordEncoder.encode(dto.getPassword()));
			u.setAdmin(false);
			return ur.save(u);
		}
		return u;
	}
	
	@RequestMapping(value = "/logOut", method=RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie("Authorization", null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public User currentUserNameSimple(HttpServletRequest request, @RequestHeader(value="Cookie") String cookie) {
        Principal principal = request.getUserPrincipal();
        return ur.findByUsername(principal.getName());
    }

}
