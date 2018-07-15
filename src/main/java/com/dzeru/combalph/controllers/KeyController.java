package com.dzeru.combalph.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

@Controller
public class KeyController
{
	@Value("${upload.path}")
	private String uploadPath;

	@GetMapping("/getkey")
	public String getKey(Model model)
	{
		String key = UUID.randomUUID().toString();
		key = key.substring(0, 5);
		model.addAttribute("key", key);

		return "index";
	}

	@PostMapping("/find")
	public String find(@RequestParam String key, Model model)
	{
		ArrayList<String> files = new ArrayList<>();
		File dir = new File(uploadPath);

		if(dir.exists())
		{
			for(File file : dir.listFiles())
			{
				String filename = file.getName();
				if(filename.substring(0, 5).equals(key))
				{
					files.add(file.getName());
				}
			}
		}

		model.addAttribute("foundTexts", files);
		return "index";
	}
}
