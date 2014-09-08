package com.ibus.mediabeaver.core.test;

import java.util.List;

import org.junit.Test;

import com.ibus.mediabeaver.core.entity.OpenSubtitlesField;
import com.ibus.mediabeaver.core.util.PathParser;
import com.ibus.mediabeaver.core.util.ServiceFieldParser;
import com.ibus.mediabeaver.core.util.PathParser.PathToken;

import static org.junit.Assert.assertTrue;

public class PathParserTests
{
	@Test
	public void parsePathTest()
	{
		String path = "Some stuff {Name}.Trim().Replace(\"~`!@#$%^&*()_-+=\",\"hello\") some rugbbish";
		
		PathParser parser = new PathParser();
		List<PathToken> tokens = parser.parsePath(path);
		
		assertTrue(true);
	}
	
	
	
	
}
