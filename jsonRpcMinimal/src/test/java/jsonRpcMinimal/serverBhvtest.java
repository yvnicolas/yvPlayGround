package jsonRpcMinimal;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

public class serverBhvtest {

    private static final Logger logger = LoggerFactory.getLogger(serverBhvtest.class);

    private static ServiceInterface serviceForTest = new ServiceImplementation();

    @Test
    public void severalRequests() {
        String jsonString = "{\"id\":\"req-001\",\"method\":\"create\",\"params\":{\"last\":\"Feydeau\",\"first\":\"Georges\"},\"jsonrpc\":\"2.0\"}";
        oneRequest(jsonString);
        jsonString = "{\"id\":\"req-002\",\"method\":\"retrieveFromName\",\"params\":{\"name\":\"Feydeau\"},\"jsonrpc\":\"2.0\"}";
        oneRequest(jsonString);
        jsonString = "{\"id\":\"req-003\",\"method\":\"retrieveFromName\",\"params\":{\"name\":\"Py\"},\"jsonrpc\":\"2.0\"}";
        oneRequest(jsonString);
        jsonString = "{\"id\":\"req-004\",\"method\":\"create\",\"params\":{\"last\":\"Py\",\"first\":\"Olivier\"},\"jsonrpc\":\"2.0\"}";
        oneRequest(jsonString);
        jsonString = "{\"id\":\"req-005\",\"method\":\"retrieveFromName\",\"params\":{\"name\":\"Py\"},\"jsonrpc\":\"2.0\"}";
        oneRequest(jsonString);
        jsonString = "{\"id\":\"req-006\",\"method\":\"wrong\",\"params\":{\"name\":\"Py\"},\"jsonrpc\":\"2.0\"}";
        oneRequest(jsonString);
    }

    public void oneRequest(String jsonString) {
        JSONRPC2Request reqIn = null;

        try {
            reqIn = JSONRPC2Request.parse(jsonString);

        } catch (JSONRPC2ParseException e) {
            logger.error("Exception raised : {}", e.getMessage());
            // Handle exception...
        }

        // How to extract the request data
        logger.debug("Parsed request with properties :");
        logger.debug("\tmethod     : " + reqIn.getMethod());
        logger.debug("\tparameters : " + reqIn.getNamedParams());
        logger.debug("\tid         : " + reqIn.getID() + "\n\n");

        // Process request...
        String result;
        JSONRPC2Response respOut;
        try {
            result = process(reqIn, serviceForTest);
            respOut = new JSONRPC2Response(result, reqIn.getID());
        } catch (rpcException e) {
            respOut = new JSONRPC2Response(new JSONRPC2Error(001, e.getMessage()), reqIn.getID());
        }

        // Serialise response to JSON-encoded string
        jsonString = respOut.toString();

        logger.debug("Produced response : {}", jsonString);
    }

    private String process(JSONRPC2Request reqIn, Object service) throws rpcException {
        // TODO Auto-generated method stub
        Class<?> classe = service.getClass();
        Class<?>[] argTypes = { String.class, String.class };

        Object result = null;
        try {
            Method toInvoke = getClassMethodByName(reqIn.getMethod(), classe);

            // TODO : le traitement précédent ne traite pas correctement l'ordre des arguments.
            // Assez pour le sandbox, pour se rendre compte de comment ca marche mais à corriger si
            // on utilise cette implémentation
            Object[] parameters = reqIn.getNamedParams().values().toArray();
            result = toInvoke.invoke(serviceForTest, parameters);

        } catch (NoSuchMethodException | SecurityException e) {
            logger.error("Wrong Method : {}", reqIn.getMethod());
            throw new rpcException("Wrong Method invokation", e);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.error("Exception invoking method {} : {}", reqIn.getMethod(), e.getMessage());
            throw new rpcException("Error in method invokation", e);
        }
        if (result != null)
            return result.toString();
        else
            return null;
    }

    private Method getClassMethodByName(String methodName, Class<?> classe) throws NoSuchMethodException {

        for (Method method : classe.getMethods()) {
            if (method.getName().equals(methodName))
                return method;
        }
        throw new NoSuchMethodException();
    }
}
