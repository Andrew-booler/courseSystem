package model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Admin on 2016/7/4.
 */
@Entity
public class Team {
    private String id;
    private String courseId;
    private String teamName;
    private String teamDescription;
    private boolean isFull;
    private String teamleaderId;

    @Id
    @Column(name = "id", nullable = false, length = 10)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "course_id", nullable = false, length = 10)
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "team_name", nullable = true, length = 50)
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Basic
    @Column(name = "team_description", nullable = true, length = 50)
    public String getTeamDescription() {
        return teamDescription;
    }

    public void setTeamDescription(String teamDescription) {
        this.teamDescription = teamDescription;
    }

    @Basic
    @Column(name = "is_full", nullable = false)
    public boolean getIsFull() {
        return isFull;
    }

    public void setIsFull(boolean full) {
        isFull = full;
    }

    @Basic
    @Column(name = "teamleader_id", nullable = true, length = 10)
    public String getTeamleaderId() {
        return teamleaderId;
    }

    public void setTeamleaderId(String teamleaderId) {
        this.teamleaderId = teamleaderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (isFull != team.isFull) return false;
        if (id != null ? !id.equals(team.id) : team.id != null) return false;
        if (courseId != null ? !courseId.equals(team.courseId) : team.courseId != null) return false;
        if (teamName != null ? !teamName.equals(team.teamName) : team.teamName != null) return false;
        if (teamDescription != null ? !teamDescription.equals(team.teamDescription) : team.teamDescription != null)
            return false;
        if (teamleaderId != null ? !teamleaderId.equals(team.teamleaderId) : team.teamleaderId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        result = 31 * result + (teamName != null ? teamName.hashCode() : 0);
        result = 31 * result + (teamDescription != null ? teamDescription.hashCode() : 0);
        result = 31 * result + (isFull ? 1 : 0);
        result = 31 * result + (teamleaderId != null ? teamleaderId.hashCode() : 0);
        return result;
    }
}
