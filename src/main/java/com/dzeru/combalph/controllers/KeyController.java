package com.dzeru.combalph.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.UUID;

@Controller
public class KeyController
{
	@GetMapping("/getkey")
	public String getKey(Model model)
	{
		String key = UUID.randomUUID().toString();
		key = key.substring(0, 5);
		model.addAttribute("key", key);

		return "index";
	}

	@PostMapping("/find")
	public String find(@RequestParam String key)
	{

		return "index";
	}
}
