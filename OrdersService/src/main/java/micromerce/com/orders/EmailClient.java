package micromerce.com.orders;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("email-service")
public interface EmailClient {
	
	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	@ResponseBody
	public String sendEmail
	(@RequestBody Email email, 
	@RequestHeader(value="Cookie") String cookie) throws Exception;

}
