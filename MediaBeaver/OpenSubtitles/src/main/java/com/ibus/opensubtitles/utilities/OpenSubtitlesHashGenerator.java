package com.ibus.opensubtitles.utilities;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.LongBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/*
 * 
 * this code acquired at:  http://trac.opensubtitles.org/projects/opensubtitles/wiki/HashSourceCodes#Java
 */ 

/** 
 * 
 * an OpenSubtitles hash is used to match a movie file to movie metadata. It can be used to identify files 
 * at the following web services:  
 * 
 * http://api.opensubtitles.org/xml-rpc 
 * http://api.themoviedb.org/2.1/Movie.search/en/xml/   
 * 
*/

public class OpenSubtitlesHashGenerator 
{
	 /**
	 * Size of the chunks that will be hashed in bytes (64 KB)
	 */
	private static final int HASH_CHUNK_SIZE = 64 * 1024;
	
	
	/**
	 * Computes an open subtitles hash for a file. returns an array with 
	 * the hash and the number of bytes in the file. the hash and bytes are both 
	 * used together to identify a file
	 * 
	 * @param and absolute file path
	 * @return
	 * @throws IOException
	 */
	public static OpenSubtitlesHashData computeHash(File file) throws IOException 
	{
			OpenSubtitlesHashData hashData = new OpenSubtitlesHashData(); 
			
			long size = file.length();
			long chunkSizeForFile = Math.min(HASH_CHUNK_SIZE, size);
			
			FileChannel fileChannel = new FileInputStream(file).getChannel();
			
			try {
			        long head = computeHashForChunk(fileChannel.map(MapMode.READ_ONLY, 0, chunkSizeForFile));
			        long tail = computeHashForChunk(fileChannel.map(MapMode.READ_ONLY, Math.max(size - HASH_CHUNK_SIZE, 0), chunkSizeForFile));
			        
			        hashData.setHashData(String.format("%016x", size + head + tail)); 
			        hashData.setTotalBytes(Long.toString(size));
			        
			} finally {
			        fileChannel.close();
			}
			
			return hashData;
	}
	
	
	public static String computeHash(InputStream stream, long length) throws IOException {
	        
	        int chunkSizeForFile = (int) Math.min(HASH_CHUNK_SIZE, length);
	        
	        // buffer that will contain the head and the tail chunk, chunks will overlap if length is smaller than two chunks
	        byte[] chunkBytes = new byte[(int) Math.min(2 * HASH_CHUNK_SIZE, length)];
	        
	        DataInputStream in = new DataInputStream(stream);
	        
	        // first chunk
	        in.readFully(chunkBytes, 0, chunkSizeForFile);
	        
	        long position = chunkSizeForFile;
	        long tailChunkPosition = length - chunkSizeForFile;
	        
	        // seek to position of the tail chunk, or not at all if length is smaller than two chunks
	        while (position < tailChunkPosition && (position += in.skip(tailChunkPosition - position)) >= 0);
	        
	        // second chunk, or the rest of the data if length is smaller than two chunks
	        in.readFully(chunkBytes, chunkSizeForFile, chunkBytes.length - chunkSizeForFile);
	        
	        long head = computeHashForChunk(ByteBuffer.wrap(chunkBytes, 0, chunkSizeForFile));
	        long tail = computeHashForChunk(ByteBuffer.wrap(chunkBytes, chunkBytes.length - chunkSizeForFile, chunkSizeForFile));
	        
	        return String.format("%016x", length + head + tail);
	}
	
	
	private static long computeHashForChunk(ByteBuffer buffer) {
	        
	        LongBuffer longBuffer = buffer.order(ByteOrder.LITTLE_ENDIAN).asLongBuffer();
	        long hash = 0;
	        
	        while (longBuffer.hasRemaining()) {
	                hash += longBuffer.get();
	        }
	        
	        return hash;
	}

}



