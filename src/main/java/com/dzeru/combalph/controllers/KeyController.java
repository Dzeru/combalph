package com.dzeru.combalph.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.UUID;

/**
   Copyright 2018, Dzeru, Combalph

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

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

	@GetMapping("/faq")
	public String faq()
	{
		return "faq";
	}

	@GetMapping("/faq#whatIsKey")
	public String faqKey()
	{
		return "faq#whatIsKey";
	}
}