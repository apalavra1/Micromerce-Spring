package micromerce.com.orders;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import micromerce.com.products.Product;

@FeignClient("products-service")
public interface ProductsClient {
	@RequestMapping(value = "/getProductsByIds", method = RequestMethod.GET)
	public List<Product> getProductsByIds 
	(@RequestParam(value="ids", defaultValue="World") List<String> ids, 
	 @RequestHeader(value="Cookie") String cookie);

}
