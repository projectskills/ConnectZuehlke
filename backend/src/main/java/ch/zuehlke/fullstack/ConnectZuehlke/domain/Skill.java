package ch.zuehlke.fullstack.ConnectZuehlke.domain;

public class Skill {

    private int id;
    private String name;
    private int experience;

    public Skill(int id, String name, int experience) {
        this.id = id;
        this.name = name;
        this.experience = experience;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
