package com.icwd.electronic.store.service.Impl;

import com.icwd.electronic.store.exception.BadApiRequest;
import com.icwd.electronic.store.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        String fileName = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();
      if(originalFilename ==null){
                 return "";
      }else {
          String extension = originalFilename.substring(originalFilename.lastIndexOf("."));


          String fileNameWithExtension = fileName + extension;
          String fullPathWithFileName = path + fileNameWithExtension;

          if (extension.equalsIgnoreCase(".png")
                  || extension.equalsIgnoreCase("jpg")
                  || extension.equalsIgnoreCase(".jpeg")) {

              File folder = new File(path);

              if (!folder.exists()) {
                  folder.mkdirs();
              }
              Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
              return fileNameWithExtension;

          } else {
              throw new BadApiRequest("File with this " + extension + "not allowed");
          }

      }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
