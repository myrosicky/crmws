package org.stockws.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.business.exceptions.AppException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.stockws.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {
	
	@Override
	public int upload(MultipartFile file){
		try {
			Path uploadImagesFolder = Paths.get(".", "uploadImages");
			Path uploadmage = uploadImagesFolder.resolve(file.getOriginalFilename());
			if(Files.notExists(uploadmage)){
				Files.createFile(uploadmage);
			}
			Files.copy(file.getInputStream(), uploadmage);
			return 1;
		} catch (IOException e) {
			throw new AppException("1", e.getMessage());
		}
	}

}
