package ru.kurbatov.mvc.dao;

import ru.kurbatov.mvc.model.Task;
import ru.kurbatov.mvc.model.TodoList;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TodoListInMemoryDao implements TodoListDao {
    private final AtomicInteger lastId = new AtomicInteger(0);
    private final List<TodoList> lists = new CopyOnWriteArrayList<>();

    @Override
    public int addList(TodoList list) {
        int id = lastId.incrementAndGet();
        list.setId(id);
        lists.add(list);
        return id;
    }

    @Override
    public void removeList(int id) {
        for (TodoList todoList : lists) {
            if (todoList.getId() == id) {
                lists.remove(todoList);
                return;
            }
        }
    }

    @Override
    public List<TodoList> getLists() {
        return List.copyOf(lists);
    }

    @Override
    public void addTaskToList(int id, Task task) {
        for (TodoList todoList : lists) {
            if (todoList.getId() == id) {
                todoList.addTask(task);
                return;
            }
        }
    }
}
