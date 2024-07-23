package com.learnSpringBoot.myFirstWebApps.ToDo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface ToDoRepository extends JpaRepository<ToDo, Integer>{

   List<ToDo> findByName(String username);
}
