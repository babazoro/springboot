package com.example.demo.form;


import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class TodoForm {
    private Long id;

    @NotEmpty
    private String todo;

    private String memo;

    private String create_date;

    private String end_date;

    private Integer end_flg;

    private Integer delete_flg;

    private String update_date;

}
