/**
 * COPYRIGHT NOTICE: 
 *
 * This file is part of CitySDK WP5 Tourism Java Library.
 *
 * CitySDK WP5 Tourism Java Library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CitySDK WP5 Tourism Java Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with CitySDK WP5 Tourism Java Library. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2013, IST
 */
package citysdk.tourism.client.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import citysdk.tourism.client.exceptions.ServerErrorException;

/**
 * Used to perform HTTP requests.
 * 
 * @author Pedro Cruz
 *
 */
public class Request {
	/*
	 * Gets the response after querying a given URL
	 */
	protected static String getResponse(String Url) throws IOException, ServerErrorException {
		if(Url == null)
			return null;
		
		URL url = new URL(Url);
		HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
		httpUrl.setRequestMethod("GET");
		httpUrl.setRequestProperty("Accept", "application/json");
		httpUrl.connect();
		
		int code = httpUrl.getResponseCode();
		if(code == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(httpUrl.getInputStream()));
			String inputLine;
			String answer = "";
	
			while ((inputLine = in.readLine()) != null) 
				answer += inputLine;
	
			in.close();
			
			return answer;
		} else
			throw new ServerErrorException(httpUrl.getResponseMessage());
	}
}
