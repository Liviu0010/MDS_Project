package Server;

/**
 * A HostMatchRequest is a ClientRequest which provides a Match object and asks
 * the master server to add the match to its list of active matches. If the
 * client is already hosting a match, it just updates the match properties.
 */
public class HostMatchRequest implements ClientRequest {
    
    private final Match match;
    
    /**
     * @param match The Match object to be used for the request.
     */
    public HostMatchRequest(Match match) {
        this.match = match;
    }
    
    /**
     * @return Match Return the match provided in the request
     */
    public Match getMatch() {
        return match;
    }
    
}
