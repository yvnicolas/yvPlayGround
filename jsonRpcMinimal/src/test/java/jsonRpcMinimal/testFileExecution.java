package jsonRpcMinimal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class testFileExecution {
    private static final Logger logger = LoggerFactory.getLogger(testFileExecution.class);

    @Test
    public void test() throws IOException {
        InputStream is = getClass().getResourceAsStream("/rq1");
        PrintStream output = new PrintStream(new File(System.getProperty("java.io.tmpdir") + "/jsonRqResult"));
        DynTestRqFileProcessor underTest = new DynTestRqFileProcessor("bengarden.dynamease.net", is, output);
        int result = underTest.process();
        logger.debug(String.format("Processed %s requests", result));
    }

}
