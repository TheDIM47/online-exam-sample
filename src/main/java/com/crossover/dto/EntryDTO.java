package com.crossover.dto;

import java.io.Serializable;

// Simple Key-Value (or ID-Name) DTO
public class EntryDTO implements Serializable {
    public EntryDTO() {
    }

    public EntryDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Integer id;
    private String name;
}
