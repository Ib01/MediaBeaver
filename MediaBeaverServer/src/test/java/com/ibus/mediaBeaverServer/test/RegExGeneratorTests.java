package com.ibus.mediaBeaverServer.test;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;
import com.ibus.mediaBeaverServer.util.RegExGenerator;

public class RegExGeneratorTests 
{
	
	@Test
	public void captureStringsTest()
	{
		String fileName = "Iron-Man (1992).mkv";
		String exp = "(.+)[\\(\\[\\{]([0-9]{4})[\\)\\]\\}]";
		
		RegExGenerator rgen = new RegExGenerator();
		
		List<String> list = rgen.captureStrings(exp, fileName);
		
		assertTrue(list.size() == 3);
		assertTrue(list.get(1).equals("Iron-Man "));
		assertTrue(list.get(2).equals("1992"));
		
	}
	
	@Test
	public void cleanStringTest()
	{
		String fileName = "Iron-Man (1992).mkv";
		String exp = "(.+)[\\(\\[\\{]([0-9]{4})[\\)\\]\\}]";
		String movieNameCleanerExpression = "([a-z,A-Z']+)";
		String joinString = " ";
		
		RegExGenerator rgen = new RegExGenerator();
		
		List<String> list = rgen.captureStrings(exp, fileName);
		
		assertTrue(list.size() == 3);
		assertTrue(list.get(1).equals("Iron-Man "));
		assertTrue(list.get(2).equals("1992"));
		
		String movieName = rgen.cleanString(movieNameCleanerExpression, list.get(1), joinString);
		assertTrue(movieName.equals("Iron Man"));
		
	}
	
	@Test
	public void assembleStringTest()
	{
		//assertTrue(true);
		
		
		String fileName = "Iron-Man (1992).mkv";
		String exp = "(.+)[\\(\\[\\{]([0-9]{4})[\\)\\]\\}]";
		String movieNameCleanerExpression = "([a-z,A-Z']+)";
		String joinString = " ";
		String nameAssembleString = "name: {1}";
		String yearAssembleString = "{2}";
		
		RegExGenerator rgen = new RegExGenerator();
		
		//capture groups (i.e movie name and year)
		List<String> list = rgen.captureStrings(exp, fileName);
		
		assertTrue(list.size() == 3);
		assertTrue(list.get(1).equals("Iron-Man "));
		assertTrue(list.get(2).equals("1992"));
		
		//assemble groups to form a movie name
		String name = rgen.assembleString(list, nameAssembleString);
		String year = rgen.assembleString(list, yearAssembleString);
		assertTrue(name.equals("name: Iron-Man "));
		assertTrue(year.equals("1992"));
		
		
		String cleanedName = rgen.cleanString(movieNameCleanerExpression, name, joinString);
		assertTrue(cleanedName.equals("name Iron Man"));
	}
	
	@Test
	public void containsCaptureGroupTest()
	{
		RegExGenerator rgen = new RegExGenerator();
		
		assertTrue(rgen.containsCaptureGroup("{1} asdf {2}"));
		assertTrue(rgen.containsCaptureGroup("asdf {2}"));
		assertFalse(rgen.containsCaptureGroup(" asdf asdf "));
		
	}
	

}

























