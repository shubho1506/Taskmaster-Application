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

//@Controller
//@SessionAttributes("name")
public class ToDoController {

    private ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    private String getLoggedInUsername(ModelMap model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
    //list-todos
    @RequestMapping("/list-todos")
    public String listAllToDos(ModelMap model){
        List<ToDo> todos = toDoService.findToDoListByUsername(getLoggedInUsername(model));
        model.put("todos",todos);
        return "listToDos";
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.GET)
    public String showAddNewTodoPage(ModelMap model){
        ToDo toDo = new ToDo(0,getLoggedInUsername(model),LocalDate.now().plusYears(1),"","");
        model.put("todo",toDo);
        return "add-todo";
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String addNewTodo(ModelMap model, @Valid @ModelAttribute("todo") ToDo toDo, BindingResult result){
        if(result.hasErrors()){
            return "redirect:/add-todo";
        }
        toDoService.addTodo(getLoggedInUsername(model), toDo.getTargetDate(),toDo.getDescription(),"Not done");
//        toDoService.addTodo(toDo.getName(), toDo.getTargetDate(),toDo.getDescription(),"Not done");
        return "redirect:/list-todos";
    }

    @RequestMapping(value = "/delete-todo")
    public String deleteTodo(@RequestParam int id){
        toDoService.deleteById(id-1);
        return "redirect:/list-todos";
    }

    @RequestMapping(value="update-todo",method = RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
        ToDo todo = toDoService.findById(id);
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
        toDoService.updateTodo(todo);
        return "redirect:/list-todos";
    }

}
