package jsonRpcMinimal;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceImplementation implements ServiceInterface {
    private static final Logger logger = LoggerFactory.getLogger(ServiceImplementation.class);
    
    private Map<String, String> registeredPersons = new HashMap<>();

    public Person retrieveFromName(String name) {
        if (registeredPersons.containsKey(name))
        return( new Person(registeredPersons.get(name), name));
        else
            return null;
        
    }

    public void create(String first, String last) {
       registeredPersons.put(last, first);
       logger.debug("Registered persons : {} {}", first, last);
    }

}
