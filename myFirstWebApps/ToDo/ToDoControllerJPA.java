package com.learnSpringBoot.myFirstWebApps.ToDo;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes("name")
public class ToDoControllerJPA {

    public ToDoControllerJPA(ToDoRepository todoRepository, ToDoService toDoService) {
        super();
        this.todoRepository = todoRepository;
        this.toDoService = toDoService;
    }

    private ToDoRepository todoRepository;
    private ToDoService toDoService;
    private String getLoggedInUsername(ModelMap model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    //list-todos
    @RequestMapping("list-todos")
    public String listAllTodos(ModelMap model) {
        String username = getLoggedInUsername(model);
        List<ToDo> todos = todoRepository.findByName(username);
        model.addAttribute("todos", todos);
        return "listToDos";
    }
    
    @RequestMapping(value = "/add-todo", method = RequestMethod.GET)
    public String showAddNewTodoPage(ModelMap model){
        ToDo toDo = new ToDo(0,getLoggedInUsername(model),LocalDate.now().plusYears(1),"","Not Done");
        model.put("todo",toDo);
        return "add-todo";
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String addNewTodo(ModelMap model, @Valid @ModelAttribute("todo") ToDo toDo, BindingResult result){
        if(result.hasErrors()){
            return "redirect:/add-todo";
        }
        String username = getLoggedInUsername(model);
        toDo.setName(username);
        todoRepository.save(toDo);
        return "redirect:/list-todos";
    }

    @RequestMapping(value = "/delete-todo")
    public String deleteTodo(@RequestParam int id){
        todoRepository.deleteById(id);
        return "redirect:/list-todos";
    }

    @RequestMapping(value="update-todo",method = RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
        ToDo todo = todoRepository.findById(id).get();
        model.addAttribute("todo", todo);
        return "add-todo";
    }

    @RequestMapping(value="update-todo", method = RequestMethod.POST)
    public String updateTodo(ModelMap model, @Valid ToDo todo, BindingResult result) {
        if(result.hasErrors()) {
            return "todo";
        }
        String username = getLoggedInUsername(model);
        todo.setName(username);
        todoRepository.save(todo);
        return "redirect:/list-todos";
    }

}
