package com.dzeru.combalph.services;

import com.dzeru.combalph.objects.ComplexityLevel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/*
Service for combining two alphabets.
Return text with kana and count of kana in the end of text.
TextController cuts the count.
 */
public class CombineService
{
	//Directory, where matching files are situated
	private static String kanaPath = Thread.currentThread().getContextClassLoader().getResource("kana").getPath();

	public static String combine(String text, String language, String kana, String complexityLevel) throws IOException
	{
		String path = kanaPath + "/" + kana + "-" + language + ".properties";
		Properties kanaProps = new Properties();
		kanaProps.load(new FileInputStream(path));

		/*
		Enum with int param. @See objects.ComplexityLevel class for more info.
		 */
		ComplexityLevel complexitylvl = ComplexityLevel.valueOf(complexityLevel);
		int complvl = complexitylvl.getComplvl();

		StringBuffer sb = new StringBuffer();
		int kanaCount = 0;

		int i = 0;

		/*if(language.equals("ru"))
		{
			for(int i = 0; i < text.length() - 2; i++)
			{

			}
		}*/

		if(language.equals("en"))
		{
			//complvl variable special for loop
			int v = complvl;

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

				if(v == complvl)
				{
					if (kanaProps.getProperty(Character.toString(c0) + c1) != null)
					{
						if(hasSpace)
							sb.append(' ');
						if(hasEndline)
							sb.append('\n');
						sb.append(kanaProps.getProperty(Character.toString(c0) + c1));
						kanaCount++;
					}
					else if(kanaProps.getProperty(Character.toString(c0)) != null &&
							kanaProps.getProperty(Character.toString(c1)) != null)
					{
						sb.append(kanaProps.getProperty(Character.toString(c0)));
						sb.append(kanaProps.getProperty(Character.toString(c1)));
						kanaCount += 2;
					}
					else if (kanaProps.getProperty(Character.toString(c0)) != null)
					{
						if(hasSpace)
							sb.append(' ');
						if(hasEndline)
							sb.append('\n');

						sb.append(kanaProps.getProperty(Character.toString(c0)));
						sb.append(c1);
						kanaCount++;
					} else if (kanaProps.getProperty(Character.toString(c1)) != null)
					{
						if(hasSpace)
							sb.append(' ');
						if(hasEndline)
							sb.append('\n');

						sb.append(c0);
						sb.append(kanaProps.getProperty(Character.toString(c1)));
						kanaCount++;
					} else
					{
						if(hasSpace)
							sb.append(' ');
						if(hasEndline)
							sb.append('\n');
						sb.append(c0);
						sb.append(c1);
						v--;
					}

					i += 2;

					if(v == 0)
						v = complvl;
					else
						v--;
				}
				else
				{
					if(hasSpace)
						sb.append(' ');
					if(hasEndline)
						sb.append('\n');
					sb.append(c0);
					sb.append(c1);

					i += 2;

					if(v == 0)
						v = complvl;
					else
						v--;
				}
			}
		}

		sb.append(" ").append(kanaCount);
		return sb.toString();
	}
}
