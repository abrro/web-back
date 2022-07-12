package com.example.demo.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Category {

    @NotNull(message = "Field required")
    @NotEmpty(message = "Field required")
    @NotBlank(message = "Field cannot be whitespace")
    private String label;

    @NotNull(message = "Field required")
    @NotEmpty(message = "Field required")
    @NotBlank(message = "Field cannot be whitespace")
    private String description;

    public Category() {
    }

    public Category(String label, String description) {
        this();
        this.label = label;
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
