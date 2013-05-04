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
package citysdk.tourism.client.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import citysdk.tourism.client.exceptions.InvalidParameterException;
import citysdk.tourism.client.exceptions.InvalidParameterTermException;
import citysdk.tourism.client.exceptions.InvalidValueException;
import citysdk.tourism.client.exceptions.ResourceNotAllowedException;
import citysdk.tourism.client.exceptions.ServerErrorException;
import citysdk.tourism.client.exceptions.UnknownErrorException;
import citysdk.tourism.client.exceptions.VersionNotAvailableException;
import citysdk.tourism.client.parser.DataReader;
import citysdk.tourism.client.parser.Field;
import citysdk.tourism.client.parser.data.ImageContent;
import citysdk.tourism.client.poi.lists.ListEvent;
import citysdk.tourism.client.poi.lists.ListPointOfInterest;
import citysdk.tourism.client.poi.single.Category;
import citysdk.tourism.client.poi.single.Event;
import citysdk.tourism.client.poi.single.PointOfInterest;
import citysdk.tourism.client.requests.Parameter;
import citysdk.tourism.client.requests.ParameterList;
import citysdk.tourism.client.requests.TourismClient;
import citysdk.tourism.client.requests.TourismClientFactory;
import citysdk.tourism.client.terms.ParameterTerms;
import citysdk.tourism.client.terms.Term;

/**
 * Unit tests
 * 
 * @author Pedro Cruz
 *
 */
public class CitySdkTests {
	private TourismClient client;
	private String homeUrl = "http://polar-lowlands-9873.herokuapp.com/?list=backend";
		
	@Before
	public void setUp() throws Exception {
		client = TourismClientFactory.getInstance().getClient(homeUrl);
		client.useVersion("1.0");
	}
	
	@Test
	public void testGeneric() throws Exception {
		ParameterList list = new ParameterList();
		list.add(new Parameter(ParameterTerms.CODE, "http://awolnationmusic.com/"));
		client.getGeneric(list);
	}

	@Test
	public void testListEvent() throws InvalidParameterException, IOException, ResourceNotAllowedException, UnknownErrorException, ServerErrorException, VersionNotAvailableException, InvalidValueException {
		List<Integer> show = new ArrayList<Integer>();
		show.add(0);
		show.add(19);
		
		List<String> category = new ArrayList<String>();
		category.add("Music");
		category.add("Not’cias");
		category.add("Stuff from Stuff");
		
		ParameterList params = new ParameterList();
		params.add(new Parameter(ParameterTerms.CATEGORY, category));
		params.add(new Parameter(ParameterTerms.TAG, "rock"));
		params.add(new Parameter(ParameterTerms.SHOW, show));
		ListEvent eventList = client.getEvents(params);
		List<Event> events = eventList.getEvents();
		for(Event event : events) {
			System.out.println(event.getBase() + "" + event.getId());
		}
	}
	
	private static void printCategories(Category category) {
		List<Category> categories;
		
		categories = category.getSubCategories();
		for (Category cat : categories) {
			System.out.println("Category: " + DataReader.getLabel(cat, Term.LABEL_TERM_PRIMARY, new Locale("pt", "PT")));
			if (cat.getNumCategories() > 0) {
				printCategories(cat);
			}
		}
	}

	@Test
	public void testCategories() throws IOException, UnknownErrorException, InvalidParameterTermException, ServerErrorException, ResourceNotAllowedException, VersionNotAvailableException, InvalidParameterException, InvalidValueException {
		ParameterList list = new ParameterList();
		list.add(new Parameter(ParameterTerms.LIST, ParameterTerms.POIS.getTerm()));
		Category categories = client.getCategories(list);
		printCategories(categories);
	}
	
	@Test
	public void testPoiWithId() throws InvalidParameterException, IOException, UnknownErrorException, ServerErrorException, ResourceNotAllowedException, VersionNotAvailableException, InvalidValueException {
		List<Integer> show = new ArrayList<Integer>();
		show.add(0);
		show.add(19);
		
		List<String> category = new ArrayList<String>();
		category.add("Museum");
		category.add("Garden");
		
		ParameterList params = new ParameterList();
		params.add(new Parameter(ParameterTerms.CATEGORY, category));
		params.add(new Parameter(ParameterTerms.TAG, "culture"));
		params.add(new Parameter(ParameterTerms.SHOW, show));
		ListPointOfInterest poiList = client.getPois(params);
		List<PointOfInterest> pois = poiList.getPois();
		for(PointOfInterest poi : pois) {
			poi = client.getPoi(poi.getBase(), poi.getId());
			System.out.println(poi.getBase() + "" + poi.getId());
		}
	}
	
	@Test
	public void testAvailableLanguages() throws InvalidParameterException, IOException, ResourceNotAllowedException, UnknownErrorException, ServerErrorException, VersionNotAvailableException, InvalidValueException {
		List<Integer> show = new ArrayList<Integer>();
		show.add(0);
		show.add(19);
		
		List<String> category = new ArrayList<String>();
		category.add("Museum");
		category.add("Garden");
		
		ParameterList params = new ParameterList();
		params.add(new Parameter(ParameterTerms.CATEGORY, category));
		params.add(new Parameter(ParameterTerms.TAG, "culture"));
		params.add(new Parameter(ParameterTerms.SHOW, show));
		
		ListPointOfInterest poiList = client.getPois(params);
		List<PointOfInterest> pois = poiList.getPois();
		for(PointOfInterest poi : pois) {
			Map<String, Locale> map = DataReader.getAvailableLangs(poi, Field.FIELD_LABEL);
			for(String key : map.keySet()) {
				System.out.println(key + ": " + map.get(key));
			}
		}
	}
	
	@Test
	public void testDataReader() throws InvalidParameterException, IOException, ResourceNotAllowedException, UnknownErrorException, ServerErrorException, VersionNotAvailableException, InvalidValueException {
		String label = null;
	    String description = null;
	    String thumbnail = null;
	    String image = null;
	    
	    List<Integer> show = new ArrayList<Integer>();
		show.add(0);
		show.add(19);
		
		List<String> category = new ArrayList<String>();
		category.add("Music");
		category.add("Live");
		
		List<String> tag = new ArrayList<String>();
		tag.add("rock");
		tag.add("indie");
		
		ParameterList params = new ParameterList();
		params.add(new Parameter(ParameterTerms.CATEGORY, category));
		params.add(new Parameter(ParameterTerms.TAG, tag));
		params.add(new Parameter(ParameterTerms.SHOW, show));
		
		ListEvent eventList = client.getEvents(params);
		List<Event> events = eventList.getEvents();
		
	    DataReader.setDefaultLocale(new Locale("en","GB"));
	    Locale locale = new Locale("pt", "PT");
		
		for(Event event : events) {
			label = DataReader.getLabel(event, Term.LABEL_TERM_PRIMARY, locale);
	         
	        description = DataReader.getDescription(event, locale);
	         
	        List<ImageContent> img = DataReader.getThumbnails(event);
	        ImageContent imgContent = null;
	        if(img.size() > 0) {
	            imgContent = img.get(0);
	            thumbnail = imgContent.getContent();
	        }
	         
	        List<ImageContent> imgUri = DataReader.getImagesUri(event);
	        if(imgUri.size() > 0)
	            image = imgUri.get(0).getContent();
	                
	        System.out.println("LABEL: " + label);
	        System.out.println("DESCRIPTION: " + description);
	        if(imgContent != null) {
	            System.out.println("THUMBNAIL (URI?: " + imgContent.hasImgUri() + ")" + 
	                    ";(BYTE-CODE?: " + imgContent.hasImgByteCode() + ") : " + thumbnail);
	        } else {
	            System.out.println("THUMBNAIL: " + thumbnail);
	        }
	         
	        System.out.println("IMAGE: " + image);
		}
	}
}
