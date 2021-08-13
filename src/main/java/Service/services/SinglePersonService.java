package Service.services;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Model.Person;
import requests.SinglePersonRequest;
import results.SinglePersonResult;

import java.sql.Connection;

/**
 * Class that returns the single Service.Person object with the specified ID.
 */
public class SinglePersonService extends Service {

    public SinglePersonService() throws DataAccessException {
    }

    /**
     * Returns the single Service.Person object with the specified ID.
     * @param r SinglePersonRequest object that contains necessary http request info
     * @return SinglePersonResult object that contains necessary http response info
     */
    public SinglePersonResult person(SinglePersonRequest r) throws DataAccessException {
        SinglePersonResult result = new SinglePersonResult();

        Database db = new Database();
        Connection connection = db.getConnection();
        AuthTokenDAO aDao = new AuthTokenDAO(connection);
        PersonDAO pDao = new PersonDAO(connection);

        String username = getUsername(r.getAuthtoken());
        if (username == null) {
            result.setMessage("Error: Invalid auth token");
            result.setSuccess(false);
        }
        else {
            Person person = pDao.find(r.getPersonID(), username);
            if (person == null) {
                result.setMessage("Error: No person with that personID associated with " + username);
                result.setSuccess(false);
            }
            else {
                result.setAssociatedUsername(person.getAssociatedUsername());
                result.setPersonID(person.getPersonID());
                result.setFirstName(person.getFirstName());
                result.setLastName(person.getLastName());
                result.setGender(person.getGender());
                result.setFatherID(person.getFatherID());
                result.setMotherID(person.getMotherID());
                result.setSpouseID(person.getSpouseID());
                result.setSuccess(true);
            }
        }

        db.closeConnection(false);
        return result;
    }
}
