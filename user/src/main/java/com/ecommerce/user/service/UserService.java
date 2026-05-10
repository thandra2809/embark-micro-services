package com.ecommerce.user.service;


import com.ecommerce.user.dto.AddressDTO;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.model.Address;
import com.ecommerce.user.model.User;
import com.ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;


    public List<UserResponse> fetchAllUsers() {
        List<User> users = userRepository.findAll();
        return users.
                stream().
                map(this::mapToUserResponse).
                collect(Collectors.toList());
    }

    public void addUser(UserRequest userRequest) {
        User user = new User();

        updateUserFromRequest(userRequest,user);
        userRepository.save(user);
    }


    public Optional<UserResponse> findUserById(Long userId) {
        return userRepository.findById(userId).map(this::mapToUserResponse);
    }

    public boolean updateUser(Long userId, UserRequest updatedUserRequest) {
        return userRepository.findById(userId).map(existingUser -> {
            updateUserFromRequest(updatedUserRequest,existingUser);
            userRepository.save(existingUser);
            return true;
        }).orElse(false);
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            addressDTO.setCountry(user.getAddress().getCountry());
            userResponse.setAddress(addressDTO);
        }

        return userResponse;
    }

    private void updateUserFromRequest(UserRequest userRequest, User user) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipcode(userRequest.getAddress().getZipcode());
            user.setAddress(address);
        }
    }
}
