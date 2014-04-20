package com.ibus.mediabeaver.core.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;
import com.ibus.mediabeaver.core.util.RegExHelper;

public class RegExGeneratorTests {

	@Test
	public void captureStringsTest() {
		String fileName = "Iron-Man (1992).mkv";
		String exp = "(.+)[\\(\\[\\{]([0-9]{4})[\\)\\]\\}]";

		RegExHelper rgen = new RegExHelper();

		List<String> list = rgen.captureStrings(exp, fileName);

		assertTrue(list.size() == 3);
		assertTrue(list.get(1).equals("Iron-Man "));
		assertTrue(list.get(2).equals("1992"));

	}

	@Test
	public void cleanStringRegExTest() {
		String fileName = "Iron-Man (1992).mkv";
		String exp = "(.+)[\\(\\[\\{]([0-9]{4})[\\)\\]\\}]";
		String movieNameCleanerExpression = "([a-z,A-Z']+)";
		String joinString = " ";

		RegExHelper rgen = new RegExHelper();

		List<String> list = rgen.captureStrings(exp, fileName);

		assertTrue(list.size() == 3);
		assertTrue(list.get(1).equals("Iron-Man "));
		assertTrue(list.get(2).equals("1992"));

		String movieName = rgen.cleanStringRegEx(movieNameCleanerExpression,
				list.get(1), joinString);
		assertTrue(movieName.equals("Iron Man"));

	}

	@Test
	public void assembleStringTest() {
		// assertTrue(true);

		String fileName = "Iron-Man (1992).mkv";
		String exp = "(.+)[\\(\\[\\{]([0-9]{4})[\\)\\]\\}]";
		String movieNameCleanerExpression = "([a-z,A-Z']+)";
		String joinString = " ";
		String nameAssembleString = "name: {1}";
		String yearAssembleString = "{2}";

		RegExHelper rgen = new RegExHelper();

		// capture groups (i.e movie name and year)
		List<String> list = rgen.captureStrings(exp, fileName);

		assertTrue(list.size() == 3);
		assertTrue(list.get(1).equals("Iron-Man "));
		assertTrue(list.get(2).equals("1992"));

		// assemble groups to form a movie name
		String name = rgen.assembleRegExVariable(list, nameAssembleString);
		String year = rgen.assembleRegExVariable(list, yearAssembleString);
		assertTrue(name.equals("name: Iron-Man "));
		assertTrue(year.equals("1992"));

		String cleanedName = rgen.cleanStringRegEx(movieNameCleanerExpression, name,
				joinString);
		assertTrue(cleanedName.equals("name Iron Man"));
	}

	@Test
	public void containsCaptureGroupTest() {
		RegExHelper rgen = new RegExHelper();

		assertTrue(rgen.containsCaptureGroup("{1} asdf {2}"));
		assertTrue(rgen.containsCaptureGroup("asdf {2}"));
		assertFalse(rgen.containsCaptureGroup(" asdf asdf "));

	}
	
	
	@Test
	public void cleanStringTest() 
	{
		RegExHelper rgen = new RegExHelper();
		
		String before = "asdf-_-_-_-_-xxxx";
		String expectedResult = "asdf xxxx";

		String result = rgen.cleanString(before, "[-_]+", " ");
		
		assertTrue(result.equals(expectedResult));
		
	}
	
	
	
	

}











