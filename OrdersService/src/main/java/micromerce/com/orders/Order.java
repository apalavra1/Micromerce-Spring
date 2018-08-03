package micromerce.com.orders;

import java.util.List;
import org.springframework.data.annotation.Id;


public class Order {
	
	@Id private String id;
	
	private List<String> products;
	private List<Integer> ammounts;
	private String customer;
	private String address;
	private String zipcode;
	private int total;
	
	public List<String> getProducts() {
		return products;
	}
	public void setProducts(List<String> products) {
		this.products = products;
	}
	public List<Integer> getAmmounts() {
		return ammounts;
	}
	public void setAmmounts(List<Integer> ammounts) {
		this.ammounts = ammounts;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}
