package com.example.CustomerDetails.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CustomerDetails.model.CustomerDetail;

@Repository
public interface CustomerDetailRepository extends JpaRepository<CustomerDetail, Long> {

}
