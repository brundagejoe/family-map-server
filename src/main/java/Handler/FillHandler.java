package Handler;

import DataAccess.DataAccessException;
import requests.FillRequest;
import results.FillResult;
import Service.services.FillService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class FillHandler extends Handler implements HttpHandler {
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
    public void handle(HttpExchange exchange) {
        FillResult result;

        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                int generations = getGenerations(exchange, "/fill/");
                String username = getUsername(exchange, "/fill/");

                FillRequest requestObj = new FillRequest();
                requestObj.setGenerations(generations);
                requestObj.setUsername(username);

                result = new FillService().fill(requestObj);

                if (result.getSuccess() == true) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                }

            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                result = new FillResult();
                result.setMessage("Error: Bad request");
                result.setSuccess(false);
            }

            String responseData = getJSONFromObject(result);

            OutputStream respBody = exchange.getResponseBody();
            writeString(responseData, respBody);
            respBody.close();
            exchange.close();
        } catch (IOException | DataAccessException e) {
            result = new FillResult();
            result.setMessage("Error: Internal server error");
            result.setSuccess(false);

            String responseData = getJSONFromObject(result);

            OutputStream respBody = exchange.getResponseBody();
            try {
                writeString(responseData, respBody);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            try {
                respBody.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            exchange.close();
            e.printStackTrace();
            return;
        }
    }
}
