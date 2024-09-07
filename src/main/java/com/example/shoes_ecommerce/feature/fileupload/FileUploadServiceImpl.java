package com.example.shoes_ecommerce.feature.fileupload;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file-upload.server-path}")
    private String serverPart;

    @Value("${file-upload.base-uri}")
    private String baseUri;

    @Override
    public FileUploadResponse upload(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file");
        }
        String extension = file.getContentType().split("/")[1];
        String newFileName = String.format("%s.%s"
                , UUID.randomUUID().toString(), extension
        );

        Path path = Paths.get(serverPart + newFileName);
        try{

            Files.copy(file.getInputStream(),path);

        }catch (IOException e){
            log.error(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "File upload field"
            );
        }
        return  FileUploadResponse.builder()
                .name(newFileName)
                .uri(baseUri + newFileName)
                .contentType(file.getContentType())
                .size(file.getSize())
                .build();
    }

    @Override
    public List<FileUploadResponse> uploadMultiple(List<MultipartFile> files) {
        List<FileUploadResponse> FileUploadResponses = new ArrayList<>();
        files.forEach(file -> {
            FileUploadResponse  FileUploadResponse = upload(file);
            FileUploadResponses.add(FileUploadResponse);
        });
        return  FileUploadResponses;
    }

    @Override
    public void deleteFileByName(String fileName) {

        Path path = Paths.get(serverPart + fileName);
        if(Files.exists(path)) {
            // TODO: Delete
            try{
                Files.delete(path);
            }catch (IOException e){
                log.error(e.getMessage());
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "File upload field"
                );
            }
        }else{
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "File name has not been found"
            );
        }
    }
}
