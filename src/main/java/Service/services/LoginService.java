package Service.services;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Model.AuthToken;
import Model.User;
import requests.LoginRequest;
import results.LoginResult;

import java.sql.Connection;

/**
 * Class that logs in the user and returns an auth token.
 */
public class LoginService extends Service {

    public LoginService() throws DataAccessException {
    }

    /**
     * Logs in the user and returns an auth token.
     * @param r the LoginRequest object with http request info
     * @return the LoginResult object with http response info
     */
    public LoginResult login(LoginRequest r) {
        Database db = new Database();
        LoginResult result = new LoginResult();

        try {
            Connection connection = db.getConnection();
            UserDAO uDao = new UserDAO(connection);
            User user = uDao.find(r.getUsername(), r.getPassword());
            if (user != null) {
                AuthToken authtoken = new AuthToken();
                authtoken.setAuthtoken(generateAuthToken());
                authtoken.setUsername(user.getUsername());
                AuthTokenDAO aDao = new AuthTokenDAO(connection);
                aDao.insert(authtoken);

                result.setAuthtoken(authtoken.getAuthtoken());
                result.setUsername(user.getUsername());
                result.setPersonID(user.getPersonID());
                result.setSuccess(true);
            }
            else {
                result.setSuccess(false);
                result.setMessage("Error: Login request failed. Username or password is incorrect");
            }
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
}
