package com.tss.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cars")
public class Car {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="brand", length=255, nullable=false, unique=false)
    private String brand;
    
    @Column(name="model", length=255, nullable=false, unique=false)
    private String model;
    
    @Column(name="year", nullable=false, unique=false)
    private int year;
    
    @Column(name="color", length=255, nullable=false, unique=false)
    private String color;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;
    
    @Column(name="image_file_name", length=255, nullable=false, unique=false)
    private String imageFileName;

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
    
    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
    
    public Car(Long id, String brand, String model, int year, String color, Date created) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.created = created;
    }

    public Car(Long id, String brand, String model, int year, String color) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
    }
    
    public Car() {
        super();
    }

    @Override
    public String toString() {
        return "Car{" + "id=" + id + ", brand=" + brand + ", model=" + model + ", year=" + year + ", color=" + color + ", created=" + created + '}';
    }

    
    
}
