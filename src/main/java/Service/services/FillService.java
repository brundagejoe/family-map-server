package Service.services;

import DataAccess.*;
import JSONHelpers.Location;
import Model.Event;
import Model.Person;
import Model.User;
import requests.FillRequest;
import results.FillResult;

import java.sql.Connection;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class that populates the server's database with generated data for the specified user name.
 */
public class FillService extends Service {

    public FillService() throws DataAccessException {
    }

    /**
     * Populates the server's database with generated data for the specified user name.
     * The required "username" parameter must be a user already registered with the server.
     * If there is any data in the database already associated with the given user name, it is deleted.
     * The optional “generations” parameter lets the caller specify the number of generations of ancestors to be generated,
     * and must be a non-negative integer (the default is 4, which results in 31 new persons each with associated events).
     * @param r the FillRequest object that contains the http request info
     * @return the FillResult object that contains the http response info
     */
    public FillResult fill(FillRequest r) throws DataAccessException {
        FillResult result = new FillResult();

        Database db = new Database();
        Connection connection = db.getConnection();

        AuthTokenDAO aDao = new AuthTokenDAO(connection);
        EventDao eDao = new EventDao(connection);
        PersonDAO pDao = new PersonDAO(connection);
        UserDAO uDao = new UserDAO(connection);

        aDao.remove(r.getUsername());
        eDao.remove(r.getUsername());
        pDao.remove(r.getUsername());

        if (uDao.find(r.getUsername()) == null) {
            result.setMessage("Error: Username is not valid");
            result.setSuccess(false);
        }
        else {
            if (r.getGenerations() < 0) {
                result.setMessage("Error: generations must be non-negative integer. " +
                        r.getGenerations() + " is less than 0");
                result.setSuccess(false);
            }
            else {
                User user = uDao.find(r.getUsername());
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

                eDao.insert(event);

                //Recursively add people
                AtomicInteger peopleAdded = new AtomicInteger(1);
                AtomicInteger eventsAdded = new AtomicInteger(1);
                addPerson(r.getGenerations(), person, connection, peopleAdded, eventsAdded);

                result.setMessage("Successfully added " + peopleAdded + " persons and " + eventsAdded +
                        " events to the database.");
                result.setSuccess(true);
            }
        }

        db.closeConnection(true);
        return result;
    }
}
