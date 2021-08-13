package Handler;

import DataAccess.DataAccessException;
import requests.RegisterRequest;
import results.RegisterResult;
import Service.services.RegisterService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class RegisterHandler extends Handler implements HttpHandler {
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
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {

                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                RegisterRequest registerRequest = getObjectFromJSON(reqData, RegisterRequest.class);

                RegisterService registerService = new RegisterService();

                RegisterResult registerResult;

                registerResult = registerService.register(registerRequest);
                if (registerResult.getSuccess() == true) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                String respData = getJSONFromObject(registerResult);


                OutputStream respBody = exchange.getResponseBody();
                writeString(respData, respBody);
                respBody.close();
                exchange.close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                RegisterResult registerResult = new RegisterResult();
                registerResult.setMessage("Error: Bad Request");
                registerResult.setSuccess(false);
                String responseData = getJSONFromObject(registerResult);
                OutputStream respBody = exchange.getResponseBody();
                writeString(responseData, respBody);
                respBody.close();
                exchange.close();


            }
        } catch (IOException | DataAccessException e) {
            e.printStackTrace();
        }
    }
}
