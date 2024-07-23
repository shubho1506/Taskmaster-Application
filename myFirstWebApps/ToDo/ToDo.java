package com.learnSpringBoot.myFirstWebApps.ToDo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

//database(SQL)
//static list of todos => database
@Entity
public class ToDo {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String name;
    @Column
    private LocalDate targetDate;
    @Column
    @Size(min = 10,message = "please enter atleast 10 characters")
    private String description;
    @Column
    private String done;

    public ToDo() {
    }

    public ToDo(int id, String name, LocalDate targetDate, String description, String done) {
        this.id = id;
        this.name = name;
        this.targetDate = targetDate;
        this.description = description;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "ToDo {" +
                "id =" + id +
                ", name ='" + name + '\'' +
                ", targetDate =" + targetDate +
                ", description ='" + description + '\'' +
                ", done ='" + done + '\'' +
                '}';
    }
}
