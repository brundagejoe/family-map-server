package Handler;

import DataAccess.DataAccessException;
import requests.SinglePersonRequest;
import results.SinglePersonResult;
import Service.services.SinglePersonService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class SinglePersonHandler extends Handler implements HttpHandler {
    /**
     * Handle the given request and generate an appropriate response.
     * See {@link HttpExchange} for a description of the steps
     * involved in handling an exchange.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws NullPointerException if exchange is {@code null}
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                Headers reqHeaders = exchange.getRequestHeaders();

                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");

                    SinglePersonResult personResult;
                    if (verifyAuthToken(authToken)) {
                        String personID = getIDFromURL(exchange, "/person/");
                        SinglePersonRequest requestObject = new SinglePersonRequest();
                        requestObject.setPersonID(personID);
                        requestObject.setAuthtoken(authToken);
                        personResult = new SinglePersonService().person(requestObject);

                        if (personResult.getSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        personResult = new SinglePersonResult();
                        personResult.setMessage("Error: Authorization token not valid");
                        personResult.setSuccess(false);
                    }

                    String responseData = getJSONFromObject(personResult);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(responseData, respBody);
                    respBody.close();
                    exchange.close();

                }
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
