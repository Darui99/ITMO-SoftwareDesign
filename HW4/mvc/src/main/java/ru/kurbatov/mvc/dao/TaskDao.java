package ru.kurbatov.mvc.dao;

import ru.kurbatov.mvc.model.Task;

public interface TaskDao {
    int addTask(Task task);

    void updateStatus(int id, boolean completed);
}
