package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.domain.Todo;
import com.example.demo.form.TodoForm;
import com.example.demo.service.TodoService;

@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;

    //SELECT * FROM todo where delete_flg = 0;
    @RequestMapping("/")
    public String allTodo(Model model) {
        List<Todo> todolist = todoService.searchAll();
        model.addAttribute("todolist", todolist);
        return "top";
    }

    //完了 SELECT * FROM todo where delete_flg = 0 and end_flg = 1;
    @RequestMapping("/top")
    public String displayList(Model model) {
        List<Todo> todolist = todoService.searchAll();
        model.addAttribute("todolist", todolist);
        return "top";
    }

    //未完了 SELECT * FROM todo where delete_flg = 0 and end_flg = 0;
//    @RequestMapping("/top")
//    public String displayList(Model model) {
//        List<Todo> todolist = todoService.searchAll();
//        model.addAttribute("todolist", todolist);
//        return "top";
//    }
//

    @RequestMapping("/create")
    public String create(Model model) {
        model.addAttribute("todoForm", new TodoForm());
        return "create";
    }

    @RequestMapping(value = "/todoAd", method = RequestMethod.POST)
    public String todoAd(@Validated @ModelAttribute TodoForm todoForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "/create";
        }
        todoService.create(todoForm);
        return "redirect:/top";
    }

    @RequestMapping("/todo/{id}")
    public String view(@PathVariable Long id,Model model) {
        Todo todo = todoService.findById(id);
        model.addAttribute("todo", todo);
        return "view";
    }

    @RequestMapping("/todo/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Todo todo = todoService.findById(id);
        TodoForm todoForm = new TodoForm();
        todoForm.setTodo(todo.getTodo());
        todoForm.setMemo(todo.getMemo());
        todoForm.setId(todo.getId());
        model.addAttribute("todoForm", todoForm);
        return "/edit";
    }

    @RequestMapping(value = "/todo/{id}/complete")
    public String complete(TodoForm todoForm) {
    	todoService.complete(todoForm);
        return "redirect:/top";
    }

    @RequestMapping(value = "/todo/{id}/delete")
    public String delete(TodoForm todoForm) {
        todoService.delete(todoForm);
        return "redirect:/top";
    }

    @RequestMapping(value = "/todo/update", method = RequestMethod.POST)
    public String update(@Validated @ModelAttribute TodoForm todoForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for(ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
              model.addAttribute("validationError", errorList);
              return "/edit";
            }
        todoService.update(todoForm);
        return "redirect:/top";
    }
}
