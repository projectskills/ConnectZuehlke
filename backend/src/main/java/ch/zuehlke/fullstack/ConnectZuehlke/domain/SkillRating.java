package ch.zuehlke.fullstack.ConnectZuehlke.domain;

public class SkillRating {
    private Skill skill;
    private double rating;

    public SkillRating(Skill skill, double rating) {
        this.skill = skill;
        this.rating = rating;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
