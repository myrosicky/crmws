package org.stockws.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.business.exceptions.AppException;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

	public abstract int upload(MultipartFile file);

}
