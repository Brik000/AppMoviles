package model;

public class User {
    long id;
    String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
