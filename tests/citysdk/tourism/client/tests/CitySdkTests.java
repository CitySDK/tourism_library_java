package citysdk.tourism.client.tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
import citysdk.tourism.client.parser.JsonParser;
import citysdk.tourism.client.parser.data.DataContent;
import citysdk.tourism.client.parser.data.ImageContent;
import citysdk.tourism.client.poi.lists.ListEvent;
import citysdk.tourism.client.poi.lists.ListPointOfInterest;
import citysdk.tourism.client.poi.lists.Resources;
import citysdk.tourism.client.poi.single.Category;
import citysdk.tourism.client.poi.single.Event;
import citysdk.tourism.client.poi.single.Hypermedia;
import citysdk.tourism.client.poi.single.HypermediaLink;
import citysdk.tourism.client.poi.single.PointOfInterest;
import citysdk.tourism.client.requests.Parameter;
import citysdk.tourism.client.requests.ParameterList;
import citysdk.tourism.client.requests.TourismClient;
import citysdk.tourism.client.requests.TourismClientFactory;
import citysdk.tourism.client.requests.uri.UriTemplate;
import citysdk.tourism.client.terms.ParameterTerms;
import citysdk.tourism.client.terms.ResourceTerms;
import citysdk.tourism.client.terms.Term;

public class CitySdkTests {
	private TourismClient client;
	private String homeUrl = "http://polar-lowlands-9873.herokuapp.com/?list=backend";
		
	@Before
	public void setUp() throws Exception {
		client = TourismClientFactory.getInstance().getClient(homeUrl);
		client.useVersion("1.0");
	}
	
	@Test
	public void testUriTemplate() {
		template("http://polar-lowlands-9873.herokuapp.com/");
		template("http://polar-lowlands-9873.herokuapp.com/v1/poi/search{/category,tag:2,complete,minimal,coords*,code,show}");
		template("http://polar-lowlands-9873.herokuapp.com/v1/poi/search{?category*,tag:6,complete,minimal,coords*,code,show}");

		String template = "{base}{id}{?relation}/here";
		UriTemplate uri = UriTemplate.fromTemplate(template)
									 .set("base", "http://polar-lowlands-9873.herokuapp.com/v1/poi/")
									 .set("id", "10")
									 .set("relation", "child");
		System.out.println(uri.build());
	}
	
	private void template(String template) {
		List<String> list = new ArrayList<String>();
		list.add("29.000 -39.000");
		list.add("19.000 -19.000");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cat", "category-1");
		map.put("lol", "lol");
		UriTemplate uri = UriTemplate.fromTemplate(template)
									 .set("category", map)
									 .set("coords", list)
									 .set("tag", "ABCDEFGHIJK");
		System.out.println(uri.build());
	}
	
	public void testResources() throws UnknownErrorException {
		String file = "home-queries.txt";
		JsonParser parser = new JsonParser(readFromFile(file));
		Resources resources = parser.parseJsonAsResources();
		Set<String> versions = resources.getVersions();
		for(String version : versions) {
			System.out.println("\nVersion " + version);
			Set<String> res = resources.getResources(version);
			HypermediaLink hypermedia = resources.getHypermediaWithVersion(version);
			for(String r : res) {
				Hypermedia link = hypermedia.getLinks().get(r);
				System.out.print("Resource " + r + " ");
				System.out.println(link.getHref() + "\nTemplated? " + link.isTemplated());
				for(ParameterTerms param : ParameterTerms.values()) {
					if(!param.getTerm().isEmpty())
						System.out.println("Has " + param.getTerm() + "? " + resources.hasResourceParameter(version, ResourceTerms.fromString(r), param));
				}
			}
		}
	}
	
	private static String readFromFile(String file) {
		String DIR = "/Users/pedrocruz/Documents/workspace/citysdk-java-library/examples/";
		BufferedReader br = null;
		String s = "";
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(DIR + file));
			while ((sCurrentLine = br.readLine()) != null) {
				s += sCurrentLine;  
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return s;
	}

	@Test
	public void testListEvent() throws InvalidParameterException, IOException, ResourceNotAllowedException, UnknownErrorException, ServerErrorException, VersionNotAvailableException, InvalidValueException {
		ParameterList params = new ParameterList();
		params.add(new Parameter(ParameterTerms.CATEGORY, "some-category"));
		params.add(new Parameter(ParameterTerms.TAG, "some-tag"));
		params.add(new Parameter(ParameterTerms.SHOW, "0,19"));
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
			System.out.println("Category: " + DataReader.getLabel(cat, Term.LABEL_TERM_PRIMARY, new Locale("pt", "PT")).getContent());
			if (cat.getNumCategories() > 0) {
				printCategories(cat);
			}
		}
	}

	@Test
	public void testCategories() throws IOException, UnknownErrorException, InvalidParameterTermException, ServerErrorException, ResourceNotAllowedException, VersionNotAvailableException {
		Category categories = client.getCategories(ParameterTerms.POIS);
		printCategories(categories);
	}
	
	@Test
	public void testPoiWithId() throws InvalidParameterException, IOException, UnknownErrorException, ServerErrorException, ResourceNotAllowedException, VersionNotAvailableException, InvalidValueException {
		ParameterList params = new ParameterList();
		params.add(new Parameter(ParameterTerms.CATEGORY, "some-category"));
		params.add(new Parameter(ParameterTerms.TAG, "some-tag"));
		params.add(new Parameter(ParameterTerms.SHOW, "0,19"));
		ListPointOfInterest poiList = client.getPois(params);
		List<PointOfInterest> pois = poiList.getPois();
		for(PointOfInterest poi : pois) {
			poi = client.getPoi(poi.getBase(), poi.getId());
			System.out.println(poi.getBase() + "" + poi.getId());
		}
	}
	
	@Test
	public void testAvailableLanguages() throws InvalidParameterException, IOException, ResourceNotAllowedException, UnknownErrorException, ServerErrorException, VersionNotAvailableException, InvalidValueException {
		ParameterList params = new ParameterList();
		params.add(new Parameter(ParameterTerms.CATEGORY, "some-category"));
		params.add(new Parameter(ParameterTerms.TAG, "some-tag"));
		params.add(new Parameter(ParameterTerms.SHOW, "0,19"));
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
	    
	    DataContent content = null;
	    
		ParameterList params = new ParameterList();
		params.add(new Parameter(ParameterTerms.CATEGORY, "some-category"));
		params.add(new Parameter(ParameterTerms.TAG, "some-tag"));
		params.add(new Parameter(ParameterTerms.SHOW, "0,19"));
		ListEvent eventList = client.getEvents(params);
		List<Event> events = eventList.getEvents();
		
		// define the default language to be used, in case the wanted language
	    // does not exist. The default language - if this method is not called - is en_GB.
	    DataReader.setDefaultLocale(new Locale("en","GB"));
	 
	    // get the default locale
	    Locale locale = new Locale("pt", "PT");
		
		for(Event event : events) {
			content = DataReader.getLabel(event, Term.LABEL_TERM_PRIMARY, locale);
	        label = content.getContent();
	         
	        // get a description in PT language
	        content = DataReader.getDescription(event, locale);
	        description = content.getContent();
	         
	        // get a thumbnail (URI or base-64)
	        List<ImageContent> img = DataReader.getThumbnails(event);
	        ImageContent imgContent = null;
	        if(img.size() > 0) {
	            imgContent = img.get(0);
	            thumbnail = imgContent.getContent();
	        }
	         
	        // get an image (always a URI)
	        List<ImageContent> imgUri = DataReader.getImagesUri(event);
	        if(imgUri.size() > 0)
	            image = imgUri.get(0).getContent();
	                 
	        // print the values
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
