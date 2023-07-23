package cs211.project.models;

/**
 * You must remove this model from your project
 */
public class Movie {
    private String name;
    private int length; // in seconds

    public Movie(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public int getLengthInMinutes() {
        return length / 60;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", length= " + getLengthInMinutes() + " minutes " + (length % 60) + " seconds" +
                '}';
    }
}
