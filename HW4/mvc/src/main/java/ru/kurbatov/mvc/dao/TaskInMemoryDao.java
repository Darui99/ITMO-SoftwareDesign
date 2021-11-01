package ru.kurbatov.mvc.dao;

import ru.kurbatov.mvc.model.Task;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskInMemoryDao implements TaskDao {
    private final AtomicInteger lastId = new AtomicInteger(0);
    private final List<Task> tasks = new CopyOnWriteArrayList<>();

    @Override
    public int addTask(Task task) {
        int id = lastId.incrementAndGet();
        task.setId(id);
        tasks.add(task);
        return id;
    }

    @Override
    public void updateStatus(int id, boolean completed) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setCompleted(completed);
                return;
            }
        }
    }
}
