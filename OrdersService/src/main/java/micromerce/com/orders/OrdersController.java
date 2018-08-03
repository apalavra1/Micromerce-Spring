package micromerce.com.orders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import micromerce.com.products.Product;
import micromerce.com.products.ProductRepository;

@RestController
@EnableFeignClients(basePackages = {"micromerce.com.orders"})
public class OrdersController {
	
@Autowired
ProductsClient pc;
@Autowired
EmailClient ec;
@Autowired
private OrderRepository or;
@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
@ResponseBody
public Order addOrder
(@RequestBody String req, 
@CookieValue("Authorization") String cookie) throws Exception
{
	String authorizationCookie = "Authorization=" + cookie;
	
	JSONObject obj = new JSONObject(req);
	JSONArray arr = obj.getJSONArray("id");
	JSONArray arr1 = obj.getJSONArray("ammount");
	
	List<String> ids = new ArrayList<String>();
	List<Integer> ammounts = new ArrayList<Integer>();
	
	String customer = obj.getString("email");
	String address = obj.getString("address");
	String zipcode = obj.getString("zipcode");
	
	int total = 0;
	
	for (int i = 0; i < arr.length(); i++)
	{
	    ids.add(arr.getString(i));
	    ammounts.add(Integer.parseInt(arr1.get(i).toString()));
	}
	
	List<Product> products = pc.getProductsByIds(ids, authorizationCookie);
	List<String> productNames = new ArrayList<String>();
	
	String text = new String();
	
	for (int i = 0; i < products.size(); i++)
	{
	    total = total + (products.get(i).getPrice() * ammounts.get(i));
	    productNames.add(products.get(i).getName());
	    text += ammounts.get(i).toString() + " * " + 
	    products.get(i).getName() + " Price: " + 
	    products.get(i).getPrice() + "\n";	
	}
	text += "Total: " + total;
	
	Order o = new Order();
	o.setProducts(productNames);
	o.setAmmounts(ammounts);
	o.setCustomer(customer);
	o.setAddress(address);
	o.setZipcode(zipcode);
	o.setTotal(total);
	
	or.save(o);
	
	Email email = new Email();
	email.setTo(customer);
	email.setSubject("Order from micromerce");
	email.setText(text);
	ec.sendEmail(email, authorizationCookie);
	return o;
}
	
	@RequestMapping(value = "/getOrdersByCustomer", method = RequestMethod.GET)
	public List<Order> getOrdersByCustomer(@RequestParam(value="customer", defaultValue="World") String customer, @CookieValue("Authorization") String cookie)
	{
		return or.findByCustomer(customer);
	}

	

}
