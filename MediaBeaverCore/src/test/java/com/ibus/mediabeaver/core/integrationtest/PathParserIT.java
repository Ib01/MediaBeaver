package com.ibus.mediabeaver.core.integrationtest;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.ibus.mediabeaver.core.entity.PathToken;
import com.ibus.mediabeaver.core.exception.PathParseException;
import com.ibus.mediabeaver.core.filesystem.PathParser;

public class PathParserIT
{
	@Test
	public void parsePathTest() throws PathParseException
	{
		String path = "Some stuff {SeriesName}.trim().leftPad(\"6\",\"x\") some rugbbish";
		String value = " name ";
		
		//parse path into list of variables > methods > parameters
		List<PathToken> tokens = PathParser.getTokens(path);
		
		//parse service field value using its path token.
		PathToken token = PathParser.parseToken(tokens.get(0), value);
		assertTrue(token.getValue().equals("xxname"));
		
		String parsedPath = PathParser.parsePath(token, path);
		assertTrue(parsedPath.equals("Some stuff xxname some rugbbish"));
	}
	
	
	
	
}
