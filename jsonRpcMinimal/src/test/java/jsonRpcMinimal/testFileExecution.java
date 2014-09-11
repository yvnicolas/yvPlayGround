package jsonRpcMinimal;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class testFileExecution {
    private static final Logger logger = LoggerFactory.getLogger(testFileExecution.class);

    @Test
    public void test() throws IOException {
        InputStream is = getClass().getResourceAsStream("/rq1");
        DynTestRqFileProcessor underTest = new DynTestRqFileProcessor("ariana.dynamease.net", is, System.out);
        int result = underTest.process();
        logger.debug(String.format("Processed %s requests", result));
    }

}
