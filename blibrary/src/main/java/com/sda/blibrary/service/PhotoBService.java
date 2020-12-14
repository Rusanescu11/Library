package com.sda.blibrary.service;

import com.sda.blibrary.persistence.model.BookModel;
import com.sda.blibrary.persistence.model.PhotoL;
import com.sda.blibrary.repository.BookRepository;
import com.sda.blibrary.repository.PhotoBRepository;
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
public class PhotoBService {
    @Autowired
    private PhotoBRepository photoBRepository;
    @Autowired
    private BookRepository bookRepository;

    public PhotoL store(MultipartFile file) throws IOException, InterruptedException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        PhotoL photoL = new PhotoL(fileName, file.getContentType(), file.getBytes());
        TimeUnit.SECONDS.sleep(3);
        List<BookModel> bookModels = bookRepository.findAll();
        BookModel bookModel = bookModels.get(bookModels.size() - 1);
        photoL.setBookModel(bookModel);
        return photoBRepository.save(photoL);
    }

    public PhotoL getPhoto(String id) { //vizualizare poza dupa id
        return photoBRepository.findById(id).get();
    }

    public Stream<PhotoL> getBookPhoto(long id) { //vizualizare carte cu tot cu poza ei
        BookModel bookModel = bookRepository.findById(id).orElse(null);
        List<PhotoL> list = new ArrayList<>();
        list.add(bookModel.getPhotoB());
        return list.stream();
    }

    public void delete(String photo) {
        photoBRepository.deleteById(photo);
    }

    public Stream<PhotoL> getAllphotos() {
        return photoBRepository.findAll().stream();
    }
}
