package Service.services;

import DataAccess.*;
import JSONHelpers.JSONListHandler;
import JSONHelpers.Location;
import Model.AuthToken;
import Model.Event;
import Model.Person;

import java.sql.Connection;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Service {

    protected List<String> fnames;// = new JSONListHandler().getList("json/fnames.json");
    protected List<String> mnames;//= new JSONListHandler().getList("json/mnames.json");
    protected List<String> snames;// = new JSONListHandler().getList("json/snames.json");
    protected List<Location> locations;// = new JSONListHandler().getLocations("json/locations.json");

    public Service() throws DataAccessException {
        try {
            fnames = new JSONListHandler().getList("json/fnames.json");
            mnames = new JSONListHandler().getList("json/mnames.json");
            snames = new JSONListHandler().getList("json/snames.json");
            locations = new JSONListHandler().getLocations("json/locations.json");
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error: Unable to load in example data for creation of people.");
        }
    }

    protected static String generateAuthToken() {
        return UUID.randomUUID().toString();
    }
    protected static String generatePersonID() {
        return UUID.randomUUID().toString();
    }
    protected static String generateEventID() {
        return UUID.randomUUID().toString();
    }

    protected String getUsername(String authToken) throws DataAccessException {
        Database db = new Database();
        Connection connection = db.getConnection();

        AuthTokenDAO aDao = new AuthTokenDAO(connection);

        AuthToken authTokenModel = aDao.findFromAuthToken(authToken);

        if (authTokenModel != null) {
            db.closeConnection(false);
            return authTokenModel.getUsername();
        }

        db.closeConnection(false);
        return null;
    }

    protected void addPerson(int generationsToCreate, Person child, Connection connection) throws DataAccessException {
        if (generationsToCreate < 1) {
            return;
        }
        //Create a person's parents
        Person father = new Person();
        Person mother = new Person();

        //Create events for that person
        Random randomGenerator = new Random();

        father.setPersonID(generatePersonID());
        father.setAssociatedUsername(child.getAssociatedUsername());
        father.setFirstName(mnames.get(randomGenerator.nextInt(mnames.size())));
        father.setLastName(child.getLastName());
        father.setGender('m');

        mother.setPersonID(generatePersonID());
        mother.setAssociatedUsername(child.getAssociatedUsername());
        mother.setFirstName(fnames.get(randomGenerator.nextInt(fnames.size())));
        mother.setLastName(snames.get(randomGenerator.nextInt(snames.size())));
        mother.setGender('f');
        mother.setSpouseID(father.getPersonID());

        father.setSpouseID(mother.getPersonID());

        child.setFatherID(father.getPersonID());
        child.setMotherID(mother.getPersonID());

        PersonDAO pDao = new PersonDAO(connection);

        pDao.update(child);
        pDao.insert(father);
        pDao.insert(mother);

        EventDao eDao = new EventDao(connection);

        Event childBirth = eDao.findType("birth", child.getPersonID());

        Event parentEvent = new Event();

        Location randomLocation = locations.get(randomGenerator.nextInt(locations.size()));

        parentEvent.setAssociatedUsername(father.getAssociatedUsername());
        parentEvent.setLatitude(randomLocation.getLatitude());
        parentEvent.setLongitude(randomLocation.getLongitude());
        parentEvent.setCountry(randomLocation.getCountry());
        parentEvent.setCity(randomLocation.getCity());


        parentEvent.setEventType("birth");

        //Father's birth
        parentEvent.setEventID(generateEventID());
        parentEvent.setPersonID(father.getPersonID());
        parentEvent.setYear(childBirth.getYear() - 30 - randomGenerator.nextInt(5));
        eDao.insert(parentEvent);

        //Mother's birth
        parentEvent.setEventID(generateEventID());
        parentEvent.setPersonID(mother.getPersonID());
        parentEvent.setYear(childBirth.getYear() - 30 - randomGenerator.nextInt(5));
        eDao.insert(parentEvent);

        parentEvent.setEventType("marriage");

        //Father's marriage
        parentEvent.setEventID(generateEventID());
        parentEvent.setPersonID(father.getPersonID());
        parentEvent.setYear(childBirth.getYear() - 1 - randomGenerator.nextInt(5));
        eDao.insert(parentEvent);

        //Mother's marriage
        parentEvent.setEventID(generateEventID());
        parentEvent.setPersonID(mother.getPersonID());
        eDao.insert(parentEvent);

        parentEvent.setEventType("death");

        //Father's death
        parentEvent.setEventID(generateEventID());
        parentEvent.setPersonID(father.getPersonID());
        parentEvent.setYear(sanitizeDeathDate(childBirth.getYear() + 40 + randomGenerator.nextInt(5)));
        eDao.insert(parentEvent);

        //Mother's death
        parentEvent.setEventID(generateEventID());
        parentEvent.setPersonID(mother.getPersonID());
        parentEvent.setYear(sanitizeDeathDate(childBirth.getYear() + 40 + randomGenerator.nextInt(5)));
        eDao.insert(parentEvent);

        addPerson(generationsToCreate - 1, father, connection);
        addPerson(generationsToCreate - 1, mother, connection);
    }

    /**
     * Same as other addPerson class, but this one keeps AtomicIntegers that keep track of how many people and events are added
     * @param generationsToCreate number of generations to recursively create
     * @param child Person object of the child (two parents will be created)
     * @param connection connection to database
     * @param personCreatedCount count of people created that is updated
     * @param eventCreatedCount count of events created that is updated
     * @throws DataAccessException
     */
    protected void addPerson(int generationsToCreate, Person child, Connection connection, AtomicInteger personCreatedCount, AtomicInteger eventCreatedCount) throws DataAccessException {
        if (generationsToCreate < 1) {
            return;
        }
        //Create a person's parents
        Person father = new Person();
        Person mother = new Person();

        //Create events for that person
        Random randomGenerator = new Random();

        father.setPersonID(generatePersonID());
        father.setAssociatedUsername(child.getAssociatedUsername());
        father.setFirstName(mnames.get(randomGenerator.nextInt(mnames.size())));
        father.setLastName(child.getLastName());
        father.setGender('m');

        mother.setPersonID(generatePersonID());
        mother.setAssociatedUsername(child.getAssociatedUsername());
        mother.setFirstName(fnames.get(randomGenerator.nextInt(fnames.size())));
        mother.setLastName(snames.get(randomGenerator.nextInt(snames.size())));
        mother.setGender('f');
        mother.setSpouseID(father.getPersonID());

        father.setSpouseID(mother.getPersonID());

        child.setFatherID(father.getPersonID());
        child.setMotherID(mother.getPersonID());

        PersonDAO pDao = new PersonDAO(connection);

        pDao.update(child);
        pDao.insert(father);
        personCreatedCount.incrementAndGet();
        pDao.insert(mother);
        personCreatedCount.incrementAndGet();

        EventDao eDao = new EventDao(connection);

        Event childBirth = eDao.findType("birth", child.getPersonID());

        Event parentEvent = new Event();

        Location randomLocation = locations.get(randomGenerator.nextInt(locations.size()));

        parentEvent.setAssociatedUsername(father.getAssociatedUsername());
        parentEvent.setLatitude(randomLocation.getLatitude());
        parentEvent.setLongitude(randomLocation.getLongitude());
        parentEvent.setCountry(randomLocation.getCountry());
        parentEvent.setCity(randomLocation.getCity());


        parentEvent.setEventType("birth");

        //Father's birth
        parentEvent.setEventID(generateEventID());
        parentEvent.setPersonID(father.getPersonID());
        parentEvent.setYear(childBirth.getYear() - 30 - randomGenerator.nextInt(5));
        eDao.insert(parentEvent);
        eventCreatedCount.incrementAndGet();

        //Mother's birth
        parentEvent.setEventID(generateEventID());
        parentEvent.setPersonID(mother.getPersonID());
        parentEvent.setYear(childBirth.getYear() - 30 - randomGenerator.nextInt(5));
        eDao.insert(parentEvent);
        eventCreatedCount.incrementAndGet();

        parentEvent.setEventType("marriage");

        //Father's marriage
        parentEvent.setEventID(generateEventID());
        parentEvent.setPersonID(father.getPersonID());
        parentEvent.setYear(childBirth.getYear() - 1 - randomGenerator.nextInt(5));
        eDao.insert(parentEvent);
        eventCreatedCount.incrementAndGet();

        //Mother's marriage
        parentEvent.setEventID(generateEventID());
        parentEvent.setPersonID(mother.getPersonID());
        eDao.insert(parentEvent);
        eventCreatedCount.incrementAndGet();

        parentEvent.setEventType("death");

        //Father's death
        parentEvent.setEventID(generateEventID());
        parentEvent.setPersonID(father.getPersonID());
        parentEvent.setYear(sanitizeDeathDate(childBirth.getYear() + 40 + randomGenerator.nextInt(5)));
        eDao.insert(parentEvent);
        eventCreatedCount.incrementAndGet();

        //Mother's death
        parentEvent.setEventID(generateEventID());
        parentEvent.setPersonID(mother.getPersonID());
        parentEvent.setYear(sanitizeDeathDate(childBirth.getYear() + 40 + randomGenerator.nextInt(5)));
        eDao.insert(parentEvent);
        eventCreatedCount.incrementAndGet();

        addPerson(generationsToCreate - 1, father, connection, personCreatedCount, eventCreatedCount);
        addPerson(generationsToCreate - 1, mother, connection, personCreatedCount, eventCreatedCount);
    }

    /**
     * Returns the death date if year has occurred, returns 2020 if not
     * @param date input date
     * @return the year of death
     */
    protected int sanitizeDeathDate(int date) {
        if (date > 2021) {
            return 2020;
        }
        else {
            return date;
        }
    }
}
