package com.dzeru.combalph.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.UUID;

@Controller
public class KeyController
{
	@Value("${upload.path}")
	private String uploadPath;

	@GetMapping("/getkey")
	public String getKey(Model model) throws IOException
	{
		File uploadDir = new File(uploadPath);

		if(!uploadDir.exists())
		{
			uploadDir.mkdirs();
		}

		String keyDir = uploadPath + "/keys.txt";
		File keys = new File(keyDir);
		boolean created = false;

		if(!keys.exists())
		{
			System.out.println("ho");
			created = keys.createNewFile();
		}

		if(created || keys.exists())
		{
			String key = UUID.randomUUID().toString();
			key = key.substring(0, 5);

			String keyString = new String(Files.readAllBytes(Paths.get(keyDir)));

			while(keyString.contains(key))
			{
				key = UUID.randomUUID().toString();
				key = key.substring(0, 5);
			}
			FileWriter osw = new FileWriter(keys, true);
			osw.write(key + " ");
			osw.close();

			System.out.println(key);
			model.addAttribute("key", key);
		}

		return "key";
	}

	@GetMapping("/find")
	public String findGet()
	{
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

		if(files.size() == 0)
		{
			model.addAttribute("noTextsFound", "No texts found");
		}

		model.addAttribute("foundTexts", files);

		return "index";
	}
}
