package com.sda.blibrary.controller;

import com.sda.blibrary.persistence.dto.BookDto;
import com.sda.blibrary.persistence.model.PhotoL;
import com.sda.blibrary.persistence.pictures.ResponseFile;
import com.sda.blibrary.persistence.pictures.ResponseMessage;
import com.sda.blibrary.service.BookService;
import com.sda.blibrary.service.PhotoBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private PhotoBService photoBService;

    @PostMapping("/book")
    public void add(@RequestBody BookDto bookDto) {
        bookService.save(bookDto);
    }

    @DeleteMapping("/book/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        bookService.delete(id);
    }

    @GetMapping("/book")
    public List<BookDto> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/book/{id}")
    public BookDto get(@PathVariable(name = "id") Long id) {
        return bookService.getById(id);
    }

    @PutMapping("/book")
    public void update(@RequestBody BookDto bookDto) {
        bookService.update(bookDto);

    }
    @PostMapping("/photob") //metoda de adaugare poza
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("photo") MultipartFile photob) {
        String message;
        try {
            photoBService.store(photob);

            message = "Uploaded the file successfully: " + photob.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + photob.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    @RequestMapping(value = "/photob", method = RequestMethod.GET)//metoda de afisare a tututor pozelor
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = photoBService.getAllphotos().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/photob/")
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
    @GetMapping("/photob/{id}") //metoda de afisare a unei  poze dupa id
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        PhotoL photoL = photoBService.getPhoto(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photoL.getName() + "\"")
                .body(photoL.getData());
    }
    @GetMapping("/photoB/{id}") //metoda de afisare a pozei unei carti
    public ResponseEntity<List<ResponseFile>> getBookPhoto(@PathVariable(name = "id") Long id) {
        List<ResponseFile> files = photoBService.getBookPhoto(id).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/photob/")
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
    @DeleteMapping("/book/{photoId}/delete")
    public void deletePhoto(@PathVariable(name = "photoId") String photoId) {
        photoBService.delete(photoId);
    }
}
