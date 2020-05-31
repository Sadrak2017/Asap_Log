package message;

public class Response {
	  private String status;
	  private Object data;
	  private Object inner;
	  
	  public Response(){
	    
	  }
	  
	  public Response(String status, Object data){
	    this.status = status;
	    this.data = data;
	  }
		 
	  public Response(String status, Object data, Object inner){
	    this.status = status;
	    this.data = data;
	    this.inner = inner;
	  }
	 
	  public String getStatus() {
	    return status;
	  }
	 
	  public void setStatus(String status) {
	    this.status = status;
	  }
	 
	  public Object getInner() {
		    return inner;
	  }
	 
	  public void setInner(Object inner) {
	    this.inner = inner;
	  }
	  
	  public Object getData() {
	    return data;
	  }
	 
	  public void setData(Object data) {
	    this.data = data;
	  }
	}