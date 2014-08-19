package jsonRpcMinimal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonRqGenerator {
    private static final Logger logger = LoggerFactory.getLogger(JsonRqGenerator.class);

    public static void main(String[] args) throws FileNotFoundException {

        String url;
        Scanner inputScanner = new Scanner(new InputStreamReader(System.in));
        Logger outputLogger = logger;
        switch (args.length) {
        case 3:
            outputLogger = LoggerFactory.getLogger(args[2]);
        case 2:
            inputScanner = new Scanner(new File(args[1]));
        case 1:
            url = args[0];
            break;

        case 0:
            System.out.println("URL?");
            url = inputScanner.nextLine();
            break;

        default:
            logger.error("Arguments expected : Url, inputfile, outputname");
            inputScanner.close();
            return;

        }
        try {
            URL serverURL = new URL(url);
            JsonRPCRequestSender.create(serverURL, inputScanner, outputLogger).process();
            
        } catch (MalformedURLException e) {
           logger.error("Malformed url {} : {}", url, e.getMessage());
        }
    }

}
