package ServerMainPackage;

/**
 * <pre> A regular request is a request which does not require the server to do
 * any additional processing on the provided data. Instead it just uses it
 * to determine a course of action. Examples:
 * - ACKNOWLEDGE_ACTIVITY - asks the server to acknowledge the connection as
 * active
 * - GET_MATCH_LIST - asks the server to send a list of active matches to the
 * client </pre>
 */
public class RegularClientRequest implements ClientRequest {
    
    private final RegularRequestType requestType;
    
    public RegularClientRequest(RegularRequestType requestType) {
        this.requestType = requestType;
    }
    
    public RegularRequestType getRequestType() {
        return requestType;
    }
}
