package jsonRpcMinimal;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Java program to simulate json Request to Dynamease Server
 * 
 * usage :
 * dynJsonSimulator [<server Url>] [stdin | input file name] [outputfile name]
 * if no argument is provided, the server url is asked on the console
 * 
 *  * Lines syntax Starting character :
 * 
 * # : comment (ignored)
 * 
 * ! <url> <header parameter> <json> : historic json dynamease
 * 
 * & <url> <method name> <json parameters> : json RPC2 requests
 * 
 * Url are relative url compared to the base server Url
 * @author Yves Nicolas
 *
 */
public class DynJsonSimulator {
    private static final Logger logger = LoggerFactory.getLogger(DynJsonSimulator.class);

    public static void main(String[] args) throws FileNotFoundException {

        String url;
        InputStream input = System.in;
        PrintStream output = System.out;
        switch (args.length) {
        case 3:
            output = new PrintStream(new File(args[2]));
        case 2:
            // if second argument is stdin, the input remains the console (to enable output to a named file but input from console)
            if (!args[1].equalsIgnoreCase("stdin"))
            input = new BufferedInputStream(new FileInputStream(new File(args[1])));
        case 1:
            url = args[0];
            break;

        case 0:
            System.out.println(" Server URL?");
            url = new Scanner(input).nextLine();
            break;

        default:
            logger.error("dynJsonSimulator [<server Url>] [stdin | input file name] [outputfile name]");
            return;

       
        }
        
        try {
            DynTestRqFileProcessor processor = new DynTestRqFileProcessor(url, input, output);
            int result = processor.process();
            logger.info(String.format("Processed %s json Request to %s", result, url));
        } catch (IOException e) {
            logger.debug(String.format("Exception raised : %s", e.toString()));
        }

    }

}
