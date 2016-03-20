package ServerMainPackage;

public class RegularClientRequest implements ClientRequest {
    
    private final RegularRequestType requestType;
    
    public RegularClientRequest(RegularRequestType requestType) {
        this.requestType = requestType;
    }
    
    public RegularRequestType getRequestType() {
        return requestType;
    }
}
