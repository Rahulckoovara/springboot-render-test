package com.example.demoPostGre.controller;

import com.example.demoPostGre.model.person;
import com.example.demoPostGre.repo.Student;
import com.example.demoPostGre.repo.personRepo;
import com.example.demoPostGre.service.personService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class personController {

    @Autowired
    private personService service;


//    private  List<Student> students= new ArrayList<>((List.of(
//            new Student(1,"ram",12),
//            new Student(2,"raju",13)
//    )));
//
//    @GetMapping("/students")
//public List<Student> getStudents(){
//    return students;
//}
//
//
//    @PostMapping("/students")
//    public Student update(@RequestBody Student updated) {
//
//            students.add(updated);
//            return updated;
//        }
////csrf
//    @GetMapping("/csrf-token")
//    public CsrfToken getCsrfToken(HttpServletRequest req){
//        return (CsrfToken) req.getAttribute("_csrf");
//    }

//---------------------------------------------------------------

   @PostMapping("/person")
    public ResponseEntity<person> createPerson (@Valid @RequestBody person p){
        person result =   service.addPerson(p);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    //for getting all users
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<person>> getAllPerson(){
        List<person> result = service.getAllUsers();
     return new  ResponseEntity<>(result,HttpStatus.OK);

    }

    //byId
    @GetMapping("/getAllUsers/{id}")
    public ResponseEntity<person> getUserById(@PathVariable Long id){
        person result = service.getUserById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    //update by id
    @PutMapping("/update/{id}")
    public  ResponseEntity<person>updateUserById(@PathVariable Long id, @RequestBody person p){
      person result=  service.updateUserById(id,p);
      return new ResponseEntity<>(result,HttpStatus.OK);

    }

    //deleteData
    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
boolean isDeleted = service.deleteById(id);
        if (isDeleted) {
            return ("Product deleted successfully");
        } else {
            return ("Product not found");
        }

     //  service.deleteById(id);
    }

    //sort
    @GetMapping("/users")
    public ResponseEntity<List<person>> getAllUsers(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        List<person> users = service.getAllUsers(sortBy, sortDir);
        return ResponseEntity.ok(users);
    }

    //pagintio
    @GetMapping("/sortPage")
    public ResponseEntity<List<person>> getPage(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortDir,
            @RequestParam String sortBy) {

        List<person> persons = service.getPage(page, size, sortDir, sortBy);

        return ResponseEntity.ok(persons);
    }

    //for image
    @PostMapping(value = "/product")
    public ResponseEntity<?> addImage(
            @RequestPart("person") person p,
            @RequestPart("imageFile") MultipartFile imageFile) throws IOException {

        person result = service.addUser(p, imageFile);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    //getimage
    @GetMapping("/imageById/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id){
        person response = service.getUserById(id);
        byte[] image = response.getImageData();
        return ResponseEntity.ok().contentType(MediaType.valueOf(response.getImageType()))
                .body(image);

    }



}
