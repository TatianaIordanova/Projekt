package com.tss.controllers;

import com.tss.components.SessionComponent;
import com.tss.dto.CarDto;
import com.tss.entities.Car;
import com.tss.repositories.CarRepository;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


/**
 * It's a Spring MVC controller that handles HTTP requests related to car management, including creating, editing, viewing, and deleting cars.
 * The class ensures proper handling of image files associated with each car, storing them in the src/main/resources/static/images/ 
 * directory and managing their lifecycle (e.g., deleting old images when updating or deleting cars).
 * The controller also increases the session counter each time any database query is executed.
 */
@Controller
@RequestMapping("/cars")
public class CarController {
    
    @Autowired
    private CarRepository repo;
    
    @Autowired
    private SessionComponent session;

    @GetMapping({"", "/"})
    public String getCars(Model model) {
        session.increaseCounter();
        model.addAttribute("cars", repo.findAll());
        model.addAttribute("sessionCounter", session.getCounter());

        return "cars.html";
    }
    
    
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        CarDto cardto = new CarDto();
        model.addAttribute("cardto", cardto);
        session.increaseCounter();
        
        return "createCar";
    }
    
    @PostMapping("/create")
    public String createCar(@Valid @ModelAttribute("cardto") CarDto cardto, BindingResult result) {
        if(cardto.getImageFile().isEmpty()) {
            result.addError(new FieldError("cardto", "imageFile", "The image file is required"));
        }
        if(result.hasErrors()) {
            return "createCar";
        }
        
        MultipartFile image = cardto.getImageFile();
        String storageFileName=image.getOriginalFilename();
        try {
            Path uploadPath = Paths.get("src/main/resources/static/images/");
            if(!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
            
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get("src/main/resources/static/images/" + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch(Exception ex) {
            System.out.println("Exception" + ex.getMessage());
        }      
        Car car = new Car();
        car.setBrand(cardto.getBrand());
        car.setModel(cardto.getModel());
        car.setYear(cardto.getYear());
        car.setColor(cardto.getColor());
        car.setImageFileName(cardto.getImageFile().getOriginalFilename());
        car.setCreated(new Date());
        
        repo.save(car);
        session.increaseCounter();
        
        return "redirect:/cars";
    }
    
    @GetMapping("/edit") 
    public String showEditPage(Model model, @RequestParam Long id) {
        try {
            Car car = repo.findById(id).get();
            model.addAttribute("car", car);
            
            CarDto cardto = new CarDto();
            cardto.setBrand(car.getBrand());
            cardto.setModel(car.getModel());
            cardto.setYear(car.getYear());
            cardto.setColor(car.getColor());
            model.addAttribute("cardto", cardto);
        } catch(Exception ex) {
            System.out.println("Exception" + ex.getMessage());
            return "redirect:/cars";
        }
        session.increaseCounter();
        return "editCar";
    }
    
    @PostMapping("/edit")
    public String updateCar(Model model, @RequestParam("id") Long id, @Valid @ModelAttribute CarDto cardto, BindingResult result) {
        try {
            Car car = repo.findById(id).get();
            model.addAttribute("car", car);
            
            if(result.hasErrors()) {
            return "editCar";
            }
            
            if(!cardto.getImageFile().isEmpty()) {
                Path oldImagePath=Paths.get("src/main/resources/static/images/" + car.getImageFileName());
                
                try {
                    Files.delete(oldImagePath);
                } catch(Exception ex) {
                    System.out.println("Exception" + ex.getMessage());
                }
                
                MultipartFile image = cardto.getImageFile();
                String storageFileName = image.getOriginalFilename();
                
                try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get("src/main/resources/static/images/" + storageFileName), StandardCopyOption.REPLACE_EXISTING);
                }
                car.setImageFileName(storageFileName);
            }
            car.setBrand(cardto.getBrand());
            car.setModel(cardto.getModel());
            car.setYear(cardto.getYear());
            car.setColor(cardto.getColor());
            
            repo.save(car);
        } catch(Exception ex) {
            System.out.println("Exception" + ex.getMessage());
        }
        session.increaseCounter();
        return "redirect:/cars";
    } 
    
    @GetMapping("/delete") 
    public String deleteCar(@RequestParam Long id) {
        
        try {
            Car car = repo.findById(id).get();
            Path imagePath = Paths.get("src/main/resources/static/images/" + car.getImageFileName());
            
            try {
                Files.delete(imagePath);
            } catch(Exception ex) {
            System.out.println("Exception" + ex.getMessage());
        }
            repo.delete(car);
        } catch(Exception ex) {
            System.out.println("Exception" + ex.getMessage());
        }
        session.increaseCounter();
        return "redirect:/cars";
    }
}
