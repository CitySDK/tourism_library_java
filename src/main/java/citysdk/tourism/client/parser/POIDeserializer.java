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

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;

import citysdk.tourism.client.poi.Deserializable;
import citysdk.tourism.client.poi.base.Geometry;
import citysdk.tourism.client.poi.base.Line;
import citysdk.tourism.client.poi.base.Location;
import citysdk.tourism.client.poi.base.POIBaseType;
import citysdk.tourism.client.poi.base.POITermType;
import citysdk.tourism.client.poi.base.Point;
import citysdk.tourism.client.poi.base.Polygon;
import citysdk.tourism.client.poi.base.Relationship;
import citysdk.tourism.client.poi.lists.ListEvent;
import citysdk.tourism.client.poi.lists.ListPOIS;
import citysdk.tourism.client.poi.lists.ListPointOfInterest;
import citysdk.tourism.client.poi.lists.ListRoute;
import citysdk.tourism.client.poi.lists.ListTag;
import citysdk.tourism.client.poi.lists.Resources;
import citysdk.tourism.client.poi.single.Category;
import citysdk.tourism.client.poi.single.Event;
import citysdk.tourism.client.poi.single.Hypermedia;
import citysdk.tourism.client.poi.single.POI;
import citysdk.tourism.client.poi.single.PointOfInterest;
import citysdk.tourism.client.poi.single.HypermediaLink;
import citysdk.tourism.client.poi.single.Route;
import citysdk.tourism.client.poi.single.Tag;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Gson deserializer class for the message formats involved in the CitySDK
 * Tourism WP.
 * 
 * @author Pedro Cruz
 * 
 */
public class POIDeserializer implements JsonDeserializer<Deserializable> {
	private static final String TOURISM = "citysdk-tourism";
	private static final String EVENT = "event";
	private static final String POI = "poi";
	private static final String ROUTES = "routes";
	private static final String POIS = "pois";
	private static final String _LINK = "_links";
	private static final String CATEGORIES = "categories";
	private static final String VERSION = "version";
	private static final String TEMPLATED = "templated";
	private static final String TAGS = "tags";
	private static final String TAG = "tag";
	private static final String ID = "id";
	private static final String VALUE = "value";
	private static final String HREF = "href";
	private static final String TYPE = "type";
	private static final String LANG = "lang";
	private static final String BASE = "base";
	private static final String CREATED = "created";
	private static final String UPDATED = "updated";
	private static final String DELETED = "deleted";
	private static final String AUTHOR = "author";
	private static final String LICENSE = "license";
	private static final String TERM = "term";
	private static final String DESCRIPTION = "description";
	private static final String LABEL = "label";
	private static final String CATEGORY = "category";
	private static final String LOCATION = "location";
	private static final String POINT_P = "Point";
	private static final String POINT = "point";
	private static final String LINE_STRING = "LineString";
	private static final String LINE = "line";
	private static final String SIMPLE_POLYGON = "SimplePolygon";
	private static final String POLYGON = "polygon";
	private static final String POS_LIST = "posList";
	private static final String ADDRESS = "address";
	private static final String RELATIONSHIP = "relationship";
	private static final String TARGET_POI = "targetPOI";
	private static final String TARGET_EVENT = "targetEVENT";
	private static final String TIME = "time";
	private static final String LINK = "link";
	private static final String SCHEME = "scheme";
	private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	protected POIDeserializer() {
	}

	/*
	 * Deserializes the POIBaseType JSON Object Its fields values will depend on
	 * the type of message it it being parsed and it parses the following
	 * fields: - the id as an Integer; - value as a String; - href as a String;
	 * - type as a String; - lang as a String; - base as a String; - created,
	 * updated and deleted as a Date; - author and license as POITermType.
	 */
	private POIBaseType getPOIBaseType(JsonObject json) {
		POIBaseType base = new POIBaseType();
		if (json.has(ID) && !json.get(ID).isJsonNull())
			base.setId(json.get(ID).getAsString());

		if (json.has(VALUE) && !json.get(VALUE).isJsonNull())
			base.setValue(json.get(VALUE).getAsString());

		if (json.has(HREF) && !json.get(HREF).isJsonNull())
			base.setHref(json.get(HREF).getAsString());

		if (json.has(TYPE) && !json.get(TYPE).isJsonNull())
			base.setType(json.get(TYPE).getAsString());

		if (json.has(LANG) && !json.get(LANG).isJsonNull())
			base.setLang(json.get(LANG).getAsString());

		if (json.has(BASE) && !json.get(BASE).isJsonNull())
			base.setBase(json.get(BASE).getAsString());

		try {
			if (json.has(CREATED) && !json.get(CREATED).isJsonNull())
				base.setCreated(new SimpleDateFormat(FORMAT, Locale.ENGLISH)
						.parse(json.get(CREATED).getAsString()));

			if (json.has(UPDATED) && !json.get(UPDATED).isJsonNull())
				base.setCreated(new SimpleDateFormat(FORMAT, Locale.ENGLISH)
						.parse(json.get(UPDATED).getAsString()));

			if (json.has(DELETED) && !json.get(DELETED).isJsonNull())
				base.setCreated(new SimpleDateFormat(FORMAT, Locale.ENGLISH)
						.parse(json.get(DELETED).getAsString()));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (json.has(AUTHOR) && !json.get(AUTHOR).isJsonNull()) {
			base.setAuthor(getPOITermType(json.get(AUTHOR).getAsJsonObject()));
		}

		if (json.has(LICENSE) && !json.get(LICENSE).isJsonNull()) {
			base.setAuthor(getPOITermType(json.get(LICENSE).getAsJsonObject()));
		}

		return base;
	}

	/*
	 * Deserializes the POITermType JSON Object Its fields values will depend on
	 * the type of message it it being parsed and it parses the following
	 * fields: - the term as a String; - the value as a String.
	 */
	private POITermType getPOITermType(JsonObject json) {
		POITermType termType = new POITermType();
		if (json.has(TERM) && !json.get(TERM).isJsonNull())
			termType.setTerm(json.get(TERM).getAsString());

		if (json.has(VALUE) && !json.get(VALUE).isJsonNull())
			termType.setValue(json.get(VALUE).getAsString());

		copyTo(termType, getPOIBaseType(json));
		return termType;
	}

	/*
	 * Copies the content of the base type to the term type.
	 */
	private void copyTo(POIBaseType to, POIBaseType from) {
		to.setBase(from.getBase());
		to.setCreated(from.getCreated());
		to.setDeleted(from.getDeleted());
		to.setHref(from.getHref());
		to.setAuthor(from.getAuthor());
		to.setId(from.getId());
		to.setLang(from.getLang());
		to.setLicense(from.getLicense());
		to.setType(from.getType());
		to.setUpdated(from.getUpdated());
		to.setValue(from.getValue());
	}

	/*
	 * Deserializes a QueryLink JSON Object. It will mainly set the primary
	 * fields of the mentioned POI Object: base, id and lang.
	 */
	private HypermediaLink getResource(JsonObject ob) {
		HypermediaLink hypermediaLink = new HypermediaLink();
		hypermediaLink.setVersion(ob.get(VERSION).getAsString());
		
		if(ob.has(_LINK)) {
			JsonObject map = ob.getAsJsonObject(_LINK);
			Set<Entry<String, JsonElement>> keySet = map.entrySet();
			for(Entry<String, JsonElement> entry : keySet) {
				JsonElement element = entry.getValue();
				JsonObject value = element.getAsJsonObject();
				
				Hypermedia link = new Hypermedia();
				link.setHref(value.get(HREF).getAsString());
				link.setTemplated(value.get(TEMPLATED).getAsBoolean());
				
				hypermediaLink.addHypermedia(entry.getKey(), link);
			}
		}
		
		return hypermediaLink;
	}

	/*
	 * Deserializes the JSON Object containing the a description of the various
	 * labels unto the POI Object (either POI, Event or Route). It will mainly
	 * set the primary fields of the mentioned POI Object: base, id and lang.
	 */
	private void getLabels(POI poi, JsonObject jObject) {
		JsonArray jArray = jObject.getAsJsonArray(LABEL);
		for (int i = 0; i < jArray.size(); i++) {
			JsonElement e = jArray.get(i);
			JsonObject o = e.getAsJsonObject();
			poi.addLabel(getPOITermType(o));
		}
	}

	/*
	 * Deserializes the JSON Object containing the a description of the various
	 * descriptions (POIBaseType) unto the POI Object (either POI, Event or
	 * Route).
	 */
	private void getDescription(POI poi, JsonObject jObject) {
		JsonArray jArray = jObject.getAsJsonArray(DESCRIPTION);
		for (int i = 0; i < jArray.size(); i++) {
			JsonElement e = jArray.get(i);
			JsonObject o = e.getAsJsonObject();
			poi.addDescription(getPOIBaseType(o));
		}
	}

	/*
	 * Deserializes the JSON Object containing the a description of the various
	 * categories (POITermType) unto the POI Object (either POI, Event or
	 * Route).
	 */
	private void getCategories(POI poi, JsonObject jObject) {
		JsonArray jArray = jObject.getAsJsonArray(CATEGORY);
		for (int i = 0; i < jArray.size(); i++) {
			JsonElement e = jArray.get(i);
			JsonObject o = e.getAsJsonObject();
			poi.addCategory(getPOITermType(o));
		}
	}

	/*
	 * Deserializes the JSON Object containing the geometries described as
	 * single Points unto the POI Object (either POI, Event or Route).
	 */
	private void getPoints(Location location, JsonArray array) {
		for (int i = 0; i < array.size(); i++) {
			JsonObject ob = array.get(i).getAsJsonObject();

			Geometry g = new Geometry();
			g.setPosList(ob.get(POINT_P).getAsJsonObject().get(POS_LIST)
					.getAsString());

			Point point = new Point();
			point.setTerm(ob.get(TERM).getAsString());
			point.setPoint(g);

			location.addPoint(point);
		}
	}

	/*
	 * Deserializes the JSON Object containing the geometries described as Lines
	 * unto the POI Object (either POI, Event or Route).
	 */
	private void getLine(Location location, JsonArray array) {
		for (int i = 0; i < array.size(); i++) {
			JsonObject ob = array.get(i).getAsJsonObject();

			Geometry g = new Geometry();
			g.setPosList(ob.get(LINE_STRING).getAsJsonObject().get(POS_LIST)
					.getAsString());

			Line line = new Line();
			line.setTerm(ob.get(TERM).getAsString());
			line.setLineString(g);

			location.addLine(line);
		}
	}

	/*
	 * Deserializes the JSON Object containing the geometries described as
	 * single Polygons unto the POI Object (either POI, Event or Route).
	 */
	private void getSimplePolygon(Location location, JsonArray array) {
		for (int i = 0; i < array.size(); i++) {
			JsonObject ob = array.get(i).getAsJsonObject();

			Geometry g = new Geometry();
			g.setPosList(ob.get(SIMPLE_POLYGON).getAsJsonObject().get(POS_LIST)
					.getAsString());

			Polygon polygon = new Polygon();
			polygon.setTerm(ob.get(TERM).getAsString());
			polygon.setSimplePolygon(g);

			location.addPolygon(polygon);
		}
	}

	/*
	 * Deserializes the JSON Object containing the Relationships of the POI unto
	 * the POI Object (either POI, Event or Route).
	 */
	private void getRelationships(Location location, JsonArray array) {
		for (int i = 0; i < array.size(); i++) {
			JsonObject ob = array.get(i).getAsJsonObject();

			Relationship r = new Relationship();
			r.setTerm(ob.get(TERM).getAsString());
			r.setBase(ob.get(BASE).getAsString());
			if (ob.has(TARGET_POI))
				r.setTargetPOI(ob.get(TARGET_POI).getAsString());

			if (ob.has(TARGET_EVENT))
				r.setTargetEvent(ob.get(TARGET_EVENT).getAsString());

			location.addRelationship(r);
		}
	}

	/*
	 * Deserializes the JSON Object containing the Location unto the POI Object
	 * (either POI, Event or Route).
	 */
	private void getLocation(POI poi, JsonObject jObject) {
		JsonObject json = jObject.getAsJsonObject(LOCATION);
		JsonArray array;

		Location location = new Location();
		if (json.has(POINT)) {
			array = json.getAsJsonArray(POINT);
			getPoints(location, array);
		}

		if (json.has(LINE)) {
			array = json.getAsJsonArray(LINE);
			getLine(location, array);
		}

		if (json.has(POLYGON)) {
			array = json.getAsJsonArray(POLYGON);
			getSimplePolygon(location, array);
		}

		if (json.has(ADDRESS))
			location.setAddress(getPOIBaseType(json.getAsJsonObject(ADDRESS)));

		if (json.has(RELATIONSHIP)) {
			array = json.getAsJsonArray(RELATIONSHIP);
			getRelationships(location, array);
		}

		poi.setLocation(location);
	}

	/*
	 * Deserializes the JSON Object containing the a description of the various
	 * times (POITermType) unto the POI Object (either POI, Event or Route).
	 */
	private void getTime(POI poi, JsonObject jObject) {
		JsonArray jArray = jObject.getAsJsonArray(TIME);
		for (int i = 0; i < jArray.size(); i++) {
			JsonObject ob = jArray.get(i).getAsJsonObject();
			POITermType term = getPOITermType(ob);

			if (ob.has(SCHEME))
				term.setScheme(ob.get(SCHEME).getAsString());

			poi.addTime(term);
		}
	}

	/*
	 * Deserializes the JSON Object containing the a description of the various
	 * links (POITermType) unto the POI Object (either POI, Event or Route).
	 */
	private void getLinks(POI poi, JsonObject jObject) {
		JsonArray jArray = jObject.getAsJsonArray(LINK);
		for (int i = 0; i < jArray.size(); i++) {
			JsonObject ob = jArray.get(i).getAsJsonObject();
			POITermType term = getPOITermType(ob);
			poi.addLink(term);
		}
	}

	/*
	 * Deserializes the JSON Object as a POI, Event or Route
	 */
	private void getSinglePOI(POI poi, JsonObject jObject) {
		copyTo(poi, getPOIBaseType(jObject));
		if (jObject.has(LABEL))
			getLabels(poi, jObject);

		if (jObject.has(DESCRIPTION))
			getDescription(poi, jObject);

		if (jObject.has(CATEGORY))
			getCategories(poi, jObject);

		if (jObject.has(LOCATION))
			getLocation(poi, jObject);

		if (jObject.has(TIME))
			getTime(poi, jObject);

		if (jObject.has(LINK))
			getLinks(poi, jObject);
	}

	/*
	 * Deserializes the JSON Object as a List of POIs
	 */
	private void deserializeListPois(ListPointOfInterest list, JsonObject jObject) {
		JsonElement jElement = jObject.get(POI);
		JsonArray jArray = jElement.getAsJsonArray();
		for (int i = 0; i < jArray.size(); i++) {
			PointOfInterest poi = new PointOfInterest();
			getSinglePOI(poi, jArray.get(i).getAsJsonObject());
			list.addPoi(poi);
		}
	}
	
	/*
	 * Deserializes the JSON Object as a List of Events
	 */
	private void deserializeListEvents(ListEvent list, JsonObject jObject) {
		JsonElement jElement = jObject.get(EVENT);
		JsonArray jArray = jElement.getAsJsonArray();
		for (int i = 0; i < jArray.size(); i++) {
			Event event = new Event();
			getSinglePOI(event, jArray.get(i).getAsJsonObject());
			list.addEvent(event);
		}
	}
	
	/*
	 * Deserializes the JSON Object as a List of Routes
	 */
	private void deserializeListRoutes(ListRoute list, JsonObject jObject) {
		JsonElement jElement = jObject.get(ROUTES);
		JsonArray jArray = jElement.getAsJsonArray();
		for (int i = 0; i < jArray.size(); i++) {
			Route route = new Route();
			getSinglePOI(route, jArray.get(i).getAsJsonObject());
			list.addRoute(route);
		}
	}

	/*
	 * Deserializes the JSON Object as a Route
	 */
	private void deserializePoiBased(Deserializable poi, JsonObject json) {
		getSinglePOI((POI)poi, json);
		
		// for routes
		if(json.has(POIS)) {
			JsonArray jArray = json.get(POIS).getAsJsonArray();
			ListPointOfInterest list = ((Route) poi).getListPoi();
			for (int i = 0; i < jArray.size(); i++) {
				PointOfInterest rPoi = new PointOfInterest();
				getSinglePOI(rPoi, jArray.get(i).getAsJsonObject());
				list.addPoi(rPoi);
			}
		}
	}

	/*
	 * Deserializes the JSON Object as a Query Link
	 */
	private void deserializeResources(Resources resources, JsonObject jObject) {
		JsonElement jElement = jObject.get(TOURISM);
		JsonArray jArray = jElement.getAsJsonArray();
		for (int i = 0; i < jArray.size(); i++) {
			JsonObject ob = jArray.get(i).getAsJsonObject();
			HypermediaLink resource = getResource(ob);
			resources.addResource(resource);
		}
	}

	/*
	 * Deserializes the JSON Object as Categories
	 */
	private POI deserializeCategories(Category list, JsonElement elem) {
		JsonObject json = null;
		JsonArray array;
		
		if(elem.isJsonObject()
				&& elem.getAsJsonObject().has(CATEGORIES)) {
			array = elem.getAsJsonObject().get(CATEGORIES).getAsJsonArray();
			for(int i = 0; i < array.size(); i++) {
				Category cat = new Category();
				json = array.get(i).getAsJsonObject();
				getSinglePOI(cat, json);
				if(json.has(CATEGORIES)){
					Category subs = (Category) deserializeCategories(new Category(), json);
					for(int j = 0; j < subs.getNumCategories(); j++)
						cat.addCategory(subs.getCategory(j));
				}
				
				list.addCategory(cat);
			}
		}

		return list;
	}

	/*
	 * Gets a single tag
	 */
	private void deserializeTag(POI tag, JsonArray array) {
		POI poi;
		for (int i = 0; i < array.size(); i++) {
			poi = new Tag();
			getSinglePOI(poi, array.get(i).getAsJsonObject());
			((Tag) tag).addTag((Tag) poi);
		}
	}

	/*
	 * Deserializes the Json Object as Tags
	 */
	private void deserializeTags(ListTag list, JsonObject jObject) {
		JsonObject json = null;
		JsonArray array;
		POI tag;

		array = jObject.get(TAGS).getAsJsonArray();
		for (int i = 0; i < array.size(); i++) {
			tag = new Tag();
			json = array.get(i).getAsJsonObject();
			deserializeTag(tag, json.get(TAG).getAsJsonArray());
			list.add((Tag) tag);
		}
	}
	
	/*
	 * Deserializes the Json Object as List of Lists
	 */
	private void deserializeListOfList(ListPOIS list, JsonObject jObject) {
		if(jObject.has(POI)) {
			ListPointOfInterest pois = new ListPointOfInterest();
			deserializeListPois(pois, jObject);
			list.setListPoi(pois);
		}
		
		if(jObject.has(EVENT)) {
			ListEvent events = new ListEvent();
			deserializeListEvents(events, jObject);
			list.setListEvent(events);
		}
		
		if(jObject.has(ROUTES)) {
			ListRoute routes = new ListRoute();
			deserializeListRoutes(routes, jObject);
			list.setListRoute(routes);
		}
	}
	
	/*
	 * Infers the class that should be instantiated
	 */
	@SuppressWarnings({ "unchecked" })
	private Object inferType(Type typeOfT) {
		Object poi = null;
		try {
			String className = typeOfT.toString().split(" ")[1];
			Class<? extends POI> classDefinition = 
					(Class<? extends citysdk.tourism.client.poi.single.POI>) Class.forName(className);
			poi = classDefinition.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return poi;
	}

	/**
	 * Gson deserializer for the CitySDK Tourism POI Classes
	 */
	@Override
	public Deserializable deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject jObject = json.getAsJsonObject();
		Deserializable deserializable = (Deserializable) inferType(typeOfT);
		
		if (deserializable.getClass().equals(ListPOIS.class)) {
			deserializeListOfList((ListPOIS) deserializable, jObject);
		} else if (jObject.has(POI)) {
			deserializeListPois((ListPointOfInterest) deserializable, jObject);
		} else if (jObject.has(EVENT)) {
			deserializeListEvents((ListEvent) deserializable, jObject);
		} else if (jObject.has(ROUTES)) {
			deserializeListRoutes((ListRoute) deserializable, jObject);
		} else if (jObject.has(TOURISM)) {
			deserializeResources((Resources) deserializable, jObject);
		} else if (jObject.has(CATEGORIES)) {
			deserializeCategories((Category) deserializable,jObject);
		} else if (jObject.has(TAGS)) {
			deserializeTags((ListTag) deserializable, jObject);
		} else if(deserializable != null) {
			deserializePoiBased(deserializable, jObject);
		}
		
		return deserializable;
	}
}
