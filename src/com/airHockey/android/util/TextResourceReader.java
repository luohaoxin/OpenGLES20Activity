package com.airHockey.android.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.Resources;

public class TextResourceReader {

	public static String readTextFileFromResource(Context context, int resourceId) {
		StringBuilder body = new StringBuilder();
		
		try {
			InputStream inputStream = context.getResources().openRawResource(resourceId);
			InputStreamReader inReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inReader);
			
			String nextLine;
			while((nextLine = bufferedReader.readLine()) != null) {
				body.append(nextLine);
				body.append('\n');
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not open resource: " + resourceId, e);
		} catch (Resources.NotFoundException e) {
			throw new RuntimeException("Resource not found: " + resourceId, e);
		}
		
		return body.toString();
	}
}
