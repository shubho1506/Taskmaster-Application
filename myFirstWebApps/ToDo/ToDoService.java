package com.learnSpringBoot.myFirstWebApps.ToDo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class ToDoService {
    private static List<ToDo> toDoList = new ArrayList<>();
    private static int todoCount = 0;
    static {
        toDoList.add(
                new ToDo(++todoCount,
                        "sarkar",
                        LocalDate.now().plusYears(1),
                        "learn Spring",
                        "Not Done"
                )
        );
        toDoList.add(
                new ToDo(++todoCount,
                        "sarkar",
                        LocalDate.now().plusYears(2),
                        "learn testing",
                        "Done"
                )
        );
        toDoList.add(
                new ToDo(++todoCount,
                        "sarkar",
                        LocalDate.now().plusYears(3),
                        "learn devOps",
                        "Not Done"
                )
        );
    }

    public List<ToDo> findToDoListByUsername(String username){
        Predicate<? super ToDo> predicate = toDo -> toDo.getName().equalsIgnoreCase(username);
        return toDoList.stream().filter(predicate).toList();
    }
    public void addTodo(String username,LocalDate date,String description,String done){
        toDoList.add(new ToDo(++todoCount,username,date,description,done));
    }

    public void deleteById(@Valid int id){
        toDoList.remove(id-1);
    }

    public ToDo findById(int id) {
        Predicate<? super ToDo> predicate = toDo -> toDo.getId()==id;
        ToDo todo = toDoList.stream().filter(predicate).findFirst().get();
        return todo;
    }

    public void updateTodo(@Valid ToDo toDo) {
        deleteById(toDo.getId());
        toDoList.add(toDo);
    }
}
