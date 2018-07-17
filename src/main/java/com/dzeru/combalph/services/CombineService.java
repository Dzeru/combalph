package com.dzeru.combalph.services;

import com.dzeru.combalph.objects.ComplexityLevel;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/*
Service for combining two alphabets.
Return text with kana and count of kana in the end of text.
TextController cuts the count.
 */
@Service
public class CombineService
{
	//Directory, where matching files are situated
	private static String kanaPath = Thread.currentThread().getContextClassLoader().getResource("kana").getPath();

	private static boolean hasSpace = false;
	private static boolean hasEndline = false;

	public static String combine(String text, String language, String kana, String complexityLevel) throws IOException
	{
		/*
		Enum with int param. @See objects.ComplexityLevel class for more info.
		 */
		ComplexityLevel complexitylvl = ComplexityLevel.valueOf(complexityLevel);
		int complvl = complexitylvl.getComplvl();

		StringBuffer sb = new StringBuffer();

		String path = kanaPath + "/" + kana + ".properties";
		Properties kanaProps = new Properties();
		kanaProps.load(new FileInputStream(path));

		int i = 0;

		if(language.equals("en"))
		{
			while (i < text.length())
			{
				hasSpace = false;
				hasEndline = false;

				//complvl variable special for loop
				int v = complvl;

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

				String c0 = Character.toString(text.charAt(i));
				String c1 = ".";

				if(i + 1 < text.length())
				{
					c1 = Character.toString(text.charAt(i + 1));
				}

				if(v == complvl)
				{
					if (kanaProps.getProperty(c0 + c1) != null)
					{
						hasSpaceOrEndline(sb, hasSpace, hasEndline);
						sb.append(kanaProps.getProperty(c0 + c1));
					}
					else if(kanaProps.getProperty(c0) != null &&
							kanaProps.getProperty(c1) != null)
					{
						sb.append(kanaProps.getProperty(c0)).append(kanaProps.getProperty(c1));
					}
					else if (kanaProps.getProperty(c0) != null)
					{
						hasSpaceOrEndline(sb, hasSpace, hasEndline);
						sb.append(kanaProps.getProperty(c0)).append(c1);
					} else if (kanaProps.getProperty(c1) != null)
					{
						hasSpaceOrEndline(sb, hasSpace, hasEndline);
						sb.append(c0).append(kanaProps.getProperty(c1));
					} else
					{
						hasSpaceOrEndline(sb, hasSpace, hasEndline);
						sb.append(c0).append(c1);
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
					hasSpaceOrEndline(sb, hasSpace, hasEndline);
					sb.append(c0).append(c1);

					i += 2;

					if(v == 0)
						v = complvl;
					else
						v--;
				}
			}
		}
		if(language.equals("ru"))
		{
			System.out.println("Begin");
			String transPath = kanaPath + "/trans.properties";
			Properties transProps = new Properties();
			transProps.load(new FileInputStream(transPath));

			while (i < text.length())
			{
				hasSpace = false;
				hasEndline = false;

				//complvl variable special for loop
				int v = complvl;

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

				String c0 = Character.toString(text.charAt(i));
				String c1 = ".";


				if(i + 1 < text.length())
				{
					c1 = Character.toString(text.charAt(i + 1));
				}

				/*
				If en-rus property load successfully, @Param rate changes
				 */
				int rate = 0;
				/*
				@Param symb represents english side of en-rus translation
				 */
				String symb = "";

				if(transProps.getProperty(c0 + c1) != null)
				{
					symb = transProps.getProperty(c0 + c1);
					rate = 3;
				}
				else if(transProps.getProperty(c0) != null)
				{
					symb = transProps.getProperty(c0);
					rate = 1;

				}
				else if(transProps.getProperty(c1) != null)
				{
					symb = transProps.getProperty(c1);
					rate = 2;
				}
				System.out.println(symb + " " + rate);
				if(v == complvl)
				{
					if(rate == 0)
					{
						hasSpaceOrEndline(sb, hasSpace, hasEndline);
						sb.append(c0).append(c1);
					}
					if(rate == 1)
					{
						hasSpaceOrEndline(sb, hasSpace, hasEndline);
						if(kanaProps.getProperty(symb) != null)
						{
							sb.append(kanaProps.getProperty(symb)).append(c1);
						}
						else
						{
							sb.append(c0).append(c1);
						}
					}
					if(rate == 2)
					{
						hasSpaceOrEndline(sb, hasSpace, hasEndline);
						if(kanaProps.getProperty(symb) != null)
						{
							sb.append(c0).append(kanaProps.getProperty(symb));
						}
						else
						{
							sb.append(c0).append(c1);
						}
					}
					if(rate == 3)
					{
						if(kanaProps.getProperty(symb) != null)
						{
							sb.append(kanaProps.getProperty(symb));
						}
						else
						{
							sb.append(c0).append(c1);
						}
					}

					i += 2;

					if(v == 0)
						v = complvl;
					else
						v--;
				}
				else
				{
					hasSpaceOrEndline(sb, hasSpace, hasEndline);
					sb.append(c0).append(c1);

					i += 2;

					if(v == 0)
						v = complvl;
					else
						v--;
				}
			}
		}


		return sb.toString();
	}

	private static void hasSpaceOrEndline(StringBuffer sb, boolean hasSpace, boolean hasEndline)
	{
		if(hasSpace)
			sb.append(' ');
		if(hasEndline)
			sb.append('\n');
	}
}
