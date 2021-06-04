package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Todo;
import com.example.demo.form.TodoForm;
import com.example.demo.repository.TodoRepository;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public String now() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(now);
    }
    //全件取得
    public List<Todo> searchAll() {
        return todoRepository.findAll();
      }

    public void create(TodoForm todoForm) {
        Todo todo = new Todo();
        todo.setTodo(todoForm.getTodo());
        todo.setDelete_flg(0);
        todo.setEnd_flg(0);
        todo.setCreate_date(this.now());
        todo.setUpdate_date(this.now());
        todoRepository.save(todo);
      }

    public Todo findById(Long id) {
        return todoRepository.findById(id).get();
      }

    public void update(TodoForm todoForm) {
        Todo todo = findById(todoForm.getId());
        todo.setTodo(todoForm.getTodo());
        todo.setMemo(todoForm.getMemo());
        todo.setUpdate_date(this.now());
        todoRepository.save(todo);
    }

    public void delete (TodoForm todoForm) {
        Todo todo = findById(todoForm.getId());
        todo.setDelete_flg(1);
        todoRepository.save(todo);
    }

    public void complete (TodoForm todoForm) {
        Todo todo = findById(todoForm.getId());
        todo.setEnd_flg(1);
        todo.setEnd_date(this.now());
        todoRepository.save(todo);
    }

    public List<Todo> findToDo(String searchWord) {
        return todoRepository.findByTodo(searchWord);
    }
}
