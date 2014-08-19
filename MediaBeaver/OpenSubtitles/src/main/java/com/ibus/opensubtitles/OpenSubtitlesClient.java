package com.ibus.opensubtitles;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import com.ibus.opensubtitles.dto.OpenSubtitlesResponse;
import com.ibus.opensubtitles.utilities.OpenSubtitlesHashData;

public class OpenSubtitlesClient
{
	/*
	 * useragent: used by opensubtitles to identify the connecting application.
	 * Looks like u/n and pass can be blank if this is a test 
	 * userName: name of the connecting user 
	 * password: password of the connecting user 
	 * host: the OpenSubtitles web service host 
	 * sublanguageid: the id of the language(s) to download subtitles for. 
	 * token: a security token. Once logged on the security token will remain valid for 30 minutes.
	 */

	// private static Logger logger = Logger.getLogger("moviejukebox");

	//TODO; INJECT THIS INFORMATION
	private static String userName = " ";
	private static String password = " ";
	private static String useragent = "OS Test User Agent";
	private static String host = "http://api.opensubtitles.org/xml-rpc";
	private static String sublanguageid = "eng";
	private OpenSubtitlesToken token = new OpenSubtitlesToken();
	

	public OpenSubtitlesClient()
	{
	}

	public OpenSubtitlesResponse getTitleForHash(OpenSubtitlesHashData data) throws MalformedURLException, IOException
	{
		if (token.tokenHasExpired())
			throw new RuntimeException("OpenSubtitlesClient token has expired.  you must call OpenSubtitlesClient.login() before calling any other method");

		String requestXml = generateXMLRPCSS(data.getHashData(), data.getTotalBytes());
		String responseXml = sendRPC(requestXml);

		return new OpenSubtitlesResponse(getValue("MovieName", responseXml), getValue("MovieYear", responseXml));
	}

	public void logOut() throws MalformedURLException, IOException
	{
		if (token.tokenHasExpired())
			return;

		String methodParams[] = { token.getToken() };
		String methodName = "LogOut";
		
		String requestXml = generateXMLRPC(methodName, methodParams);
		sendRPC(requestXml);
	}
	
	// this method should be called before any other.
	public boolean login() throws MalformedURLException, IOException
	{
		String methodParams[] =
		{ userName, password, "", useragent };
		String methodName = "LogIn";

		String requestXml = generateXMLRPC(methodName, methodParams);
		String tokenXml = sendRPC(requestXml);

		// store the token for future calls
		token.setToken(getValue("token", tokenXml));
		return !token.tokenHasExpired();
	}

	
	
	private String generateXMLRPC(String methodName, String s[])
	{
		StringBuffer str = new StringBuffer();
		str.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><methodCall><methodName>");
		str.append(methodName + "</methodName><params>");

		for (int i = 0; i < s.length; i++)
		{
			str.append("<param><value><string>" + s[i]
					+ "</string></value></param>");
		}

		str.append("</params></methodCall>");
		return str.toString();
	}

	private String sendRPC(String xml) throws MalformedURLException, IOException
	{
		StringBuffer str = new StringBuffer();
		URL url = new URL(host);
		
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("Connection", "Close"); //ensures connections not reused?
		connection.setRequestProperty("Content-Type", "text/xml");
		connection.setDoOutput(true);

		connection.getOutputStream().write(xml.getBytes("UTF-8"));

		Scanner in = new Scanner(connection.getInputStream());
		while (in.hasNextLine())
		{
			str.append(in.nextLine());
		}
		in.close();
		
		((HttpURLConnection) connection).disconnect();

		return str.toString();
	}

	private String getValue(String toFind, String xml)
	{
		String substring = "";
		int foundAt = xml.indexOf(toFind);
		if (foundAt != -1)
		{
			int getFrom = xml.indexOf("<string>", foundAt);
			int getTo = xml.indexOf("</string>", getFrom);
			if ((getFrom != -1) && (getTo != -1))
			{
				return xml.substring(getFrom + 8, getTo);
			}
		}

		return substring;
	}

	private String generateXMLRPCSS(String moviehash, String moviebytesize)
	{
		String str = "";
		str += "<?xml version=\"1.0\" encoding=\"utf-8\"?><methodCall><methodName>";
		str += "SearchSubtitles";
		str += "</methodName><params><param><value><string>";
		str += token.getToken();
		str += "</string></value></param><param><value><struct>";
		str += "<member><value><struct>";

		str += addymember("sublanguageid", sublanguageid);
		str += addymember("moviehash", moviehash);
		str += addymember("moviebytesize", moviebytesize);

		str += "</struct></value></member>";

		str += "</struct></value></param></params></methodCall>";
		return str;
		
		/*String methodName = "SearchSubtitles";
		str += token.getToken();
		
		String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
				+ "<methodCall>"
				+ "<methodName>%s</methodName>"
				+ "<params>"
				+ "<param><value><string>%s</string></value></param>";
				+ "<param><value><struct>"
				
		str += "<member><value><struct>";

		str += addymember("sublanguageid", sublanguageid);
		str += addymember("moviehash", moviehash);
		str += addymember("moviebytesize", moviebytesize);

		str += "</struct></value></member>";

		str += "</struct></value></param></params></methodCall>";
		return str;*/
		
	};

	private String addymember(String name, String value)
	{
		return "<member><name>" + name + "</name><value><string>"
				+ addEncje(value) + "</string></value></member>";
	}

	private String addEncje(String a)
	{
		a = a.replace("&", "&amp;");
		a = a.replace("<", "&lt;");
		a = a.replace(">", "&gt;");
		a = a.replace("'", "&apos;");
		a = a.replace("\"", "&quot;");
		return a;
	}

	/***************************************************************************
	 ************************************************************************** 
	 * 
	 * Not in use
	 * 
	 *************************************************************************** 
	 ****************************************************************************/

	/*
	 * 
	 * 
	 * private String generateXMLRPCSS(String query) { String str = ""; str +=
	 * "<?xml version=\"1.0\" encoding=\"utf-8\"?><methodCall><methodName>"; str
	 * += "SearchSubtitles"; str +=
	 * "</methodName><params><param><value><string>"; str += token.getToken();
	 * str += "</string></value></param><param><value><struct>";
	 * 
	 * str += "<member><value><struct>";
	 * 
	 * str += addymember("sublanguageid", sublanguageid); str +=
	 * addymember("query", query);
	 * 
	 * str += "</struct></value></member>";
	 * 
	 * str += "</struct></value></param></params></methodCall>"; return str; };
	 * 
	 * private String generateXMLRPCUS(String idmovieimdb, String subhash[],
	 * String subcontent[], String subfilename[], String moviehash[], String
	 * moviebytesize[], String movietimems[], String movieframes[], String
	 * moviefps[], String moviefilename[]) { String str = ""; str +=
	 * "<?xml version=\"1.0\" encoding=\"utf-8\"?><methodCall><methodName>"; str
	 * += "UploadSubtitles"; str +=
	 * "</methodName><params><param><value><string>"; str += token.getToken();
	 * str += "</string></value></param><param><value><struct>";
	 * 
	 * for (int i = 0; i < subhash.length; i++) { str += "<member><name>" + "cd"
	 * + (i + 1) + "</name><value><struct>"; str += addymember("movietimems",
	 * movietimems[i]); str += addymember("moviebytesize", moviebytesize[i]);
	 * str += addymember("subfilename", subfilename[i]); str +=
	 * addymember("subcontent", subcontent[i]); str += addymember("subhash",
	 * subhash[i]); str += addymember("movieframes", movieframes[i]); str +=
	 * addymember("moviefps", moviefps[i]); str += addymember("moviefilename",
	 * moviefilename[i]); str += addymember("moviehash", moviehash[i]);
	 * 
	 * str += "</struct></value></member>"; }
	 * 
	 * str += "<member><name>baseinfo</name><value><struct>"; str +=
	 * addymember("sublanguageid", sublanguageid); str +=
	 * addymember("idmovieimdb", idmovieimdb); str +=
	 * addymember("subauthorcomment", ""); str += addymember("movieaka", "");
	 * str += addymember("moviereleasename", "");
	 * 
	 * str += "</struct></value></member>";
	 * 
	 * str += "</struct></value></param></params></methodCall>"; return str; }
	 * 
	 * private String generateXMLRPCTUS(String subhash[], String subfilename[],
	 * String moviehash[], String moviebytesize[], String movietimems[], String
	 * movieframes[], String moviefps[], String moviefilename[]) { String str =
	 * ""; str +=
	 * "<?xml version=\"1.0\" encoding=\"utf-8\"?><methodCall><methodName>"; str
	 * += "TryUploadSubtitles"; str +=
	 * "</methodName><params><param><value><string>"; str += token.getToken();
	 * str += "</string></value></param><param><value><struct>";
	 * 
	 * for (int i = 0; i < subhash.length; i++) { str += "<member><name>" + "cd"
	 * + (i + 1) + "</name><value><struct>"; str += addymember("movietimems",
	 * movietimems[i]); str += addymember("moviebytesize", moviebytesize[i]);
	 * str += addymember("subfilename", subfilename[i]); str +=
	 * addymember("subhash", subhash[i]); str += addymember("movieframes",
	 * movieframes[i]); str += addymember("moviefps", moviefps[i]); str +=
	 * addymember("moviefilename", moviefilename[i]); str +=
	 * addymember("moviehash", moviehash[i]);
	 * 
	 * str += "</struct></value></member>"; }
	 * 
	 * str += "</struct></value></param></params></methodCall>"; return str; };
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * private String getIntValue(String find, String xml) { String str = "";
	 * int a = xml.indexOf(find); if (a != -1) { int b = xml.indexOf("<int>",
	 * a); int c = xml.indexOf("</int>", b); if ((b != -1) && (c != -1)) {
	 * return xml.substring(b + 5, c); } } ; return str; }
	 * 
	 * private String hashstring(byte[] arayhash) { StringBuffer str = new
	 * StringBuffer(); String hex = "0123456789abcdef"; for (int i = 0; i <
	 * arayhash.length; i++) { int m = arayhash[i] & 0xff;
	 * str.append(hex.charAt(m >> 4) + hex.charAt(m & 0xf)); } ; return
	 * str.toString(); }
	 * 
	 * @SuppressWarnings("unused") private String
	 * generateXMLRPCDetectLang(String body) { String str = ""; str +=
	 * "<?xml version=\"1.0\" encoding=\"utf-8\"?><methodCall><methodName>"; str
	 * += "DetectLanguage" + "</methodName><params>";
	 * 
	 * str += "<param><value><string>" + token.getToken() +
	 * "</string></value></param>"; str += "<param><value>" + body +
	 * " </value></param>";
	 * 
	 * str += "</params></methodCall>"; return str; }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * private String tuBase64(byte s[]) { // You may use this for lower applet
	 * size // return new sun.misc.BASE64Encoder().encode(s);
	 * 
	 * // char tx; // long mili = Calendar.getInstance().getTimeInMillis();
	 * String str = ""; String t =
	 * "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"; char
	 * t2[] = t.toCharArray(); char wynik[] = new char[(s.length / 3 + 1) * 4];
	 * 
	 * int tri = 0; for (int i = 0; i < (s.length / 3 + 1); i++) { tri = 0; int
	 * iii = i * 3; try { tri |= (s[iii] & 0xff) << 16; tri |= (s[iii + 1] &
	 * 0xff) << 8; tri |= (s[iii + 2] & 0xff); } catch (Exception error) { } ;
	 * for (int j = 0; j < 4; j++) { wynik[i * 4 + j] = (iii * 8 + j * 6 >=
	 * s.length * 8) ? '=' : t2[(tri >> 6 * (3 - j)) & 0x3f]; } // if((i+1) % 19
	 * ==0 ) str +="\n"; } ; str = new String(wynik); if (str.endsWith("===="))
	 * { str = str.substring(0, str.length() - 4); }
	 * 
	 * return str; }
	 * 
	 * 
	 * 
	 * private String getHash(File f) throws IOException { // Open the file and
	 * then get a channel from the stream FileInputStream fis = new
	 * FileInputStream(f); FileChannel fc = fis.getChannel(); long sz =
	 * fc.size();
	 * 
	 * if (sz < 65536) { fc.close(); return "NoHash"; }
	 * 
	 * MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, 65536);
	 * long sum = sz;
	 * 
	 * bb.order(ByteOrder.LITTLE_ENDIAN); for (int i = 0; i < 65536 / 8; i++) {
	 * sum += bb.getLong();// sum(bb); } bb =
	 * fc.map(FileChannel.MapMode.READ_ONLY, sz - 65536, 65536);
	 * bb.order(ByteOrder.LITTLE_ENDIAN); for (int i = 0; i < 65536 / 8; i++) {
	 * sum += bb.getLong();// sum(bb); } sum = sum & 0xffffffffffffffffL;
	 * 
	 * String s = String.format("%016x", sum); fc.close(); fis.close(); return
	 * s; }
	 */
}