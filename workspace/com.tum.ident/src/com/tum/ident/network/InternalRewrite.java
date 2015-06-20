package com.tum.ident.network;

import java.util.Map;

import com.tum.ident.network.NanoHTTPD.Response;



/**
 * The Class InternalRewrite.
 *
 * @author Paul S. Hawke (paul.hawke@gmail.com) On: 9/15/13 at 2:52 PM
 */
public class InternalRewrite extends Response {
	
	/** The uri. */
	private final String uri;
	
	/** The headers. */
	private final Map<String, String> headers;

	/**
	 * Instantiates a new internal rewrite.
	 *
	 * @param headers the headers
	 * @param uri the uri
	 */
	public InternalRewrite(Map<String, String> headers, String uri) {
		super(null);
		this.headers = headers;
		this.uri = uri;
	}

	/**
	 * Gets the uri.
	 *
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * Gets the headers.
	 *
	 * @return the headers
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}
}
