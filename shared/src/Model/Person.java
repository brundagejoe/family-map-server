package Model;

/**
 * Class for each person represented in the Family Map
 */
public class Person {
    /**
     * Unique identifier for this person (non-empty string)
     */
    private String personID;
    /**
     * User (Username) to which this person belongs
     */
    private String associatedUsername;
    /**
     * Service.Person’s first name (non-empty string)
     */
    private String firstName;
    /**
     * Service.Person’s last name (non-empty string)
     */
    private String lastName;
    /**
     * Service.Person’s gender (Character: 'f' for female or 'm' for male)
     */
    private Character gender;
    /**
     * Service.Person ID of person’s father (possibly null)
     */
    private String fatherID;
    /**
     * Service.Person ID of person’s mother (possibly null)
     */
    private String motherID;
    /**
     * Service.Person ID of person’s spouse (possibly null)
     */
    private String spouseID;

    /**
     * Constructor with all of the parameters
     * @param personID unique person ID
     * @param associatedUsername username of the account associated with this person
     * @param firstName person's first name
     * @param lastName person's last name
     * @param gender person's gender ('m' of 'f')
     * @param fatherID the person ID of this person's father
     * @param motherID the person ID of this person's mother
     * @param spouseID the person ID of this person's spouse.
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, Character gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    /**
     * A constructor that does not require the ID's of those related to the person
     * @param personID unique person ID
     * @param associatedUsername username of the account associated with this person
     * @param firstName person's first name
     * @param lastName person's last name
     * @param gender person's gender ('m' of 'f')
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, Character gender) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }


    /**
     * Constructor for initialization that requires no parameters
     */
    public Person(){}

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Person)) {
            return false;
        }

        Person comparedPerson = (Person)obj;
        return this.personID.equals(comparedPerson.personID) &&
                this.associatedUsername.equals(comparedPerson.associatedUsername) &&
                this.firstName.equals(comparedPerson.firstName) &&
                this.lastName.equals(comparedPerson.lastName) &&
                this.gender.equals(comparedPerson.gender) &&
                this.fatherID.equals(comparedPerson.fatherID) &&
                this.motherID.equals(comparedPerson.motherID) &&
                this.spouseID.equals(comparedPerson.spouseID);
    }
}
