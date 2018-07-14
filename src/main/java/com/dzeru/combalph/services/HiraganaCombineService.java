package com.dzeru.combalph.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class HiraganaCombineService extends CombineService
{
	/*
	TODO: Add complexity level
	*/
	public static String combine(String text, String language) throws IOException
	{
		String path = kanaPath + "/hiragana-" + language + ".properties";
		Properties hiraganaProperties = new Properties();
		hiraganaProperties.load(new FileInputStream(path));

		System.out.println(path);

		System.out.println(hiraganaProperties.getProperty("i"));

		StringBuffer sb = new StringBuffer();

		int i = 0;

		/*if(language.equals("ru"))
		{
			for(int i = 0; i < text.length() - 2; i++)
			{

			}
		}*/

		//TODO: deal with spaces
		if(language.equals("en"))
		{
			while (i < text.length() - 2)
			{
				if(text.charAt(i) == ' ' && !(text.charAt(i + 2) == ' '))
					i++;

				char c0 = text.charAt(i);
				char c1 = text.charAt(i + 1);

				if (hiraganaProperties.getProperty(Character.toString(c0) + c1) != null)
				{
					sb.append(hiraganaProperties.getProperty(Character.toString(c0) + c1));
				} else if (hiraganaProperties.getProperty(Character.toString(c0)) != null)
				{
					sb.append(hiraganaProperties.getProperty(Character.toString(c0)));
					sb.append(c1);
				} else if (hiraganaProperties.getProperty(Character.toString(c1)) != null)
				{
					sb.append(c0);
					sb.append(hiraganaProperties.getProperty(Character.toString(c1)));
				} else
				{
					sb.append(c0);
					sb.append(c1);
				}
				i += 2;
			}
		}




/*
				if(hiraganaProperties.getProperty(Character.toString(c0) + c1) == null)
				{
					if(hiraganaProperties.getProperty(Character.toString(c0))== null)
					{
						sb.append(c0);
						sb.append(c1);
					}
					else
					{
						sb.append(hiraganaProperties.getProperty(Character.toString(c0)));
						sb.append(c1);
					}
				}
				else
				{
					sb.append(hiraganaProperties.getProperty(Character.toString(c0) + c1));
				}
				i++;
			}


		}*/

		System.out.println(sb.toString());

		return sb.toString();
	}
}
