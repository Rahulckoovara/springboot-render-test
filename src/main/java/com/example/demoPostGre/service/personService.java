package com.example.demoPostGre.service;

import com.example.demoPostGre.exception.ProductNotFoundException;
import com.example.demoPostGre.model.person;
import com.example.demoPostGre.repo.personRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class personService {

    @Autowired
    private personRepo repo;


    public person addPerson(person p) {
      return repo.save(p);
      //  return ResponseEntity.ok(response);

    }

    public List<person> getAllUsers() {
        return repo.findAll();
    }

    public person getUserById(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new ProductNotFoundException(
                        "Person not found with id : " + id));
    }

    //update user
    public person updateUserById(Long id, person updatedData) {
       person existingData=repo.findById(id).orElseThrow(() ->
               new ProductNotFoundException(
                       "Person not found with id : " + id));
            existingData.setName(updatedData.getName());
           existingData.setEmail(updatedData.getEmail());

       return repo.save(existingData);
    }

    public boolean deleteById(Long id) {
         repo.deleteById(id);
         return  true;


    }

    //sort
    public List<person> getAllUsers(String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        return repo.findAll(sort);
    }

    //pagination

    public List<person> getPage(int page,
                                int size,
                                String sortDir,
                                String sortBy) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(direction, sortBy)
        );

        return repo.findAll(pageable).getContent();
    }

//image up
    public person addUser(person p, MultipartFile imageFile) throws IOException {
        p.setImageName(imageFile.getOriginalFilename());
        p.setImageType(imageFile.getContentType());
        p.setImageData(imageFile.getBytes());

        return repo.save(p);
    }
}
