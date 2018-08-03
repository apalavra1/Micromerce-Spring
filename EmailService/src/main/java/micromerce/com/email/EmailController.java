package micromerce.com.email;



import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import org.springframework.stereotype.Component;



@RestController
public class EmailController {
	
@Autowired
private JavaMailSender javaMailSender;

@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
@ResponseBody
public String sendEmail(@RequestBody Email email, @RequestHeader(value="Cookie") String cookie) throws Exception{
	String to = new String(), subject = new String(), text = new String();
	to = email.getTo();
	subject = email.getSubject();
	text = email.getText();

	MimeMessage mail = javaMailSender.createMimeMessage();
    try {
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(to);
        helper.setFrom("info.micromerce@gmail.com");
        helper.setSubject(subject);
        helper.setText(text);
    } catch (MessagingException e) {
        e.printStackTrace();
    } finally {}
    javaMailSender.send(mail);
	
	return "ok";
}

}