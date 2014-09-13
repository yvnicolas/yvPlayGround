package jsonRpcMinimal;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFileExecution {
    private static final Logger logger = LoggerFactory.getLogger(TestFileExecution.class);

    @Test
    public void testariana() throws IOException {
       testFileServer("ariana.dynamease.net", "ariana", "arianaresults");
    }

    
    private void testFileServer(String server, String inputResource, String resultResource) throws IOException {
        
        // Processing request file
        InputStream is = getClass().getResourceAsStream(inputResource);
        PrintStream output = new PrintStream(new File(System.getProperty("java.io.tmpdir") + "/testInProgress"));
        logger.debug("Processing test request file {}", inputResource);
        DynTestRqFileProcessor fileProcessor = new DynTestRqFileProcessor(server, is, output);
        int result = fileProcessor.process();
        logger.debug(String.format("Processed %s requests", result));
        output.close();
        
        // checking result validity
        Scanner actual = new Scanner(new BufferedInputStream(new FileInputStream(System.getProperty("java.io.tmpdir") + "/testInProgress")));
        Scanner expected = new Scanner(getClass().getResourceAsStream(resultResource));
        
        int nberChecked=0;
        while (expected.hasNext()) {
            if (!actual.hasNext()) {
                actual.close();
                expected.close();
                fail(String.format("%s request correctly checked but %s files expected more actual results", nberChecked, resultResource));
            }
            String actualResult = actual.nextLine();
            String expectedResult = expected.nextLine();
            
           assertTrue(String.format("request %s fail. Expected result : %s -- Actual Result : %s",nberChecked, expectedResult, actualResult), expectedResult.equals(actualResult));
           nberChecked++;
        }
        
        logger.info(String.format("%s requests from test file %s successfully processed", nberChecked, inputResource));
        
    }
}
