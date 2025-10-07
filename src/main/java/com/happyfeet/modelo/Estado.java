package com.happyfeet.modelo;

import java.time.LocalDateTime;

public class Estado {
    private int id;
    private String nombre;
    private LocalDateTime createdAt;

    public Estado() {
    }

    public Estado(int id, String nombre, LocalDateTime createdAt) {
        this.id = id;
        this.nombre = nombre;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
