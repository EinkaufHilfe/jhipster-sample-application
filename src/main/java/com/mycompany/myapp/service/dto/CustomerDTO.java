package com.mycompany.myapp.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CustomerDTO {
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" + "name='" + name + '\'' + '}';
    }
}
