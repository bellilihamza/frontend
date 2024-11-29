package com.moto.moto.restcontrollers;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.moto.moto.Moto;
import com.moto.moto.Type;

import com.moto.moto.repos.TypeRepository;
import com.moto.moto.services.MotoService;

@RestController
@RequestMapping("/api/type")
@CrossOrigin("*")
public class TypeRESTController {

    @Autowired
    TypeRepository typeRepository;

    // Retrieve all Types
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Type>> getAllTypes() {
        List<Type> types = typeRepository.findAll();
        return new ResponseEntity<>(types, HttpStatus.OK);
    }

    // Retrieve a Type by ID
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Type> getTypeById(@PathVariable("id") Long id) {
        Optional<Type> type = typeRepository.findById(id);
        return type.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create a new Type
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Type> createType(@RequestBody Type type) {
        Type createdType = typeRepository.save(type);
        return new ResponseEntity<>(createdType, HttpStatus.CREATED);
    }

    // Delete a Type by ID
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteType(@PathVariable("id") Long id) {
        if (typeRepository.existsById(id)) {
            typeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update a Type
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Type> updateType(@PathVariable("id") Long id, @RequestBody Type type) {
        if (typeRepository.existsById(id)) {
            type.setIdtype(id);  // Ensure the ID is set correctly
            Type updatedType = typeRepository.save(type);
            return new ResponseEntity<>(updatedType, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}