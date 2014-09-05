/**
 * 
 */
package jsonRpcMinimal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;



/**
 * 
 * A class adapted from JSONRPC2 session to send Json content for dynamease tests
 * 
 * @author yves
 *
 */
public class JSONSession {


    /**
     * The server URL, which protocol must be HTTP or HTTPS.
     *
     * <p>
     * Example URL: "http://jsonrpc.example.com:8080"
     */
    private URI uri;

    private static final HttpClient httpclient = HttpClientBuilder.create().build();

    /**
     * Creates a new client session to a JSON-RPC 2.0 server at the specified URL. Uses a default
     * {@link JSONRPC2SessionOptions} instance.
     *
     * @param url
     *            The server URL, e.g. "http://jsonrpc.example.com:8080". Must not be {@code null}.
     */
    public JSONSession(final URI uri) {

        if (!uri.getScheme().equalsIgnoreCase("http") && !uri.getScheme().equalsIgnoreCase("https"))
            throw new IllegalArgumentException("The URL protocol must be HTTP or HTTPS");

        this.uri = uri;

    }

    /**
     * Gets the server URL.
     *
     * @return The server URL.
     */
    public URI getURi() {

        return uri;
    }

    /**
     * Sets the JSON-RPC 2.0 server URL.
     *
     * @param url
     *            The server URL. Must not be {@code null}.
     */
    public void setURI(final URI uri) {

        if (uri == null)
            throw new IllegalArgumentException("The server URI must not be null");

        this.uri = uri;
    }

    /**
     * Send the request in a post
     * 
     * @param header
     *            : will be added post application/ in the Content-Type header
     * @param request
     *            : the Json to be sent as a string.
     * @return
     * @throws IOException
     */
    public String sendJson(String header, String request) throws IOException {

        InputStream inputStream = null;
        String result = "";

        HttpPost httpPost = new HttpPost(uri);

        StringEntity se = new StringEntity(request);
        httpPost.setEntity(se);
        
        // httpPost.setHeader("Accept", "application/json-callContacts");
        httpPost.setHeader("Content-Type", "application/" + header);
        HttpResponse httpResponse = httpclient.execute(httpPost);
        
        inputStream = httpResponse.getEntity().getContent();
        if (inputStream != null) {
            result = convertInputStreamToString(inputStream);
            inputStream.close();
        }
        return result;
    }

    private String convertInputStreamToString(InputStream is) throws IOException {

        // Read the response content
        StringBuilder responseText = new StringBuilder();

        BufferedReader input = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        String line;

        while ((line = input.readLine()) != null) {
            responseText.append(line);
            responseText.append(System.getProperty("line.separator"));
        }

        input.close();

        return responseText.toString();
    }

}
