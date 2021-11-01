package ru.kurbatov.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kurbatov.mvc.dao.TaskDao;
import ru.kurbatov.mvc.dao.TodoListDao;
import ru.kurbatov.mvc.model.Task;

@Controller
public class TaskController {
    private final TaskDao taskDao;
    private final TodoListDao todoListDao;

    public TaskController(TaskDao taskDao, TodoListDao todoListDao) {
        this.taskDao = taskDao;
        this.todoListDao = todoListDao;
    }

    @RequestMapping(value = "/add-task", method = RequestMethod.POST)
    public String addTask(@ModelAttribute("task") Task task, @RequestParam int id) {
        taskDao.addTask(task);
        todoListDao.addTaskToList(id, task);
        return "redirect:/get-lists";
    }

    @RequestMapping(value = "/update-task", method = RequestMethod.POST)
    public String updateStatus(@RequestParam int id, @RequestParam boolean completed) {
        taskDao.updateStatus(id, completed);
        return "redirect:/get-lists";
    }
}
