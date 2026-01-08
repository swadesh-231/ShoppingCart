package com.shoppingcart.repository;

import com.shoppingcart.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByPincode(String pincode);
    List<Address> findByCity(String city);
    List<Address> findByCityOrderByPincodeAsc(String city);
}
