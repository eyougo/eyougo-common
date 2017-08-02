package com.eyougo.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemInfoParser {

	private SystemInfoParser() {
	}

	public static String getHostName() throws IOException {
		String os = System.getProperty("os.name");
		if (os != null && !os.startsWith("Windows")) {
			Process p = Runtime.getRuntime().exec("hostname");
			try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
				return br.readLine();
			}
		} else if (os != null) {
			return System.getenv("COMPUTERNAME");
		}
		return null;
	}

}