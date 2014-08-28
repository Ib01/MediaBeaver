package com.ibus.mediabeaver.core.test;

import org.junit.Test;

import com.ibus.mediabeaver.core.entity.OpenSubtitlesField;
import com.ibus.mediabeaver.core.util.ServiceFieldParser;
import static org.junit.Assert.assertTrue;

public class ServiceFieldParserTests
{
	@Test
	public void getFieldIdentifierTest()
	{
		String transformFormat = "{MovieYear}.replaceAll(regex, replacement)";
		
		ServiceFieldParser helper = new ServiceFieldParser();
		
		String result = helper.getFieldIdentifier(transformFormat);
		assertTrue(result != null && result.equals("MovieYear"));
	}
	
	@Test
	public void parseFieldTest()
	{
		String transformFormat = "{MovieYear}.replaceAll(\"\\s*\\.\\s*\", \" \").leftPad(\"10\", \"-\")";
		String fieldValue = "Iron.Man";
		
		ServiceFieldParser helper = new ServiceFieldParser();
		String parsedField = helper.parseField(fieldValue, transformFormat);
		
		assertTrue(parsedField != null && parsedField.equals("--Iron Man"));
		
	}
	
	
}
