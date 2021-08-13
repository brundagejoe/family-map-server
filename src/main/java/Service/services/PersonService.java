package Service.services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Model.Person;
import results.PersonResult;

import java.sql.Connection;
import java.util.List;

/**
 * Class that returns ALL family members of the current user. The current user is determined from the provided auth token.
 */
public class PersonService extends Service {
    public PersonService() throws DataAccessException {
    }

    /**
     * Returns ALL family members of the current user. The current user is determined from the provided auth token.
     * @param authtoken auth token of the user
     * @return a PersonResult object with necessary information (data, success, and message)
     */
    public PersonResult person(String authtoken) throws DataAccessException {
        String userName = getUsername(authtoken);

        PersonResult result = new PersonResult();

        Database db = new Database();
        Connection connection = db.getConnection();

        PersonDAO pDao = new PersonDAO(connection);

        List<Person> people = pDao.findFromUser(userName);

        if (people != null) {
            result.setData(people);
            result.setSuccess(true);
        }

        else {
            result.setMessage("Error: Unable to retrieve people associated with " + userName);
            result.setSuccess(false);
        }

        db.closeConnection(false);

        return result;
    }
}
