package micromerce.com.user.security;

public class AccountCredentials {

	  private String username;
	  private String password;
	  // getters & setters
	  public String getUsername() {
		  return this.username;
	  }
	  public void setUsername(String u) {
		  this.username = u;
	  }
	  public String getPassword(){
		  return this.password;
	  }
	  public void setPassword(String p){
		  this.password = p;
	  }
	  public AccountCredentials(){}
	}
