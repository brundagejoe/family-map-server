package Service.services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import results.ClearResult;

/**
 * Class that Deletes ALL data from the database, including user accounts, auth tokens, and generated person and event data.
 */
public class ClearService {
    /**
     * Deletes ALL data from the database, including user accounts, auth tokens, and generated person and event data.
     * @return the ClearResult object that contains the http response info
     */
    public ClearResult clear() {
        Database db = new Database();
        ClearResult result = new ClearResult();
        try {
            db.getConnection();
            db.clearTables();
            db.closeConnection(true);
            result.setMessage("Clear succeeded");
            result.setSuccess(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            result.setMessage("Error: " + e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }
}
