package Handler;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.*;
import java.sql.Connection;

public class Handler {
    protected String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    protected void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);
        bw.flush();
    }

    protected static <T> T getObjectFromJSON(String data, Class<T> tClass) {
        Gson gson = new Gson();
        T t = gson.fromJson(data, tClass);
        return t;
    }

    protected String getJSONFromObject(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    protected boolean verifyAuthToken(String authToken) throws DataAccessException {
        Database db = new Database();
        Connection connection = db.getConnection();

        AuthTokenDAO aDao = new AuthTokenDAO(connection);

        if (aDao.findFromAuthToken(authToken) != null ) {
            db.closeConnection(false);
            return true;
        }

        db.closeConnection(false);
        return false;
    }

    protected String getIDFromURL(HttpExchange exchange, String beginningPath) {
        return removeBeginningPathURL(exchange, beginningPath);
    }

    protected String removeBeginningPathURL(HttpExchange exchange, String beginningPath) {
        try {
            URI comparedURI = new URI(beginningPath);
            URI inputURI = exchange.getRequestURI();
            return comparedURI.relativize(inputURI).getPath();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected int getGenerations(HttpExchange exchange, String beginningPath) {
        String path = removeBeginningPathURL(exchange, beginningPath);
        String[] paths = path.split("/");

        if (paths.length < 2) {
            return 4;
        }
        else {
            return Integer.valueOf(paths[1]);
        }
    }

    protected String getUsername(HttpExchange exchange, String beginningPath) {
        String path = removeBeginningPathURL(exchange, beginningPath);
        String[] paths = path.split("/");
        return paths[0];
    }
}
