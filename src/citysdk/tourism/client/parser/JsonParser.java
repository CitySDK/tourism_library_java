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
package citysdk.tourism.client.parser;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import citysdk.tourism.client.exceptions.UnknownErrorException;
import citysdk.tourism.client.poi.Deserializable;
import citysdk.tourism.client.poi.lists.ListEvent;
import citysdk.tourism.client.poi.lists.ListPOIS;
import citysdk.tourism.client.poi.lists.ListPointOfInterest;
import citysdk.tourism.client.poi.lists.ListRoute;
import citysdk.tourism.client.poi.lists.ListTag;
import citysdk.tourism.client.poi.lists.Resources;
import citysdk.tourism.client.poi.single.Category;
import citysdk.tourism.client.poi.single.Event;
import citysdk.tourism.client.poi.single.POI;
import citysdk.tourism.client.poi.single.PointOfInterest;
import citysdk.tourism.client.poi.single.Route;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Parses a given JSON message. It allows the deserialization of: 
 * <ul>
 * <li>{@link citysdk.tourism.client.poi.single.PointOfInterest}, {@link citysdk.tourism.client.poi.single.Event}, 
 * {@link citysdk.tourism.client.poi.single.Route};</li>
 * <li>{@link citysdk.tourism.client.poi.single.HypermediaLink}, {@link citysdk.tourism.client.poi.single.Category}, {@link citysdk.tourism.client.poi.single.Tag};</li>
 * <li>Listings and Mappings.</li>
 * </ul>
 * @author pedrocruz
 * 
 */
public class JsonParser {
	private String json;

	/**
	 * Initializes a JsonParser with the given json string. Such JSON can 
	 * be replaced by using its setter.
	 * @param json the json string containing a given description.
	 */
	public JsonParser(String json) {
		this.json = json;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	/**
	 * Parses the JSON as a {@link citysdk.tourism.client.poi.single.PointOfInterest}.
	 * @return a {@link citysdk.tourism.client.poi.single.PointOfInterest} containing the content of the JSON message.
	 * @throws UnknownErrorException thrown in case of unforeseen errors.
	 */
	public PointOfInterest parseJsonAsPointOfInterest() throws UnknownErrorException {
		return (PointOfInterest) parseJson(PointOfInterest.class);
	}

	/**
	 * Parses the JSON as an {@link citysdk.tourism.client.poi.single.Event}.
	 * @return an {@link citysdk.tourism.client.poi.single.Event} containing the content of the JSON message.
	 * @throws UnknownErrorException thrown in case of unforeseen errors. 
	 */
	public Event parseJsonAsEvent() throws UnknownErrorException {
		return (Event) parseJson(Event.class);
	}

	/**
	 * Parses the JSON as a {@link citysdk.tourism.client.poi.single.Route}.
	 * @return a {@link citysdk.tourism.client.poi.single.Route} containing the content of the JSON message
	 * @throws UnknownErrorException thrown in case of unforeseen errors.
	 */
	public Route parseJsonAsRoute() throws UnknownErrorException {
		return (Route) parseJson(Route.class);
	}

	/**
	 * Parses the JSON as a {@link citysdk.tourism.client.poi.lists.ListPointOfInterest}.
	 * @return a {@link citysdk.tourism.client.poi.lists.ListPointOfInterest} containing the content of the JSON message.
	 * @throws UnknownErrorException thrown in case of unforeseen errors.
	 */
	public ListPointOfInterest parseJsonAsListOfPois() throws UnknownErrorException {
		return (ListPointOfInterest) parseJson(ListPointOfInterest.class);
	}

	/**
	 * Parses the JSON as a {@link citysdk.tourism.client.poi.lists.ListEvent}.
	 * @return a {@link citysdk.tourism.client.poi.lists.ListEvent} containing the content of the JSON message.
	 * @throws UnknownErrorException thrown in case of unforeseen errors.
	 */
	public ListEvent parseJsonAsListOfEvents() throws UnknownErrorException {
		return (ListEvent) parseJson(ListEvent.class);
	}

	/**
	 * Parses the JSON as a {@link citysdk.tourism.client.poi.lists.ListRoute}.
	 * @return a {@link citysdk.tourism.client.poi.lists.ListRoute} containing the content of the JSON message.
	 * @throws UnknownErrorException thrown in case of unforeseen errors.
	 */
	public ListRoute parseJsonAsListOfRoutes() throws UnknownErrorException {
		return (ListRoute) parseJson(ListRoute.class);
	}

	/**
	 * Parses the JSON as a {@link citysdk.tourism.client.poi.lists.Resources}.
	 * @return a {@link citysdk.tourism.client.poi.lists.Resources} containing the content of the JSON message
	 * @throws UnknownErrorException thrown in case of unforeseen errors. 
	 */
	public Resources parseJsonAsResources() throws UnknownErrorException {
		return (Resources) parseJson(Resources.class);
	}

	/**
	 * Parses the JSON as a {@link citysdk.tourism.client.poi.single.Category}.
	 * @return a {@link citysdk.tourism.client.poi.single.Category} containing the content of the JSON message
	 * @throws UnknownErrorException thrown in case of unforeseen errors. 
	 */
	public Category parseJsonAsCategory() throws UnknownErrorException {
		return (Category) parseJson(Category.class);
	}

	/**
	 * Parses the JSON as a {@link citysdk.tourism.client.poi.lists.ListTag}.
	 * @return a {@link citysdk.tourism.client.poi.lists.ListTag} containing the content of the JSON message.
	 * @throws UnknownErrorException thrown in case of unforeseen errors. 
	 */
	public ListTag parseJsonAsTags() throws UnknownErrorException {
		return (ListTag) parseJson(ListTag.class);
	}
	
	/**
	 * Parses the JSON as a {@link citysdk.tourism.client.poi.lists.ListPOIS}.
	 * @return a {@link citysdk.tourism.client.poi.lists.ListPOIS} containing the content of the JSON message.
	 * @throws UnknownErrorException thrown in case of unforeseen errors. 
	 */
	public ListPOIS parseJsonAsListPOIS() throws UnknownErrorException {
		return (ListPOIS) parseJson(ListPOIS.class);
	}

	/**
	 * Parses the JSON as a generic {@link citysdk.tourism.client.poi.single.POI}.
	 * @return a {@link citysdk.tourism.client.poi.single.POI} containing the content of the JSON message.
	 * @throws UnknownErrorException thrown in case of unforeseen errors. 
	 */
	public Deserializable parseJsonAsGeneric() throws UnknownErrorException {
		return parseJson(POI.class);
	}

	/*
	 * Parses the JSON message taking into account the class specified by each
	 * of the public methods. It will return a W3C object, in which, each method
	 * can cast to its own Object.
	 */
	private Deserializable parseJson(Class<? extends Deserializable> clazz) throws UnknownErrorException {
		if (json == null)
			return null;

		Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.fine("Deserializing for " + clazz);
		logger.finest("JSON is: " + json);
		Deserializable deserialize;
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(clazz, new POIDeserializer());

		Gson gson = builder.create();
		try {
			deserialize = gson.fromJson(json, clazz);
		} catch (Exception e) {
			throw new UnknownErrorException("There was an error handling the request: " + e.getMessage(), e);
		}
		
		logger.fine("Done deserialization");
		return deserialize;
	}
}
