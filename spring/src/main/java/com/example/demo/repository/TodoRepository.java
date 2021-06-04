package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>{

	List<Todo> findByTodo(String searchWord);

}
