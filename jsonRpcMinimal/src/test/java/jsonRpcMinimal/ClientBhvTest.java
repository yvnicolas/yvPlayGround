package jsonRpcMinimal;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;

public class ClientBhvTest {
    
 private static final Logger logger = LoggerFactory.getLogger(ClientBhvTest.class);
	
	@Test
	public void test() {
		// The remote method to call
		String method = "create";

		// The required named parameters to pass
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("first", "Georges");
		params.put("last", "Feydeau");

		// The mandatory request ID
		String id = "req-001";

		// Create a new JSON-RPC 2.0 request
		JSONRPC2Request reqOut = new JSONRPC2Request(method, params, id);

		// Serialise the request to a JSON-encoded string
		String jsonString = reqOut.toString();
		logger.debug("Json request produced : {}" , reqOut);

		// jsonString can now be dispatched to the server...

	}

}
