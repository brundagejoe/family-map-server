package UnitTests.Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.Person;
import results.PersonResult;
import Service.services.PersonService;
import requests.RegisterRequest;
import results.RegisterResult;
import Service.services.RegisterService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest extends ServiceTest {
    PersonService service;
    PersonResult result;
    String authtoken;

    @BeforeEach
    public void setUp() throws DataAccessException {
        RegisterService rService = new RegisterService();
        RegisterRequest rRequest = new RegisterRequest();
        rService = new RegisterService();
        rRequest = new RegisterRequest();
        rRequest.setUsername("user");
        rRequest.setPassword("password");
        rRequest.setEmail("email");
        rRequest.setFirstName("First");
        rRequest.setLastName("Last");
        rRequest.setGender('m');

        RegisterResult rResult = rService.register(rRequest);
        //eventID = rResult.get();
        Database db = new Database();
        Connection connection = db.getConnection();
        authtoken = new AuthTokenDAO(connection).find(rRequest.getUsername()).getAuthtoken();
        db.closeConnection(false);

        service = new PersonService();

    }

    @Test
    public void testSuccess() throws DataAccessException {
        result = service.person(authtoken);

        List<Person> people = result.getData();

        assertTrue(people.size() > 0);
        assertTrue(result.getSuccess());

        assertEquals("user", people.get(0).getAssociatedUsername());
    }

    @Test
    public void testFailure() throws DataAccessException {
        result = service.person("BADAUTHTOKEN");

        assertNull(result.getData());
        assertFalse(result.getSuccess());
        assertTrue(result.getMessage().toLowerCase().contains("error"));
    }
}
