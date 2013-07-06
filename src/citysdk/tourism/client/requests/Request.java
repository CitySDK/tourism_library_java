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
import java.util.logging.LogManager;
import java.util.logging.Logger;

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
		Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.info("Queried " + Url + " with response code " + code);
		if(code == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(httpUrl.getInputStream()));
			String inputLine;
			String answer = "";
	
			while ((inputLine = in.readLine()) != null) 
				answer += inputLine;
	
			in.close();
			httpUrl.disconnect();
			logger.fine("Answer: " + answer);
			return answer;
		} else {
			String read, message = "";
			logger.warning("Error code " + code);
			try {
				BufferedReader inStream = 
						new BufferedReader(new InputStreamReader(httpUrl.getErrorStream()));
				while ((read = inStream.readLine()) != null)
					message += read;
				
				inStream.close();
				httpUrl.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			logger.severe("Error message " + message);
			throw new ServerErrorException(message);
		}
	}
}
