package com.saurabh.E_Commerce.repository;

import com.saurabh.E_Commerce.models.Vendors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorsRepository extends JpaRepository<Vendors,Long> {

}
