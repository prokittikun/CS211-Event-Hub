package cs211.project.models;

public class Person {
    private String fullName;
    private String firstName;
    private String lastName;
    private String pathToProfile;

    public String getFullName() {
        return fullName;
    }

    public void setFullName() {
        this.fullName = this.firstName + " " + this.lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean setFirstName(String firstName) {
        if (firstName != null && !firstName.isEmpty()) {
            this.firstName = firstName;
            setFullName();
            return true;
        }
        return false;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean setLastName(String lastName) {
        if (lastName != null && !lastName.isEmpty()) {
            this.lastName = lastName;
            setFullName();
            return true;
        }
        return false;
    }

    public String getProfilePicture() {
        return pathToProfile;
    }

    public boolean setProfilePicture(String pathToProfile) {
        if (pathToProfile != null && !pathToProfile.isEmpty()) {
            this.pathToProfile = pathToProfile;
            return true;
        }
        return false;
    }
}

