package com.example.shoes_ecommerce.feature.fileupload;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/upload")

public class FileUploadController {

    private final FileUploadService fileUploadService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/multiple")
    List<FileUploadResponse> uploadMultiple(@RequestPart List<MultipartFile> files) {
        return fileUploadService.uploadMultiple(files);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    FileUploadResponse upload(@RequestPart MultipartFile file)  {

        return fileUploadService.upload(file);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{fileName}")
    void deleteByFileName(@PathVariable String fileName) {

        fileUploadService.deleteFileByName(fileName);
    }

}