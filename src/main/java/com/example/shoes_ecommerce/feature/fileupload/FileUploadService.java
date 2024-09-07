package com.example.shoes_ecommerce.feature.fileupload;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface FileUploadService {

     FileUploadResponse upload(MultipartFile file);

     List<FileUploadResponse> uploadMultiple(List<MultipartFile> files);

     void deleteFileByName(String fileName);
}
