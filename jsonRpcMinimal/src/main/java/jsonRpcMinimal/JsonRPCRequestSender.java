/**
 * 
 */
package jsonRpcMinimal;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;

/**
 * A class to send JsonRPC request and get the result Input taken from console or from a file Each
 * line of the input are assumed to be <method name><params as a Json String> Example :
 * subscriberSignin { "email" : "gfeydeau@theatre.com", "password" : "dindon"}
 * 
 * @author yves
 *
 */
public class JsonRPCRequestSender {
    private static final Logger logger = LoggerFactory.getLogger(JsonRPCRequestSender.class);

    private Scanner inputScanner;
    private Logger outputLogger;

    private static ObjectMapper MAPPER = new ObjectMapper();

    private JSONRPC2Session jsonRpcSession;

    private int currentId = 1;

    private JsonRPCRequestSender(Scanner inputScanner, Logger outputLogger) {
        super();
        this.inputScanner = inputScanner;
        this.outputLogger = outputLogger;
    }

    @SuppressWarnings("unchecked")
    public void process() {

        while (inputScanner.hasNext()) {
            String methodName = inputScanner.next();
            String parameters = inputScanner.nextLine();

            JSONRPC2Request request = null;
            JSONRPC2Response response = null;
            try {
                request = new JSONRPC2Request(methodName, MAPPER.readValue(parameters, Map.class), currentId++);
                response = jsonRpcSession.send(request);

            } catch (IOException e1) {
                logger.error("Exception processing parameters {} : {}", parameters, e1.getMessage());
            } catch (JSONRPC2SessionException e) {

              logger.error("Exception raised sending request : {}", e.getMessage());
            }

            outputLogger.info("Request sent : {}", request.toJSONString());
            outputLogger.info("Response received : {}", response.toJSONString());
        }
    }
    
    private void init(URL url) {
     // Create new JSON-RPC 2.0 client session
        jsonRpcSession = new JSONRPC2Session(url);
    }
    
    public static JsonRPCRequestSender create(URL url, Scanner inputScanner, Logger outputLogger) {
        JsonRPCRequestSender toReturn = new JsonRPCRequestSender(inputScanner, outputLogger);
        toReturn.init(url);
        return toReturn;
        
    }
}
