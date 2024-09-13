package com.example.CustomerDetails.controller;

import com.example.CustomerDetails.model.CustomerDetail;
import com.example.CustomerDetails.repo.CustomerDetailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerDetailController {

    @Autowired
    CustomerDetailRepository customerDetailRepository;

    @GetMapping("/customerDetails")
    public ResponseEntity<List<CustomerDetail>> getAllCustomerDetails() {
        try {
            List<CustomerDetail> customerDetails = new ArrayList<>();
            customerDetailRepository.findAll().forEach(customerDetails::add);

            if (customerDetails.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(customerDetails, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customerDetail/{id}")
    public ResponseEntity<CustomerDetail> getCustomerDetailById(@PathVariable Long id) {
        Optional<CustomerDetail> customerDetail = customerDetailRepository.findById(id);
        if (customerDetail.isPresent()) {
            return new ResponseEntity<>(customerDetail.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/customerDetail")
    public ResponseEntity<Object> addCustomerDetail(@Valid @RequestBody CustomerDetail customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Collect errors and return 400 Bad Request
            Map<String, String> errors = new HashMap<>();
            bindingResult.getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            CustomerDetail customerDetail = customerDetailRepository.save(customer);
            return new ResponseEntity<>(customerDetail, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            // Handle database integrity issues (e.g., unique constraint violations)
            return new ResponseEntity<>("Database integrity violation: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } 
        catch (Exception e) {
        	// return appropriate exception, it may happen that the db server is unavailable, return custom exception message
        	// below is the generic exception sent for simplicity , we never throw Internal Server error to the user, 
        	// instead add a detailed exception message
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    

}
