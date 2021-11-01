package ru.kurbatov.mvc.model;

import java.util.ArrayList;
import java.util.List;

public class TodoList {
    private int id;
    private String name;
    private List<Task> tasks;

    public TodoList() {
        tasks = new ArrayList<>();
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

    public List<Task> getTasks() {
        return List.copyOf(tasks);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
}
