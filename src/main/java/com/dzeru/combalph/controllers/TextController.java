package com.dzeru.combalph.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class TextController
{
	@Value("${upload.path}")
	private String uploadPath;

	@GetMapping("/")
	public String index()
	{
		return "index";
	}
	
	@PostMapping("/upload")
	public String index(@RequestParam String key,
						@RequestParam String complexityLevel,
						@RequestParam String language,
						@RequestParam String kana,
	                    @RequestParam("file") MultipartFile file,
						Model model) throws IOException
	{
		if(key == null || key.isEmpty())
			key = UUID.randomUUID().toString();

		if(file != null)
		{
			File uploadDir = new File(uploadPath);

			if(!uploadDir.exists())
			{
				uploadDir.mkdirs();
			}

			String filename = key + "-" + file.getOriginalFilename();
			String fullFilename = uploadPath + "/" + filename;

			file.transferTo(new File(fullFilename));

			String text = new String(Files.readAllBytes(Paths.get(fullFilename)));

			model.addAttribute("text",  text);
		}
		return "index";
	}
}