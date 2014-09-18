package com.ibus.opensubtitles.client.utilities;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.LongBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import com.ibus.opensubtitles.client.entity.OpenSubtitlesHashData;

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
	 * This method does not use memory maps to get the data, so it should be 
	 * unproblematic on both windows and linux systems. However it may be a little slower   
	 * 
	 * @param and absolute file path
	 * @return OpenSubtitlesHashData
	 * @throws IOException
	 */
	public static OpenSubtitlesHashData computeHash(File file) throws IOException 
	{
		return computeHash(new FileInputStream(file),file.length());
	}
	
	
	/**
	 * Computes an open subtitles hash for a file. returns an array with 
	 * the hash and the number of bytes in the file. the hash and bytes are both 
	 * used together to identify a file
	 * 
	 * This method does not use memory maps to get the data, so it should be 
	 * unproblematic on both windows and linux systems. However it may be a little slower   
	 * 
	 * @param and absolute file path
	 * @return OpenSubtitlesHashData
	 * @throws IOException
	 */
	public static OpenSubtitlesHashData computeHash(InputStream stream, long length) throws IOException 
	{
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
        
        OpenSubtitlesHashData hashData = new OpenSubtitlesHashData(); 
        hashData.setTotalBytes(Long.toString(length));
        hashData.setHashData(String.format("%016x", length + head + tail));
        
        in.close();
        stream.close();
        
        return hashData;
	}
	
	
	private static long computeHashForChunk(ByteBuffer buffer) 
	{
	        LongBuffer longBuffer = buffer.order(ByteOrder.LITTLE_ENDIAN).asLongBuffer();
	        long hash = 0;
	        
	        while (longBuffer.hasRemaining()) {
	                hash += longBuffer.get();
	        }
	        
	        return hash;
	}
	
	
	
	/**
	 * Computes an open subtitles hash for a file. returns an array with 
	 * the hash and the number of bytes in the file. the hash and bytes are both 
	 * used together to identify a file
	 * 
	 * This method uses memory maps to get the data. This is problematic under windows as windows 
	 * locks files that have memory mapped in this way and it does not unlock the files until the 
	 * app garbage collects. This method manually garbage collects which ensures that files are 
	 * not locked but this is probably not very efficient     
	 * 
	 * @param and absolute file path
	 * @return OpenSubtitlesHashData
	 * @throws IOException
	 */
	public static OpenSubtitlesHashData computeHashMapped(File file) throws IOException 
	{
		OpenSubtitlesHashData hashData = new OpenSubtitlesHashData(); 
		
		long size = file.length();
		long chunkSizeForFile = Math.min(HASH_CHUNK_SIZE, size);
		
		FileInputStream inStream = new FileInputStream(file);
		FileChannel fileChannel = inStream.getChannel();
		MappedByteBuffer headBuffer;
		MappedByteBuffer tailBuffer;
		try 
		{
			headBuffer = fileChannel.map(MapMode.READ_ONLY, 0, chunkSizeForFile);
			tailBuffer =  fileChannel.map(MapMode.READ_ONLY, Math.max(size - HASH_CHUNK_SIZE, 0), chunkSizeForFile);
	        long head = computeHashForChunk(headBuffer);
	        long tail = computeHashForChunk(tailBuffer);
	        
	        hashData.setHashData(String.format("%016x", size + head + tail)); 
	        hashData.setTotalBytes(Long.toString(size));
		        
		} finally 
		{
	        fileChannel.close();
	        inStream.close();
	        fileChannel = null;
	        inStream= null;
	        headBuffer = null;
	        tailBuffer = null;
	        
	        //TODO: fileChannel.map creates a memory buffer map which stays in bloody memory and effectively locks the file!!!! 
	        //System.gc() frees memory and hence the lock but not convinced this is a good solution. This should only be a 
	        //problem under windows
	        System.gc();
		}
		
		return hashData;
	}

}



