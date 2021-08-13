package UnitTests.Service;

import DataAccess.DataAccessException;
import Model.Event;
import Model.Person;
import Model.User;
import requests.LoadRequest;
import results.LoadResult;
import Service.services.LoadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoadServiceTest extends ServiceTest{
    LoadRequest request;
    LoadService service;
    LoadResult result;


    @BeforeEach
    public void setUp() throws DataAccessException {
        User user1 = new User();
        user1.setUsername("a");
        user1.setPassword("-");
        user1.setEmail("-");
        user1.setFirstName("-");
        user1.setLastName("-");
        user1.setGender('m');
        user1.setPersonID("-");

        User user2 = new User();
        user2.setUsername("b");
        user2.setPassword("-");
        user2.setEmail("-");
        user2.setFirstName("-");
        user2.setLastName("-");
        user2.setGender('m');
        user2.setPersonID("-");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        Event event1 = new Event();
        event1.setEventID("a");
        event1.setAssociatedUsername("-");
        event1.setPersonID("-");
        event1.setLatitude(1.0F);
        event1.setLongitude(1.0F);
        event1.setCountry("-");
        event1.setCity("-");
        event1.setEventType("-");
        event1.setYear(1);

        Event event2 = new Event();
        event2.setEventID("b");
        event2.setAssociatedUsername("-");
        event2.setPersonID("-");
        event2.setLatitude(1.0F);
        event2.setLongitude(1.0F);
        event2.setCountry("-");
        event2.setCity("-");
        event2.setEventType("-");
        event2.setYear(1);

        List<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);

        Person person1 = new Person();
        person1.setPersonID("a");
        person1.setAssociatedUsername("-");
        person1.setFirstName("-");
        person1.setLastName("-");
        person1.setGender('m');

        Person person2 = new Person();
        person2.setPersonID("b");
        person2.setAssociatedUsername("-");
        person2.setFirstName("-");
        person2.setLastName("-");
        person2.setGender('m');

        List<Person> people = new ArrayList<>();
        people.add(person1);
        people.add(person2);

        request = new LoadRequest(users, people, events);
        service = new LoadService();
    }

    @Test
    public void testSuccessful() throws DataAccessException {
        result = service.load(request);

        assertTrue(result.getSuccess());
        assertTrue(result.getMessage().toLowerCase(Locale.ROOT).contains("successfully added"));

    }

    @Test
    public void testFailure() throws DataAccessException {
        User user = new User();
        user.setUsername("a");
        List<User> testList = new ArrayList<>();
        testList.add(user);
        request.setUsers(testList);
        result = service.load(request);

        //Fails when not enough info is provided
        assertFalse(result.getSuccess());
        assertTrue(result.getMessage().toLowerCase().contains("error"));
    }
}
