package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.dto.AddressRequest;
import com.saurabh.E_Commerce.dto.CustomerResponse;
import com.saurabh.E_Commerce.dto.UserDto;
import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.models.Address;
import com.saurabh.E_Commerce.models.Users;
import com.saurabh.E_Commerce.repository.AddressRepository;
import com.saurabh.E_Commerce.repository.UserRepository;
import com.saurabh.E_Commerce.security.AuthUtils;
import jakarta.mail.search.SearchTerm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final AuthUtils authUtils;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    private Address fetchAddress(long id){
        Address address=addressRepository.findById(id).orElseThrow(
                ()->new ApiError("Address does not exists", HttpStatus.NOT_FOUND.value())
        );

        Users users=authUtils.getCurrentUser();
        if (users.getUserId() !=address.getUsers().getUserId()){
            throw new ApiError("Address not found",HttpStatus.NOT_FOUND.value());
        }

        return address;
    }
    public CustomerResponse getProfile() {
        Users users=authUtils.getCurrentUser();

        Set<AddressRequest>addressRequests=new HashSet<>();
        Set<Address>addresses=users.getAddresses();
        for(Address address:addresses){
            AddressRequest request=new AddressRequest();
            request.setAddressType(address.getAddressType());
            request.setCity(address.getCity());
            request.setCountry(address.getCountry());
            request.setAddressLine1(address.getAddressLine1());
            request.setAddressLine2(address.getAddressLine2());
            request.setState(address.getState());
            request.setPostalCode(address.getPostalCode());
            addressRequests.add(request);
        }

        return CustomerResponse.builder()
                .id(users.getUserId())
                .email(users.getEmail())
                .phone(users.getPhone())
                .address(addressRequests)
                .first_name(users.getFirstName())
                .last_name(users.getLastName())
                .build();
    }

    public void updateProfile(UserDto request) {
       Users users=authUtils.getCurrentUser();
       users.setUserId(request.getId());
       users.setEmail(request.getEmail());
       users.setFirstName(request.getFirst_name());
       users.setLastName(request.getLast_name());
       users.setPhone(request.getPhone());
       userRepository.save(users);
    }

    public Set<AddressRequest> getAddress() {
        Users users=authUtils.getCurrentUser();

        Set<AddressRequest>addressRequests=new HashSet<>();
        Set<Address>addresses=users.getAddresses();
        for(Address address:addresses){
            AddressRequest request=new AddressRequest();
            request.setAddressType(address.getAddressType());
            request.setCity(address.getCity());
            request.setCountry(address.getCountry());
            request.setAddressLine1(address.getAddressLine1());
            request.setAddressLine2(address.getAddressLine2());
            request.setState(address.getState());
            request.setPostalCode(address.getPostalCode());
            addressRequests.add(request);
        }

        return addressRequests;
    }

    public void addAddress(AddressRequest request) {
        Users users=authUtils.getCurrentUser();

        Address address=new Address();
        address.setState(request.getState());
        address.setAddressType(request.getAddressType());
        address.setCity(request.getCity());
        address.setAddressLine2(request.getAddressLine2());
        address.setAddressLine1(request.getAddressLine1());
        address.setPostalCode(request.getPostalCode());
        address.setUsers(users);
        address.setCountry(request.getCountry());
        addressRepository.save(address);

        users.setAddresses(Set.of(address));

    }

    public void updateAddress(AddressRequest request, Long id) {
       Address address=fetchAddress(id);

       address.setAddressId(id);
       address.setCountry(request.getCountry());
       address.setCity(request.getCity());
       address.setState(request.getState());
       address.setAddressLine1(request.getAddressLine1());
       address.setAddressLine2(request.getAddressLine2());
       address.setPostalCode(request.getPostalCode());

       addressRepository.save(address);
    }

    public AddressRequest getSpecificAddress(Long id) {
        Address address=fetchAddress(id);
        AddressRequest addressRequest=new AddressRequest();
        addressRequest.setCountry(address.getCountry());
        addressRequest.setCity(address.getCity());
        addressRequest.setState(address.getState());
        addressRequest.setAddressLine1(address.getAddressLine1());
        addressRequest.setAddressLine2(address.getAddressLine2());
        addressRequest.setPostalCode(address.getPostalCode());
        return addressRequest;
     }

    public void deleteAddress(Long id) {
        Address address=fetchAddress(id);
        addressRepository.delete(address);
    }

    public void setDefault(Long id) {
        Address address=fetchAddress(id);
        address.setAddressId(id);
        address.setDefault(true);
        addressRepository.save(address);
    }
}
