package me.sofianehamadi.tp1simpleapp.models;

import java.io.Serializable;
import java.util.Date;

public class UserRepositoryDetails implements Serializable {
    private String projectName;
    private String description;
    private String language;
    private String createAt;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == "null") {
            this.description = "No description";
        }
        else {
            this.description = description;
        }
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
