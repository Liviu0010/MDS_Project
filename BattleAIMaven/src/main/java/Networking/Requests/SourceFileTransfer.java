package Networking.Requests;

import Editor.Source;
import java.io.ObjectOutputStream;

public class SourceFileTransfer extends Request {

    private final Source source;
    
    public SourceFileTransfer(Source source) {
        super(RequestType.SOURCE_FILE_TRANSFER);
        this.source = source;
    }
    
    @Override
    public void execute(ObjectOutputStream outputStream) {
    }
    
    public Source getSource() {
        return source;
    }
}
