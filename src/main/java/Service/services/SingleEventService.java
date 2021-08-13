package Service.services;

import DataAccess.*;
import Model.Event;
import requests.SingleEventRequest;
import results.SingleEventResult;

import java.sql.Connection;

/**
 * Class that returns the single Event object with the specified ID.
 */
public class SingleEventService extends Service {

    public SingleEventService() throws DataAccessException {
    }

    /**
     * Returns the single Event object with the specified ID.
     * @param r SingleEventRequest object that contains eventID info
     * @return SingleEventResult object that contains all info about event
     */
    public SingleEventResult event(SingleEventRequest r) throws DataAccessException {
        SingleEventResult result = new SingleEventResult();

        Database db = new Database();
        Connection connection = db.getConnection();
        AuthTokenDAO aDao = new AuthTokenDAO(connection);
        EventDao eDao = new EventDao(connection);

        String username = getUsername(r.getAuthtoken());
        if (username == null) {
            result.setMessage("Error: Invalid auth token");
            result.setSuccess(false);
        }
        else {
            Event event = eDao.find(r.getEventID(), username);
            if (event == null) {
                result.setMessage("Error: No event with that eventID associated with " + username);
                result.setSuccess(false);
            }
            else {
                result.setAssociatedUsername(event.getAssociatedUsername());
                result.setEventID(event.getEventID());
                result.setPersonID(event.getPersonID());
                result.setLatitude(event.getLatitude());
                result.setLongitude(event.getLongitude());
                result.setCountry(event.getCountry());
                result.setCity(event.getCity());
                result.setEventType(event.getEventType());
                result.setYear(event.getYear());
                result.setSuccess(true);
            }
        }

        db.closeConnection(false);
        return result;
    }
}
