/**
 * 
 */
package jsonRpcMinimal;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.DynCryptoServices;
import utils.DynCryptoServicesImpl;

/**
 * @author yves
 *
 */
public class DynJsonClientTest {
    
    private static final Logger logger = LoggerFactory.getLogger(DynJsonClientTest.class);
    
    public static final DynCryptoServices crypter = new DynCryptoServicesImpl();
    
    public static final String server = "http://ariana.dynamease.net/";
   

    @Test
    public void testSubId() throws URISyntaxException, IOException {
        

        // Open a connection to Monastir
        JSONSession session = new JSONSession(new URI(server + "webapp/communication"));
        String result = session.sendJson("json-subinfo", "{\"subId\":\"6\"}");
        logger.debug("result : {}", result);
    }
    
    @Test
    public void testCallVcard() throws URISyntaxException, IOException {
        JSONSession session = new JSONSession(new URI(server + "appless/communication"));
        StringBuilder request = new StringBuilder("{\"subscriberId\":\"6\",\"firstName\":\"");
        request.append(crypter.basicEncrypt("selma"));
        request.append("\",\"lastName\":\"");
        request.append(crypter.basicEncrypt("bouzar"));
        request.append("\"}");
        logger.debug("sending request : {}", request.toString());
        String result = session.sendJson("json-callContacts", request.toString());
        logger.debug("received result : {}", result);
    }
    
    
    @Test
    public void testCallNber() throws Exception {
        JSONSession session = new JSONSession(new URI(server + "appless/communication"));
        String result = session.sendJson("json", "{\"callerNumber\":\"0556692250\",\"calledNumber\":\"0278990114\"}");
        logger.debug("result : {}", result);
    }

}
