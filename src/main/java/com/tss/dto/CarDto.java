package com.tss.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class CarDto {
    
    @NotEmpty(message="The brand is required")
    private String brand;
    
    @NotEmpty(message="The model is required")
    private String model;
    
    @Min(1885)
    @Max(2024)
    private int year;
    
    @NotEmpty(message="The color is required")
    private String color;
    
    private MultipartFile imageFile;

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

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
    
    
}
