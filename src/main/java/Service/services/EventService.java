package Service.services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.Event;
import results.EventResult;

import java.sql.Connection;
import java.util.List;

/**
 * Class that returns ALL events for ALL family members of the current user. The current user is determined from the provided auth token.
 */
public class EventService extends Service {

    public EventService() throws DataAccessException {
    }

    /**
     * eturns ALL events for ALL family members of the current user. The current user is determined from the provided auth token.
     * @param authtoken auth token string to identify user
     * @return EventResult object that contains an array of event objects for everyone in that user's family
     */
    public EventResult event(String authtoken) throws DataAccessException {
        String userName = getUsername(authtoken);

        EventResult result = new EventResult();

        Database db = new Database();
        Connection connection = db.getConnection();

        EventDao eDao = new EventDao(connection);

        List<Event> events = eDao.findFromUser(userName);

        if (events != null) {
            result.setData(events);
            result.setSuccess(true);
        }

        else {
            result.setMessage("Error: Unable to retrieve events associated with " + userName);
            result.setSuccess(false);
        }

        db.closeConnection(false);

        return result;
    }
}
