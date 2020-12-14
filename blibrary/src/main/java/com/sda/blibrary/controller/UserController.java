package com.sda.blibrary.controller;

import com.sda.blibrary.common.util.AuthenticationBean;
import com.sda.blibrary.persistence.dto.UserDto;
import com.sda.blibrary.persistence.model.PhotoL;
import com.sda.blibrary.persistence.model.PhotoU;
import com.sda.blibrary.persistence.pictures.ResponseFile;
import com.sda.blibrary.persistence.pictures.ResponseMessage;
import com.sda.blibrary.service.PhotoUService;
import com.sda.blibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PhotoUService photoUService;

    @PostMapping("/register")
    public void add(@RequestBody UserDto userDto) {
        userService.save(userDto);
    }

    @DeleteMapping("/user/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        userService.delete(id);
    }

    @GetMapping("/user")
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/user/{id}")
    public UserDto get(@PathVariable(name = "id") Long id) {
        return userService.getById(id);
    }

    @PutMapping("/user")
    public void update(@RequestBody UserDto userDto) {
        userService.update(userDto);

    }

    @GetMapping(path = "/basicauth")
    public AuthenticationBean basicauth() {
        return new AuthenticationBean("You are authenticated");
    }

    @GetMapping("/user/getByUsername/{username}")
    public UserDto getByUsername(@PathVariable(name = "username") String username) {
        return userService.getByUsername(username);
    }

    @PostMapping("/photou") //metoda de adaugare poza
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("photo") MultipartFile photou) {
        String message;
        try {
            photoUService.store(photou);

            message = "Uploaded the file successfully: " + photou.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + photou.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @RequestMapping(value = "/photou", method = RequestMethod.GET)//metoda de afisare a tututor pozelor
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = photoUService.getAllphotos().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/photou/")
                    .path(dbFile.getId())
                    .toUriString();
            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/photou/{id}") //metoda de afisare poza dupa id
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        PhotoU photoU = photoUService.getPhoto(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photoU.getName() + "\"")
                .body(photoU.getData());
    }

    @GetMapping("/photo/{id}") //metoda de afisare a pozei unei carti
    public ResponseEntity<List<ResponseFile>> getUserPhoto(@PathVariable(name = "id") Long id) {
        List<ResponseFile> files = photoUService.getUserPhoto(id).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/photou/")
                    .path(dbFile.getId())
                    .toUriString();
            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @DeleteMapping("/user/{photoId}/delete")
    public void deletePhoto(@PathVariable(name = "photoId") String photoId) {
        photoUService.delete(photoId);
    }

    @PutMapping("/userRole/{id}/{role}")
    public void addRole(@PathVariable(name = "id") long id,@PathVariable(name = "role") String role){
        userService.addRole(id,role);
    }
}
