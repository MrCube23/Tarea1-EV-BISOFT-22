package com.project.tarea1Evador.logic.entity.product;

import com.project.tarea1Evador.logic.entity.category.Categoria;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "producto")
@Entity
public class Producto{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nombre;
    @Column(length = 100, nullable = false)
    private String descripcion;
    @Column(length = 5)
    private Integer precio;

    @Column(nullable = false)
    private Integer cantidad_en_stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", referencedColumnName = "id", nullable = false)
    private Categoria categoria;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    // Constructors
    public Producto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getCantidad_en_stock() {
        return cantidad_en_stock;
    }

    public void setCantidad_en_stock(Integer cantidad_en_stock) {
        this.cantidad_en_stock = cantidad_en_stock;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Producto setCategoria(Categoria categoria) {
        this.categoria = categoria;

        return this;
    }
}
