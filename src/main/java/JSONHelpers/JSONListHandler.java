package JSONHelpers;

import DataAccess.DataAccessException;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JSONListHandler {

    List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public List<String> getList(String filepath) throws DataAccessException {
        String content = null;
        try {
            content = Files.readString(Paths.get(filepath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to access file " + filepath);
        }

        Gson gson = new Gson();
        JSONListHandler result = gson.fromJson(content, JSONListHandler.class);
        return result.getData();
    }

    public List<Location> getLocations(String filepath) throws DataAccessException {
        String content = null;
        try {
            content = Files.readString(Paths.get(filepath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to access file " + filepath);
        }

        Gson gson = new Gson();
        LocationList result = gson.fromJson(content, LocationList.class);
        return result.getData();
    }
}
