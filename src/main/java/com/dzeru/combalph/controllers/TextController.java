package com.dzeru.combalph.controllers;

import com.dzeru.combalph.services.CombineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
public class TextController
{
	@Autowired
	private CombineService combineService;

	@GetMapping("/")
	public String getIndex()
	{
		return "index";
	}

	@GetMapping("/upload")
	public String getUpload()
	{
		return "index";
	}
	
	@PostMapping("/upload")
	public String postUpload(@RequestParam String complexityLevel,
						@RequestParam String language,
						@RequestParam String kana,
	                    @RequestParam(value = "file", required = false) MultipartFile file,
						@RequestParam(value = "typedText", required = false) String typedText,
						Model model) throws IOException
	{
		if(!(file.getSize() == 0))
		{
			String text = "";

			if(file.getOriginalFilename().substring(file.getOriginalFilename().length() - 3).equals("txt"))
			{
				text = new String(file.getBytes());
			}
			else
			{
				model.addAttribute("error", "error.wrongFileExtension");
			}

			text = combineService.combine(text, language, kana, complexityLevel);
			model.addAttribute("text",  text);
		}
		else if(!typedText.isEmpty())
		{
			typedText = combineService.combine(typedText, language, kana, complexityLevel);
			model.addAttribute("text",  typedText);
		}
		else
		{
			model.addAttribute("error", "error.uploadFileOrTypeText");
		}

		return "index";
	}

	@GetMapping("/faq")
	public String getFaq()
	{
		return "faq";
	}
}