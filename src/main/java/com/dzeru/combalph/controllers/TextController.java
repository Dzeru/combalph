package com.dzeru.combalph.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TextController
{
	@GetMapping("/")
	public String index()
	{
		return "index";
	}
	
	@PostMapping("/")
	public String index(@RequestParam int complexityLevel,
						@RequestParam String language,
						@RequestParam String kana,
						@RequestParam String fileName,
	                    @RequestParam("file") MultipartFile file)
	{
		
	}
}