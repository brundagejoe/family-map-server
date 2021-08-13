package Handler;

import DataAccess.DataAccessException;
import requests.SingleEventRequest;
import results.SingleEventResult;
import Service.services.SingleEventService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class SingleEventHandler extends Handler implements HttpHandler {
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

                    SingleEventResult eventResult;
                    if (verifyAuthToken(authToken)) {
                        String eventID = getIDFromURL(exchange, "/event/");
                        SingleEventRequest requestObject = new SingleEventRequest();
                        requestObject.setEventID(eventID);
                        requestObject.setAuthtoken(authToken);
                        eventResult = new SingleEventService().event(requestObject);

                        if (eventResult.getSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        eventResult = new SingleEventResult();
                        eventResult.setMessage("Error: Authorization token not valid");
                        eventResult.setSuccess(false);
                    }

                    String responseData = getJSONFromObject(eventResult);
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
