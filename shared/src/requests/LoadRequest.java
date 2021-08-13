package requests;

import Model.Event;
import Model.Person;
import Model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Class for the request body of the load service
 */
public class LoadRequest {
    private List<User> users;
    private List<Person> persons;
    private List<Event> events;

    public LoadRequest(List<User> users, List<Person> persons, List<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}


