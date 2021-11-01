package ru.kurbatov.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kurbatov.mvc.dao.TodoListDao;
import ru.kurbatov.mvc.model.Task;
import ru.kurbatov.mvc.model.TodoList;

@Controller
public class TodoListController {
    private final TodoListDao todoListDao;

    public TodoListController(TodoListDao todoListDao) {
        this.todoListDao = todoListDao;
    }

    @RequestMapping(value = "/add-list", method = RequestMethod.POST)
    public String addList(@ModelAttribute("list") TodoList todoList) {
        todoListDao.addList(todoList);
        return "redirect:/get-lists";
    }

    @RequestMapping(value = "/remove-list", method = RequestMethod.POST)
    public String removeList(@RequestParam int id) {
        todoListDao.removeList(id);
        return "redirect:/get-lists";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "redirect:/get-lists";
    }

    @RequestMapping(value = "/get-lists", method = RequestMethod.GET)
    public String getLists(ModelMap map) {
        map.addAttribute("lists", todoListDao.getLists());
        map.addAttribute("list", new TodoList());
        map.addAttribute("task", new Task());
        return "index";
    }
}
