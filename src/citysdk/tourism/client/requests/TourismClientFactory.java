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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import citysdk.tourism.client.exceptions.ServerErrorException;
import citysdk.tourism.client.exceptions.UnknownErrorException;
import citysdk.tourism.client.parser.JsonParser;
import citysdk.tourism.client.poi.lists.Resources;

/**
 * Factory class allowing the creation of a {@link TourismClient}. For each link, it makes
 * only one HTTP call, fills a {@link TourismClient}, returns it and saves it. In future creations,
 * it verifies if it was already called and if positive returns the result without making 
 * a new HTTP call.
 *  
 * @author Pedro Cruz
 *
 */
public class TourismClientFactory {
	private Map<String, TourismClient> loadedUrls;
	private JsonParser parser;
	private static TourismClientFactory instance = null;

	private TourismClientFactory() { 
		parser = new JsonParser(null);
		loadedUrls = new HashMap<String, TourismClient>();
	}

	/**
	 * Gets the instance of this factory.
	 * @return the singleton instance of {@link TourismClientFactory}.
	 */
	public synchronized static TourismClientFactory getInstance() {
		if(instance == null)
			instance = new TourismClientFactory();

		return instance;
	}

	/**
	 * Gets a new {@link TourismClient} stub.
	 * @param homeUrl the entrypoint of the home URL to query.
	 * @throws IOException thrown in case of socket errors.
	 * @throws UnknownErrorException thrown in case of unforeseen errors.
	 * @throws ServerErrorException thrown if the server returns a code different from HTTP 200 OK.
	 */
	public TourismClient getClient(String homeUrl) throws IOException, UnknownErrorException, ServerErrorException {
		TourismClient client = null;
		if(!loadedUrls.containsKey(homeUrl)){
			client = initializeClient(homeUrl);
			loadedUrls.put(homeUrl, client);
		} else {
			try {
				client = (TourismClient) loadedUrls.get(homeUrl).clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		
		return client;
	}
	
	/*
	 * Initializes a TourismClient stub
	 */
	private TourismClient initializeClient(String homeUrl) throws IOException, UnknownErrorException, ServerErrorException {
		parser.setJson(Request.getResponse(homeUrl));
		Resources links = parser.parseJsonAsResources();
		return new TourismClient(homeUrl, links);
	}
}
