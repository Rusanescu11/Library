package com.sda.blibrary.service;

import com.sda.blibrary.persistence.model.BookModel;
import com.sda.blibrary.persistence.model.PhotoL;
import com.sda.blibrary.persistence.model.PhotoU;
import com.sda.blibrary.persistence.model.UserModel;
import com.sda.blibrary.repository.PhotoURepository;
import com.sda.blibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
@Service
public class PhotoUService {
    @Autowired
    private PhotoURepository photoURepository;
    @Autowired
    UserRepository userRepository;

    public PhotoU store(MultipartFile file) throws IOException, InterruptedException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        PhotoU photoU = new PhotoU(fileName, file.getContentType(), file.getBytes());
        TimeUnit.SECONDS.sleep(3);
        List<UserModel> userModels = userRepository.findAll();
        UserModel userModel = userModels.get(userModels.size() - 1);
        photoU.setUserModel(userModel);
        return photoURepository.save(photoU);
    }

    public PhotoU getPhoto(String id) {
        return photoURepository.findById(id).get();
    }

    public Stream<PhotoU> getUserPhoto(long id) {
        UserModel userModel = userRepository.findById(id).orElse(null);
        List<PhotoU> list = new ArrayList<>();
        list.add(userModel.getPhotou());
        return list.stream();
    }

    public void delete(String photo) {
        photoURepository.deleteById(photo);
    }

    public Stream<PhotoU> getAllphotos() {
        return photoURepository.findAll().stream();
    }
}
