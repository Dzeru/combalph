package com.dzeru.combalph.controllers;

import com.dzeru.combalph.services.CombineService;
import com.dzeru.combalph.services.ParseDocxService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class TextController
{
	@Autowired
	private CombineService combineService;

	@Autowired
	private ParseDocxService parseDocxService;


	@Value("${upload.path}")
	private String uploadPath;

	private String noKey = "no-key";

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
	                    @RequestParam(value = "file", required = false) MultipartFile file,
						Model model) throws IOException
	{
		if(key == null || key.isEmpty())
		{
			key = noKey;
		}

		String keyDir = uploadPath + "/keys.txt";
		File keys = new File(keyDir);
		if(keys.exists())
		{
			String keyString = new String(Files.readAllBytes(Paths.get(keyDir)));
			if(!key.equals(noKey) && !keyString.contains(key))
			{
				model.addAttribute("error", "error.noSuchKey");
				return "index";
			}
		}
		else if(!key.equals(noKey))
		{
			model.addAttribute("error", "error.noSuchKey");
			return "index";
		}

		if(file != null)
		{
			File uploadDir = new File(uploadPath);

			if(!uploadDir.exists())
			{
				uploadDir.mkdirs();
			}

			String filename = key + "-" + file.getOriginalFilename();
			String fullFilename = uploadPath + "/" + filename;

			File textFile = new File(fullFilename);

			file.transferTo(textFile);

			String text = "";

			if(filename.substring(filename.length() - 4).equals("docx"))
			{
				text = parseDocxService.parseDocx(fullFilename);
			}
			else if(filename.substring(filename.length() - 3).equals("txt"))
			{
				text = new String(Files.readAllBytes(Paths.get(fullFilename)));
			}
			else
			{
				model.addAttribute("error", "error.wrongFileExtension");
			}

			text = combineService.combine(text, language, kana, complexityLevel);
			model.addAttribute("text",  text);

			/*
			If file has no its own key, it should not take place on file storage
			 */
			if(key.equals(noKey) && textFile.exists())
			{
				textFile.delete();
			}
		}

		return "index";
	}

	@GetMapping("/fill")
	public String fillTextArea(@RequestParam String filename, Model model)
	{
		model.addAttribute("filename", filename);

		return "filltextarea";
	}

	/*
	There no need in checking file extension because files with wrong one can not be saving in index()
	 */
	@PostMapping("/fill")
	public String fill(@RequestParam String filename,
						@RequestParam String complexityLevel,
	                    @RequestParam String language,
	                    @RequestParam String kana,
	                    Model model) throws IOException
	{
		String fullFilename = uploadPath + "/" + filename;

		String text = "";

		if(filename.substring(filename.length() - 4).equals("docx"))
		{
			text = parseDocxService.parseDocx(fullFilename);
		}
		else if(filename.substring(filename.length() - 3).equals("txt"))
		{
			text = new String(Files.readAllBytes(Paths.get(fullFilename)));
		}
		text = combineService.combine(text, language, kana, complexityLevel);
		model.addAttribute("text",  text);
		model.addAttribute("filename", filename);

		return "filltextarea";
	}
}