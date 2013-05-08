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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import junit.framework.Assert;

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
	private String categories[] = { "alojamento", "hoteis", "hostel", "motel", "musica" };
	private Locale locale[] = { new Locale("pt", "PT"), new Locale("en", "GB") };
	private String reader[][] = {
			{ 
			  "Awolnation",  
			  "Awolnation ao vivo na TMN ao Vivo", 
			  "http://www1.sk-static.com/images/media/img/col6/20110322-001232-973681.jpg"
			},	
			{ 
			  "Sigur Ros",  
			  "Sigur Ros ao vivo no Campo Pequeno", 
			  "http://www1.sk-static.com/images/media/img/col6/20120930-091715-168615.jpg"
			},	
			{ 
			  "Mumford and Sons",  
			  "Mumford and Sons ao vivo no Coliseu de Lisboa", 
			  "http://www2.sk-static.com/images/media/img/col6/20110613-051124-257858.jpg"
			},	
	};
		
	@Before
	public void setUp() throws Exception {
		client = TourismClientFactory.getInstance().getClient(homeUrl);
		client.useVersion("1.0");
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
		String url = "http://polar-lowlands-9873.herokuapp.com/v1/event/";
		Integer id = 1;
		for(Event event : events) {
			assertEquals(event.getBase() + "" + event.getId(), url + (id++));
		}
	}
	
	private boolean hasCategory(String category) {
		for(int i = 0; i < this.categories.length; i++) {
			if(this.categories[i].equals(category))
				return true;
		}
		
		return false;
	}
	
	private void assertCategories(Category category) {
		List<Category> categories;
		
		categories = category.getSubCategories();
		for (Category cat : categories) {
			Assert.assertTrue(hasCategory(DataReader.getLabel(cat, Term.LABEL_TERM_PRIMARY, new Locale("pt", "PT"))));
			if (cat.getNumCategories() > 0) {
				assertCategories(cat);
			}
		}
	}

	@Test
	public void testCategories() throws IOException, UnknownErrorException, InvalidParameterTermException, ServerErrorException, ResourceNotAllowedException, VersionNotAvailableException, InvalidParameterException, InvalidValueException {
		ParameterList list = new ParameterList();
		list.add(new Parameter(ParameterTerms.LIST, ParameterTerms.POIS.getTerm()));
		Category categories = client.getCategories(list);
		assertCategories(categories);
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
		String url = "http://polar-lowlands-9873.herokuapp.com/v1/poi/";
		Integer id = 1;
		for(PointOfInterest poi : pois) {
			poi = client.getPoi(poi.getBase(), poi.getId());
			assertEquals(poi.getBase() + "" + poi.getId(), url + (id++));
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
			int i = 0;
			for(String key : map.keySet()) {
				assertEquals(map.get(key).getLanguage(), locale[i++].getLanguage());
			}
		}
	}
	
	@Test
	public void testDataReader() throws InvalidParameterException, IOException, ResourceNotAllowedException, UnknownErrorException, ServerErrorException, VersionNotAvailableException, InvalidValueException {
		String label = null;
	    String description = null;
	    String thumbnail = null;
	    
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
		int i = 0, j;
		for(Event event : events) {
			j = 0;
			
			label = DataReader.getLabel(event, Term.LABEL_TERM_PRIMARY, locale);
	        description = DataReader.getDescription(event, locale);
	        List<ImageContent> img = DataReader.getThumbnails(event);
	        ImageContent imgContent = null;
	        if(img.size() > 0) {
	            imgContent = img.get(0);
	            thumbnail = imgContent.getContent();
	        }
	                
	        assertEquals(label, reader[i][j++]);
	        assertEquals(description, reader[i][j++]);
	        assertNotNull(imgContent);
	        assertTrue(imgContent.hasImgUri());
	        assertTrue(!imgContent.hasImgByteCode());
	        assertEquals(thumbnail, reader[i++][j++]);
		}
	}
}
