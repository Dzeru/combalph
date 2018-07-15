package com.dzeru.combalph.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CombineService
{
	private static String kanaPath = Thread.currentThread().getContextClassLoader().getResource("kana").getPath();

	/*
	TODO: Add complexity level
	*/
	public static String combine(String text, String language, String kana) throws IOException
	{
		String path = kanaPath + "/" + kana + "-" + language + ".properties";
		Properties kanaProps = new Properties();
		kanaProps.load(new FileInputStream(path));

		StringBuffer sb = new StringBuffer();

		int i = 0;

		/*if(language.equals("ru"))
		{
			for(int i = 0; i < text.length() - 2; i++)
			{

			}
		}*/

		if(language.equals("en"))
		{
			while (i < text.length())
			{
				boolean hasSpace = false;
				boolean hasEndline = false;

				if(text.charAt(i) == ' ')
				{
					i++;
					hasSpace = true;
				}
				if(text.charAt(i) == '\n')
				{
					i++;
					hasEndline = true;
				}

				char c0 = text.charAt(i);
				char c1 = '.';
				if(i + 1 < text.length())
				{
					c1 = text.charAt(i + 1);
				}

				if (kanaProps.getProperty(Character.toString(c0) + c1) != null)
				{
					if(hasSpace)
						sb.append(' ');
					if(hasEndline)
						sb.append('\n');
					sb.append(kanaProps.getProperty(Character.toString(c0) + c1));
				}
				else if(kanaProps.getProperty(Character.toString(c0)) != null &&
						kanaProps.getProperty(Character.toString(c1)) != null)
				{
					sb.append(kanaProps.getProperty(Character.toString(c0)));
					sb.append(kanaProps.getProperty(Character.toString(c1)));
				}
				else if (kanaProps.getProperty(Character.toString(c0)) != null)
				{
					if(hasSpace)
						sb.append(' ');
					if(hasEndline)
						sb.append('\n');
					sb.append(kanaProps.getProperty(Character.toString(c0)));
					sb.append(c1);
				} else if (kanaProps.getProperty(Character.toString(c1)) != null)
				{
					if(hasSpace)
						sb.append(' ');
					if(hasEndline)
						sb.append('\n');
					sb.append(c0);
					sb.append(kanaProps.getProperty(Character.toString(c1)));
				} else
				{
					if(hasSpace)
						sb.append(' ');
					if(hasEndline)
						sb.append('\n');
					sb.append(c0);
					sb.append(c1);
				}
				i += 2;
			}
		}

		return sb.toString();
	}
}
