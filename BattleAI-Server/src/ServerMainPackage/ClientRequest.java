package ServerMainPackage;

import java.io.Serializable;

public class ClientRequest implements Serializable {
	private RequestType type;
	
	public ClientRequest() {
		type = RequestType.None;
	}
	
	public RequestType getType() {
		return type;
	}
}
