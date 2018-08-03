package micromerce.com.products;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsController {
	
	@Autowired
	private ProductRepository pr;
	
	@RequestMapping(value = "/getProductsByIds", method = RequestMethod.GET)
	public List<Product> getProductsByIds (@RequestParam(value="ids", defaultValue="World") List<String> ids, @RequestHeader(value="Cookie") String cookie)
	{
		List<Product> products = new ArrayList<Product>();
		products = (List<Product>) pr.findAllById(ids);
		return products;
	}
	
	@RequestMapping(value = "/getProductsByCategory", method = RequestMethod.GET)
	public List<Product> getProductsByCategory (@RequestParam(value="category", defaultValue="World") String category, @RequestHeader(value="Cookie") String cookie)
	{
		return pr.findByCategory(category);
	}

}
