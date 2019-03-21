package ch.zuehlke.fullstack.ConnectZuehlke.domain;

import java.util.Objects;

public class SkillExperience {

    private Skill skill;
    private int experience;

    public SkillExperience(Skill skill, int experience) {
        this.skill = skill;
        this.experience = experience;
    }

    public Skill getSkill() {
        return skill;
    }

    public int getExperience() {
        return experience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillExperience that = (SkillExperience) o;
        return getExperience() == that.getExperience() &&
                Objects.equals(getSkill(), that.getSkill());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSkill(), getExperience());
    }
}
