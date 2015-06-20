package com.tum.ident.network;

import java.io.IOException;



/**
 * The Class ServerRunner.
 */
public class ServerRunner {
	
	/**
	 * Run.
	 *
	 * @param <T> the generic type
	 * @param serverClass the server class
	 */
	public static <T> void run(Class<T> serverClass) {
		try {
			executeInstance((NanoHTTPD) serverClass.newInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Execute instance.
	 *
	 * @param server the server
	 */
	public static void executeInstance(NanoHTTPD server) {
		try {
			server.start();
		} catch (IOException ioe) {
			System.err.println("Couldn't start server:\n" + ioe);
			System.exit(-1);
		}

	}

	/**
	 * Stop instance.
	 *
	 * @param server the server
	 */
	public static void stopInstance(NanoHTTPD server) {
		server.stop();
	}
}
