package se.klartbra.dupo.controller.network;

public class Paths {

	/*
	 * NETWORK PATHS
	 */
	
	public static final String RELATIVE_HELP_FILE_PATH="docs/dupo";

	public static final int DEFAULT_SERVER_TCP_PORT=80;
	
	public static final String DEFAULT_SERVER_URL_HTML= "http://www.klartbra.se/"; 
	public static final String DEFAULT_SERVER_URL= "http://ec2-52-33-219-92.us-west-2.compute.amazonaws.com/";
	public static final String SERVER_HELP_FILE = DEFAULT_SERVER_URL_HTML+RELATIVE_HELP_FILE_PATH;
	
	public static final String GOOGLE_URL = "http://www.google.com/";
	public static final String GOOGLE_SEARCH_URL = GOOGLE_URL + "/search?";
	public static final String GOOGLE_SEARCH_URL2 = GOOGLE_URL + "/search?q=";

private Paths() {}

}