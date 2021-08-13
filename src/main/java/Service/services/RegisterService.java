package Service.services;

import DataAccess.*;
import JSONHelpers.Location;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import requests.RegisterRequest;
import results.RegisterResult;

import java.sql.Connection;
import java.util.Random;

/**
 * Class that creates a new user account, generates 4 generations of ancestor data for the new user, logs the user in, and returns an auth token.
 */
public class RegisterService extends Service {

    public RegisterService() throws DataAccessException {
    }

    /**
     * Creates a new user account, generates 4 generations of ancestor data for the new user, logs the user in, and returns an auth token.
     * @param r the registerRequest object with http info
     * @return the result object with http response
     */
    public RegisterResult register(RegisterRequest r) throws DataAccessException {

        RegisterResult result = new RegisterResult();

        Database db = new Database();
        Connection connection = db.getConnection();

        UserDAO uDao = new UserDAO(connection);
        PersonDAO pDao = new PersonDAO(connection);
        EventDao eventDAO = new EventDao(connection);
        AuthTokenDAO aDao = new AuthTokenDAO(connection);

        if (uDao.find(r.getUsername()) != null) {
            result.setMessage("Error: Username is unavailable");
            result.setSuccess(false);
            db.closeConnection(false);
            return result;
        }

        User user = new User();

        user.setUsername(r.getUsername());
        user.setPassword(r.getPassword());
        user.setEmail(r.getEmail());
        user.setFirstName(r.getFirstName());
        user.setLastName(r.getLastName());
        user.setGender(r.getGender());
        user.setPersonID(generatePersonID());

        uDao.insert(user);

        Person person = new Person();
        person.setPersonID(user.getPersonID());
        person.setAssociatedUsername(user.getUsername());
        person.setFirstName(user.getFirstName());
        person.setLastName(user.getLastName());
        person.setGender(user.getGender());

        pDao.insert(person);

        Event event = new Event();

        Random randomGenerator = new Random();
        Location randomLocation = locations.get(randomGenerator.nextInt(locations.size()));

        event.setEventID(generateEventID());
        event.setCity(randomLocation.getCity());
        event.setCountry(randomLocation.getCountry());
        event.setLatitude(randomLocation.getLatitude());
        event.setLongitude(randomLocation.getLongitude());
        event.setPersonID(person.getPersonID());
        event.setAssociatedUsername(person.getAssociatedUsername());
        event.setEventType("birth");
        event.setYear(2000);

        eventDAO.insert(event);

        //Recursively add people
        addPerson(4, person, connection);


        AuthToken authtoken = new AuthToken();
        authtoken.setAuthtoken(generateAuthToken());
        authtoken.setUsername(user.getUsername());

        aDao.insert(authtoken);

        result.setAuthtoken(authtoken.getAuthtoken());
        result.setUsername(user.getUsername());
        result.setPersonID(user.getPersonID());
        result.setSuccess(true);

        db.closeConnection(true);

        return result;
    }
}
