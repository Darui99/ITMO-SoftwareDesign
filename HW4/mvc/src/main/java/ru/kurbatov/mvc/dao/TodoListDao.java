package ru.kurbatov.mvc.dao;

import ru.kurbatov.mvc.model.Task;
import ru.kurbatov.mvc.model.TodoList;

import java.util.List;

public interface TodoListDao {
    int addList(TodoList list);

    void removeList(int id);

    List<TodoList> getLists();

    void addTaskToList(int id, Task task);
}
