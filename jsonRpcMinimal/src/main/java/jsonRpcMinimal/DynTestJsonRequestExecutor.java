/**
 * 
 */
package jsonRpcMinimal;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
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
 * A class meant to send Json Request to a dynamease server To be used for test or simulation
 * purposes
 * 
 * Lines syntax Starting character :
 * 
 * # : comment (ignored)
 * 
 * ! <url> <header parameter> <json> : historic json dynamease
 * 
 * & <url> <method name> <json parameters> : json RPC2 requests
 * 
 * Url are relative url compared to the base server Url
 * 
 * @author yves
 *
 */
public class DynTestJsonRequestExecutor {
    private static final Logger logger = LoggerFactory.getLogger(DynTestJsonRequestExecutor.class);

    private Scanner inputScanner;
    private String serverUrl;

    private static ObjectMapper MAPPER = new ObjectMapper();

    private int currentId;

    public DynTestJsonRequestExecutor(String server, Scanner inputScanner) {
        super();
        this.inputScanner = inputScanner;
        this.serverUrl = "http://"+server;

    }

    /**
     * Tells whether end of input has been reached or not and if yes closes input.
     *
     * @return
     */
    public boolean hasMore() {
        if (inputScanner.hasNext())
            return true;
        else {
            inputScanner.close();
            return false;
        }
    }

    public String processLine() throws IOException {
        if (!this.hasMore())
            return null;
        else {
            switch (inputScanner.next()) {
            case "#":
                inputScanner.nextLine();
                return processLine();
            case "!":
                return historicJson(inputScanner.next());
            case "&":
                return jsonRPC2(inputScanner.next());
            default:
                inputScanner.nextLine();
                return processLine();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private String jsonRPC2(String relativeUrl) throws IOException {

        JSONRPC2Session jsonRpcSession;

        String methodName = inputScanner.next();
        String parameters = inputScanner.nextLine();

        JSONRPC2Request request = null;
        JSONRPC2Response response = null;
        try {
            jsonRpcSession = new JSONRPC2Session(new URL(serverUrl + relativeUrl));
            request = new JSONRPC2Request(methodName, MAPPER.readValue(parameters, Map.class), currentId++);
            response = jsonRpcSession.send(request);
            return response.toString();

            // } catch (IOException e1) {
            // logger.error("Exception processing parameters {} : {}", parameters, e1.getMessage());
        } catch (JSONRPC2SessionException e) {

            logger.error("Exception raised sending request : {}", e.getMessage());
            throw new IOException("Unable to process " + methodName + request.toJSONString(), e);
        } catch (MalformedURLException e) {
            logger.error("Invalid Url : {} ({})", serverUrl + relativeUrl, e.getMessage());
            throw new IOException("Unable to process " + methodName + request, e);
        } catch (IOException e) {
            logger.error("Exception processing parameters {} : {}", parameters, e.getMessage());
            throw new IOException("Unable to process " + methodName + request, e);
        }
    }

    private String historicJson(String relativeUrl) throws IOException {
        String header = null;
        String jsonRq = null;
        try {
            JSONSession jsonSession = new JSONSession(new URI(serverUrl+ relativeUrl));
            header = inputScanner.next();
            jsonRq = inputScanner.nextLine();
            return jsonSession.sendJson(header, jsonRq);
        } catch (URISyntaxException e) {
            logger.error("Invalid Url : {} ({})", serverUrl + relativeUrl, e.getMessage());
            throw new IOException("Unable to process " + jsonRq, e);
        } catch (IOException e) {
           logger.error("Exception raised processiong request {} {} : {}", header, jsonRq, e.getMessage());
           throw new IOException("Unable to process " + jsonRq, e);
        }
    }

  
}
