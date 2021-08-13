package UnitTests.DAO;

import DataAccess.Database;
import DataAccess.PersonDAO;
import DataAccess.DataAccessException;
import Model.Event;
import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDAOTest {
    private Database db;
    private Person bestPerson;
    private PersonDAO pDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestPerson = new Person("a1", "b1", "c", "d", 'm', "f1", "m1", "s1");
        Connection connection = db.getConnection();
        db.clearTables();
        pDao = new PersonDAO(connection);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(true);
    }

    @Test
    public void insertSuccess() throws DataAccessException {
        pDao.insert(bestPerson);
        Person testPerson = pDao.find(bestPerson.getPersonID());
        assertEquals(bestPerson, testPerson);
    }

    @Test
    public void insertFail() throws DataAccessException {
        pDao.insert(bestPerson);
        assertThrows(DataAccessException.class, () -> pDao.insert(bestPerson));
    }

    @Test
    public void updateSuccess() throws DataAccessException {
        pDao.insert(bestPerson);

        bestPerson.setFatherID("f1");
        assertDoesNotThrow(() -> pDao.update(bestPerson));

        bestPerson.setFatherID("f2");
        assertDoesNotThrow(() -> pDao.update(bestPerson));

        bestPerson.setMotherID("m1");
        assertDoesNotThrow(() -> pDao.update(bestPerson));

        bestPerson.setSpouseID("s1");
        assertDoesNotThrow(() -> pDao.update(bestPerson));

        Person testPerson = pDao.find(bestPerson.getPersonID());
        assertEquals(bestPerson, testPerson);
    }

    @Test
    public void updateAlternative() throws DataAccessException {
        //make sure making changes to person not there does not crash
        bestPerson.setFatherID("f1");
        assertDoesNotThrow(() -> pDao.update(bestPerson));

        bestPerson.setFatherID("f2");
        assertDoesNotThrow(() -> pDao.update(bestPerson));

        bestPerson.setMotherID("m1");
        assertDoesNotThrow(() -> pDao.update(bestPerson));

        bestPerson.setSpouseID("s1");
        assertDoesNotThrow(() -> pDao.update(bestPerson));
    }

    @Test //Checks all find methods
    void findSuccess() throws DataAccessException {
        pDao.insert(bestPerson);

        Person firstPerson = pDao.find(bestPerson.getPersonID());
        Person secondPerson = pDao.find(bestPerson.getPersonID(), bestPerson.getAssociatedUsername());

        assertNotNull(firstPerson);
        assertNotNull(secondPerson);

        assertEquals(bestPerson, firstPerson);
        assertEquals(bestPerson, secondPerson);
    }

    @Test
    void findFail() throws DataAccessException {
        assertNull(pDao.find(bestPerson.getPersonID()));
        assertNull(pDao.find(bestPerson.getPersonID(), bestPerson.getAssociatedUsername()));
    }

    @Test//test both remove methods
    public void removeSuccess() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.remove(bestPerson);
        assertNull(pDao.find(bestPerson.getPersonID()));

        pDao.insert(bestPerson);
        pDao.remove(bestPerson.getPersonID());
        assertNull(pDao.find(bestPerson.getAssociatedUsername()));
    }

    @Test//test both remove methods
    public void removeAlternative() throws DataAccessException {
        assertDoesNotThrow(() -> pDao.remove(bestPerson));
        assertDoesNotThrow(() -> pDao.remove(bestPerson.getAssociatedUsername()));
    }

    @Test
    public void clearSuccess() throws DataAccessException {
        Person person1 = new Person("a1", "b1", "c1", "d1", 'm', "f1", "m1", "s1");
        Person person2 = new Person("a2", "b2", "c2", "d2", 'm', "f2", "m2", "s2");
        pDao.insert(person1);
        pDao.insert(person2);
        pDao.clear();
        assertNull(pDao.find(person1.getPersonID()));
        assertNull(pDao.find(person2.getPersonID()));
    }

    @Test
    public void clearAlternative() throws DataAccessException {
        pDao.clear();
        assertNull(pDao.find(bestPerson.getPersonID()));
    }

    @Test
    void findFromUserTest() throws DataAccessException {
        Person firstPerson = new Person("a1", "b1", "c", "d", 'm', "f1", "m1", "s1");
        Person secondPerson = new Person("a2", "b1", "c", "d", 'm', "f1", "m1", "s1");

        List<Person> bestPeople = new ArrayList<>();
        bestPeople.add(firstPerson);
        bestPeople.add(secondPerson);

        pDao.insert(firstPerson);
        pDao.insert(secondPerson);

        List<Person> people = pDao.findFromUser(firstPerson.getAssociatedUsername());

        assertNotNull(people);
        assertEquals(bestPeople, people);
    }

    @Test
    void findFromUserTestAlternative() throws DataAccessException {
        assertDoesNotThrow(() -> pDao.findFromUser(bestPerson.getAssociatedUsername()));
    }


}
