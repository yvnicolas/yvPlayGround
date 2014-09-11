/**
 * 
 */
package jsonRpcMinimal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * process a file sending each line as a request to the appropriate DYnamease server and putting all
 * responses inside an output file
 * Works on inputStream and outputstreams to enable the calling classes to manage file location
 * (intent to do classpath resources for Junit tests and real files for simulators.)
 * 
 * @author yves
 * 
 */
/**
 * @author yves
 *
 */
public class DynTestRqFileProcessor {
    
    private static final Logger logger = LoggerFactory.getLogger(DynTestRqFileProcessor.class);

    private Scanner inputScanner;
    private DataOutputStream outputStream;
    private boolean init = false;
    private DynTestJsonRequestExecutor requestExecutor;

    
    /**
     * input is a classpath relative file
     * Output is an absolute file
     * @param serverUrl
     * @param inputFileLocation
     * @param outputFileLocation
     * @throws IOException 
     */
    public DynTestRqFileProcessor (String serverUrl, InputStream input, OutputStream output) throws IOException {
        if (input==null || output == null)
            throw new IOException("DynTestRqFileProcessor called with null input or null output");
        inputScanner = new Scanner(new BufferedInputStream(input));
        outputStream = new DataOutputStream(new BufferedOutputStream(output));
        requestExecutor = new DynTestJsonRequestExecutor(serverUrl, inputScanner);
        logger.info(String.format("Test request file processor succesfully created for server %s", serverUrl));
    }
    /**
     * Launches the actual processing
     * @return the number of request that were succesfully sent and processed by the server
     */
    public int process() {
        int toReturn=0;
        while (requestExecutor.hasMore()) {
            try {
                String response = requestExecutor.processLine();
                outputStream.writeUTF(response);
                logger.debug("Received : {}", response);
                toReturn++;
            } catch (IOException e) {
               logger.debug("Exception raised processing request : {}", e.getMessage());
               if (e.getCause()!=null)
                   logger.debug("Cause  : {}", e.getCause().toString());
            }
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            logger.debug("Exception raised processing request : {}", e.getMessage());
            if (e.getCause()!=null)
                logger.debug("Cause : {}", e.getCause().getMessage());
        }
        inputScanner.close();
        init=false;
        
        return toReturn;

    }
}
