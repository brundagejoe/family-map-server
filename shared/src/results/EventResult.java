package results;

import Model.Event;

import java.util.List;

/**
 * Class that contains the result of the Event service
 */
public class EventResult extends Result {
    /**
     * Array of event objects
     */
    List<Event> data;

    public List<Event> getData() {
        return data;
    }

    public void setData(List<Event> data) {
        this.data = data;
    }
}
