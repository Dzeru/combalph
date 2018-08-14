package com.dzeru.combalph.services;

import org.springframework.stereotype.Service;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Text;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import java.io.File;
import java.util.List;

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
Service for parsing docx files to plain String.
WARNING: works only with docx, not doc files!
Returns String, which will be formatted by CombineService in TextController
 */

@Service
public class ParseDocxService
{
	public String parseDocx(String fullFilename)
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			WordprocessingMLPackage wordPack = WordprocessingMLPackage.load(new File(fullFilename));
			MainDocumentPart mainDocPart = wordPack.getMainDocumentPart();
			String textNodesXPath = "//w:t";
			List<Object> textNodes = mainDocPart.getJAXBNodesViaXPath(textNodesXPath, true);

			for(Object o : textNodes)
			{
				Text text = (Text) ((JAXBElement) o).getValue();
				sb.append(text.getValue());
			}
			return sb.toString();
		}
		catch(Docx4JException | JAXBException e)
		{
			e.printStackTrace();
			return "-1";
		}
	}
}
