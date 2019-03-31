package org.stockws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.stockws.service.UploadService;

@RestController
public class ImageClassifyController {

	private static final Logger log = LoggerFactory.getLogger(ImageClassifyController.class);
	
	@Autowired
	private UploadService uploadService;
	
	@PostMapping("/classify")
	String classify(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
		
		uploadService.upload(file);
		
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

		return "successfully uploaded, classifying";
	}
}
