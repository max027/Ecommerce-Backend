package com.saurabh.E_Commerce.service;

import com.saurabh.E_Commerce.dto.AddressDto;
import com.saurabh.E_Commerce.dto.CustomerResponse;
import com.saurabh.E_Commerce.dto.UserDto;
import com.saurabh.E_Commerce.exception.ApiError;
import com.saurabh.E_Commerce.models.Address;
import com.saurabh.E_Commerce.models.Users;
import com.saurabh.E_Commerce.repository.AddressRepository;
import com.saurabh.E_Commerce.repository.UserRepository;
import com.saurabh.E_Commerce.security.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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
            throw new ApiError("unauthorized",HttpStatus.UNAUTHORIZED.value());
        }

        return address;
    }
    public CustomerResponse getProfile() {
        Users users=authUtils.getCurrentUser();

        Set<AddressDto> addressDtos =new HashSet<>();
        Set<Address>addresses=users.getAddresses();
        for(Address address:addresses){
            AddressDto request=new AddressDto();
            request.setAddressType(address.getAddressType());
            request.setCity(address.getCity());
            request.setCountry(address.getCountry());
            request.setAddressLine1(address.getAddressLine1());
            request.setAddressLine2(address.getAddressLine2());
            request.setState(address.getState());
            request.setPostalCode(address.getPostalCode());
            addressDtos.add(request);
        }

        return CustomerResponse.builder()
                .id(users.getUserId())
                .email(users.getEmail())
                .phone(users.getPhone())
                .address(addressDtos)
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

    public Set<AddressDto> getAddress() {
        Users users=authUtils.getCurrentUser();

        Set<AddressDto> addressDtos =new HashSet<>();
        Set<Address>addresses=users.getAddresses();
        for(Address address:addresses){
            AddressDto request=new AddressDto();
            request.setAddressType(address.getAddressType());
            request.setCity(address.getCity());
            request.setCountry(address.getCountry());
            request.setAddressLine1(address.getAddressLine1());
            request.setAddressLine2(address.getAddressLine2());
            request.setState(address.getState());
            request.setPostalCode(address.getPostalCode());
            addressDtos.add(request);
        }

        return addressDtos;
    }

    public void addAddress(AddressDto request) {
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

    public void updateAddress(AddressDto request, Long id) {
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

    public AddressDto getSpecificAddress(Long id) {
        Address address=fetchAddress(id);
        AddressDto addressDto =new AddressDto();
        addressDto.setCountry(address.getCountry());
        addressDto.setCity(address.getCity());
        addressDto.setState(address.getState());
        addressDto.setAddressLine1(address.getAddressLine1());
        addressDto.setAddressLine2(address.getAddressLine2());
        addressDto.setPostalCode(address.getPostalCode());
        return addressDto;
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
