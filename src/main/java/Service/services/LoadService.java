package Service.services;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import requests.LoadRequest;
import results.LoadResult;

import java.sql.Connection;
import java.util.List;

/**
 * Class that clears all data from the database (just like the /clear API),
 * and then loads the posted user, person, and event data into the database.
 */
public class LoadService {

    /**
     * Clears all data from the database (just like the /clear API), and then loads the posted user, person, and event data into the database.
     * @param r LoadRequest object that contains the http request info
     * @return the LoadResult object that contains the neccessary http response info
     */
    public LoadResult load(LoadRequest r) {
        Database db = new Database();
        LoadResult result = new LoadResult();

        try {
            Connection connection = db.getConnection();
            UserDAO uDao = new UserDAO(connection);
            PersonDAO pDao = new PersonDAO(connection);
            EventDao eDao = new EventDao(connection);
            AuthTokenDAO aDao = new AuthTokenDAO(connection);
            uDao.clear();
            pDao.clear();
            eDao.clear();
            aDao.clear();

            try {
                List<User> users = r.getUsers();
                List<Person> persons = r.getPersons();
                List<Event> events = r.getEvents();

                for (User user : users) {
                    uDao.insert(user);
                }

                for (Person person: persons) {
                    pDao.insert(person);
                }

                for (Event event : events) {
                    eDao.insert(event);
                }

                result.setMessage("Successfully added " + users.size() + " users, "
                + persons.size() + " persons, and " + events.size() + " events to the database.");
                result.setSuccess(true);

                db.closeConnection(true);
                return result;

            } catch(DataAccessException e) {
                result.setSuccess(false);
                result.setMessage("Error: unable to load request data into database");
                db.closeConnection(false);
                return result;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
}
