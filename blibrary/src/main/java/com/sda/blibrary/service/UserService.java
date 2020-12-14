package com.sda.blibrary.service;

import com.sda.blibrary.persistence.dto.UserDto;
import com.sda.blibrary.persistence.model.ReservationModel;
import com.sda.blibrary.persistence.model.Role;
import com.sda.blibrary.persistence.model.UserModel;
import com.sda.blibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void save(UserDto userDto) {
        UserModel userModel = new UserModel();
        userModel.setId(userDto.getId());
        userModel.setUsername(userDto.getUsername());
        userModel.setEmail(userDto.getEmail());
        userModel.setCity(userDto.getCity());
        userModel.setStreet(userDto.getStreet());
        userModel.setNumber(userDto.getNumber());
        userModel.setPassword(userDto.getPassword());
        userModel.setRole(Role.valueOf("Standard"));
        userRepository.save(userModel);
    }

    public List<UserDto> getAll() {
        List<UserModel> userModelList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserModel userModel : userModelList) {
            UserDto userDto = new UserDto();
            userDto.setId(userModel.getId());
            userDto.setUsername(userModel.getUsername());
            userDto.setEmail(userModel.getEmail());
            userDto.setCity(userModel.getCity());
            userDto.setStreet(userModel.getStreet());
            userDto.setNumber(userModel.getNumber());
            userDto.setIdPhoto(userModel.getPhotou().getId());
            userDto.setRole(userModel.getRole().name());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    public UserDto getById(long id) {  //nu stiu daca e bine reverific o daca nu ti merge ceva
        Optional<UserModel> userModelOptional = userRepository.findById(id);
        UserDto userDto = new UserDto();
        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();
            userDto.setId(userModel.getId());
            userDto.setUsername(userDto.getUsername());
            userDto.setEmail(userModel.getEmail());
            userDto.setCity(userModel.getCity());
            userDto.setStreet(userModel.getStreet());
            userDto.setNumber(userModel.getNumber());
            userDto.setRole(userModel.getRole().name());
            userDto.setIdPhoto(userModel.getPhotou().getId());

        }
        return userDto;
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public void update(UserDto userDto) {
        Optional<UserModel> userModelOptional = userRepository.findById(userDto.getId());

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();
            userModel.setId(userDto.getId());
            userModel.setUsername(userDto.getUsername());
            userModel.setEmail(userDto.getEmail());
            userModel.setCity(userDto.getCity());
            userModel.setStreet(userDto.getStreet());
            userModel.setPassword(userDto.getPassword());
            userRepository.save(userModel);
        }
    }

    public UserDto getByUsername(String username) {
        Optional<UserModel> userModelOptional = userRepository.findByEmail(username);
        UserDto userDto = new UserDto();
        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();
            userDto.setId(userModel.getId());
            userDto.setUsername(userModel.getUsername());
            userDto.setEmail(userModel.getEmail());
            userDto.setCity(userModel.getCity());
            userDto.setStreet(userModel.getStreet());
            userDto.setNumber(userModel.getNumber());
            userDto.setRole(userModel.getRole().name());
        }
        return userDto;
    }

    public void addRole(long id, String role) {
        Optional<UserModel> userModelOptional = userRepository.findById(id);
        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();
            userModel.setRole(Role.valueOf(role));
            userRepository.save(userModel);
        }
    }
}
