package com.dzeru.combalph.services;

import com.dzeru.combalph.objects.ComplexityLevel;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

/*
Service for combining two alphabets: Roman or Cyrillic to hiragana or katakana.
Returns text with kana.
*/

@Service
public class CombineService
{
	public String combine(String text, String language, String kana, String complexityLevel) throws IOException
	{
		InputStream kanaPath = Thread.currentThread().getContextClassLoader().getResourceAsStream("kana/" + kana + ".properties");

		/**
		@see com.dzeru.combalph.objects.ComplexityLevel class for more info.
		 */
		int complvl = ComplexityLevel.valueOf(complexityLevel).getComplvl();

		StringBuffer sb = new StringBuffer();

		Properties kanaProps = new Properties();
		kanaProps.load(kanaPath);

		int i = 0;

		while (i < text.length())
		{
			if (text.charAt(i) == ' ')
			{
				sb.append(' ');
				if(i + 1 < text.length())
					i++;
			}
			if (text.charAt(i) == '\n')
			{
				sb.append('\n');
				if(i + 1 < text.length())
					i++;
			}

			String c0 = Character.toString(text.charAt(i));
			String c1 = ".";

			if (i + 1 < text.length())
			{
				c1 = Character.toString(text.charAt(i + 1));
			}

			/**
			Are used for ru-en translation, if
			 @param language == "ru" if
			 @param language == "en", e0 and e1 equal c0 and c1
			Are used in getProperty()
			 */
			String e0 = c0;
			String e1 = c1;

			if(language.equals("ru"))
			{
				e0 = translate(c0);
				e1 = translate(c1);
			}

			if (kanaProps.getProperty(e0 + e1) != null)
			{
				sb.append(kanaProps.getProperty(e0 + e1));
				i += 2;
				for(int k = i; k < i + complvl && k < text.length(); k++)
				{
					sb.append(text.charAt(k));
				}
				i += complvl;
			}
			else
			{
				if (kanaProps.getProperty(e0) != null)
				{
					sb.append(kanaProps.getProperty(e0)).append(c1);
					i += 2;
					for(int k = i; k < i + complvl && k < text.length(); k++)
					{
						sb.append(text.charAt(k));
					}
					i += complvl;
				}
				if (kanaProps.getProperty(e1) != null)
				{
					sb.append(c0).append(kanaProps.getProperty(e1));
					i += 2;
					for(int k = i; k < i + complvl && k < text.length(); k++)
					{
						sb.append(text.charAt(k));
					}
					i += complvl;
				}
				if (kanaProps.getProperty(e0) == null && kanaProps.getProperty(e1) == null)
				{
					sb.append(c0);
					i += 1;
				}
			}
		}

		return sb.toString();
	}

	/*
	Casts Cyrillic symbols to Roman
	 */
	private static String translate(String s)
	{
		switch(s)
		{
			case "а": s = "a"; return s;
			case "о": s = "o"; return s;
			case "и": s = "i"; return s;
			case "у": s = "u"; return s;
			case "е": s = "e"; return s;
			case "э": s = "e"; return s;

			case "я": s = "ya"; return s;
			case "ё": s = "yo"; return s;
			case "ю": s = "yu"; return s;

			case "й": s = "y"; return s;

			case "к": s = "k"; return s;
			case "г": s = "g"; return s;
			case "п": s = "p"; return s;
			case "б": s = "b"; return s;
			case "х": s = "h"; return s;
			case "м": s = "m"; return s;
			case "н": s = "n"; return s;
			case "р": s = "r"; return s;
			case "д": s = "d"; return s;
			case "т": s = "t"; return s;
			case "с": s = "s"; return s;
			case "з": s = "z"; return s;
			case "в": s = "w"; return s;

			case "А": s = "A"; return s;
			case "О": s = "O"; return s;
			case "И": s = "I"; return s;
			case "У": s = "U"; return s;
			case "Е": s = "E"; return s;
			case "Э": s = "E"; return s;

			case "Я": s = "Ya"; return s;
			case "Ё": s = "Yo"; return s;
			case "Ю": s = "Yu"; return s;

			case "Й": s = "Y"; return s;

			case "К": s = "K"; return s;
			case "Г": s = "G"; return s;
			case "П": s = "P"; return s;
			case "Б": s = "B"; return s;
			case "Х": s = "H"; return s;
			case "М": s = "M"; return s;
			case "Н": s = "N"; return s;
			case "Р": s = "R"; return s;
			case "Д": s = "D"; return s;
			case "Т": s = "T"; return s;
			case "С": s = "S"; return s;
			case "З": s = "Z"; return s;
			case "В": s = "W"; return s;
			default: return s;
		}
	}
}
