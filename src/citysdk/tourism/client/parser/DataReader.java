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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import citysdk.tourism.client.parser.data.DataContent;
import citysdk.tourism.client.parser.data.GeometryContent;
import citysdk.tourism.client.parser.data.ImageContent;
import citysdk.tourism.client.parser.data.LineContent;
import citysdk.tourism.client.parser.data.LocationContent;
import citysdk.tourism.client.parser.data.PointContent;
import citysdk.tourism.client.parser.data.PolygonContent;
import citysdk.tourism.client.poi.base.Line;
import citysdk.tourism.client.poi.base.Location;
import citysdk.tourism.client.poi.base.POIBaseType;
import citysdk.tourism.client.poi.base.POITermType;
import citysdk.tourism.client.poi.base.Point;
import citysdk.tourism.client.poi.base.Polygon;
import citysdk.tourism.client.poi.base.Relationship;
import citysdk.tourism.client.poi.lists.ListTag;
import citysdk.tourism.client.poi.single.POI;
import citysdk.tourism.client.poi.single.Tag;
import citysdk.tourism.client.terms.Term;

/**
 * Used as an aid to get data from a
 * {@link citysdk.tourism.client.poi.single.POI}-based object. It abstract the
 * parsing of data and gets the information needed specified by the application
 * using a given set of terms or languages. In case a given language is not
 * found, it defaults to en_GB.
 * 
 * @author Pedro Cruz
 * 
 */
public class DataReader {
	private static final String PRICE_TAG = "X-citysdk/price";
	private static final String WAITING_TAG = "X-citysdk/waiting-time";
	private static final String OCCUPATION_TAG = "X-citysdk/occupation";
	private static final String CALENDAR_TAG = "text/calendar";
	private static final String IMAGE_TAG = "image/";
	private static Locale defaultLang = new Locale("en", "GB");

	/**
	 * Sets the default locale. If locale is null, it defaults to en_GB.
	 * 
	 * @param locale
	 *            the default locale.
	 */
	public static void setDefaultLocale(Locale locale) {
		if (locale == null)
			return;

		defaultLang = locale;
	}

	/**
	 * Gets a mapping of all the available languages of a given field in the
	 * {@link citysdk.tourism.client.poi.single.POI} object.
	 * 
	 * @param poi
	 *            the object to get the data.
	 * @param field
	 *            the wanted field
	 * @return a mapping of language codes and the respective locales.
	 */
	public static Map<String, Locale> getAvailableLangs(POI poi, Field field) {
		if (poi == null)
			return null;

		Map<String, Locale> languages = new HashMap<String, Locale>();
		List<? extends POIBaseType> fields = invokeMethod(poi, field);
		if (field != null) {
			for (POIBaseType base : fields) {
				if (!base.getLang().equals("")) {
					Locale locale = parseLocale(base.getLang());
					languages.put(locale.getLanguage(), locale);
				}
			}
		}

		return languages;
	}

	/*
	 * Invokes the respective method from the field
	 */
	@SuppressWarnings("unchecked")
	private static List<? extends POIBaseType> invokeMethod(POI poi, Field field) {
		Class<?>[] params = { POI.class };
		try {
			Method method = DataReader.class.getDeclaredMethod(
					field.getField(), params);
			return (List<? extends POIBaseType>) method.invoke(null,
					new Object[] { poi });
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * Gets the labels field of a POI
	 */
	@SuppressWarnings("unused")
	private static List<? extends POIBaseType> getLabels(POI poi) {
		return poi.getLabel();
	}

	/*
	 * Gets the descriptions field of a POI
	 */
	@SuppressWarnings("unused")
	private static List<? extends POIBaseType> getDescriptions(POI poi) {
		return poi.getDescription();
	}

	/*
	 * Parses the locale of a given string
	 */
	private static Locale parseLocale(String locale) {
		String[] parts = locale.replace('-', '_').split("_");
		switch (parts.length) {
		case 3:
			return new Locale(parts[0], parts[1], parts[2]);
		case 2:
			return new Locale(parts[0], parts[1]);
		case 1:
			return new Locale(parts[0]);
		default:
			throw new IllegalArgumentException("Invalid locale: " + locale);
		}
	}

	/**
	 * Gets the label with the given term in a given language.
	 * 
	 * @param poi
	 *            the object to get the data.
	 * @param term
	 *            the term used.
	 * @param lang
	 *            the wanted language.
	 * @return a {@link citysdk.tourism.client.parser.data.DataContent}
	 *         containing the following: the value of the label in the given
	 *         language. If such language is not found it will return in default
	 *         language and if the default language is not present it will
	 *         return a DataContent containing null.
	 */
	public static DataContent getLabel(POI poi, Term term, Locale lang) {
		if (poi == null)
			return new DataContent(null);

		Locale labelLang;
		Locale poiLang = parseLocale(poi.getLang());

		String defaultValue = null;
		List<POITermType> labels = poi.getLabel();
		for (POITermType label : labels) {
			if (label.getLang().equals(""))
				labelLang = poiLang;
			else
				labelLang = parseLocale(label.getLang());

			if (label.getTerm().equals(term.getTerm())
					&& labelLang.getLanguage()
							.equals(defaultLang.getLanguage())) {
				defaultValue = label.getValue();
			} else if (label.getTerm().equals(term.getTerm())
					&& labelLang.getLanguage().equals(lang.getLanguage())) {
				return new DataContent(label.getValue());
			}
		}

		return new DataContent(defaultValue);
	}

	/**
	 * Gets the description in a given language.
	 * 
	 * @param poi
	 *            the object to get the data.
	 * @param lang
	 *            the wanted language.
	 * @return a {@link citysdk.tourism.client.parser.data.DataContent}
	 *         containing the following: the value of the description in the
	 *         desired language or in the default language if none found or
	 *         null.
	 */
	public static DataContent getDescription(POI poi, Locale lang) {
		if (poi == null)
			return new DataContent(null);

		Locale descriptionLang;
		Locale poiLang = parseLocale(poi.getLang());

		String defaultValue = null;
		List<POIBaseType> descriptions = poi.getDescription();
		for (POIBaseType description : descriptions) {
			if (description.getLang().equals(""))
				descriptionLang = poiLang;
			else
				descriptionLang = parseLocale(description.getLang());

			if (descriptionLang.getLanguage().equals(defaultLang.getLanguage())) {
				defaultValue = description.getValue();
			} else if (descriptionLang.getLanguage().equals(lang.getLanguage())) {
				return new DataContent(description.getValue());
			}
		}

		return new DataContent(defaultValue);
	}

	/**
	 * Gets the price in a given language.
	 * 
	 * @param poi
	 *            the object to get the data.
	 * @param lang
	 *            the wanted language.
	 * @return a {@link citysdk.tourism.client.parser.data.DataContent}
	 *         containing the following: the value of the price in the desired
	 *         language or in the default language if it was not found or null
	 *         if the default language is not present.
	 */
	public static DataContent getPrice(POI poi, Locale lang) {
		return getValueWithTag(poi, lang, PRICE_TAG);
	}

	/**
	 * Gets the waiting time in a given language.
	 * 
	 * @param poi
	 *            the object to get the data.
	 * @return a {@link citysdk.tourism.client.parser.data.DataContent}
	 *         containing the waiting time (in seconds) or null.
	 */
	public static DataContent getWaitingTime(POI poi) {
		return getValueWithTag(poi, null, WAITING_TAG);
	}

	/**
	 * Gets the occupation in a given language.
	 * 
	 * @param poi
	 *            the object to get the data.
	 * @return a {@link citysdk.tourism.client.parser.data.DataContent}
	 *         containing the occupation value (0 to 100) or null.
	 */
	public static DataContent getOccupation(POI poi) {
		return getValueWithTag(poi, null, OCCUPATION_TAG);
	}

	private static DataContent getValueWithTag(POI poi, Locale lang, String tag) {
		if (poi == null)
			return new DataContent(null);

		Locale descriptionLang;
		Locale poiLang = parseLocale(poi.getLang());

		String defaultValue = null;
		List<POIBaseType> descriptions = poi.getDescription();
		for (POIBaseType description : descriptions) {
			if (description.getLang().equals(""))
				descriptionLang = poiLang;
			else
				descriptionLang = parseLocale(description.getLang());

			if (description.getType().equals(tag)) {
				if (lang != null
						&& descriptionLang.getLanguage().equals(
								defaultLang.getLanguage()))
					defaultValue = description.getValue();
				else if (lang == null)
					defaultValue = description.getValue();

			} else if (description.getType().equals(tag)) {
				if (lang != null
						&& descriptionLang.getLanguage().equals(
								lang.getLanguage()))
					return new DataContent(description.getValue());
				else if (lang == null)
					return new DataContent(description.getValue());
			}
		}

		return new DataContent(defaultValue);
	}

	/**
	 * Gets all the thumbnails in 64-base bytecode or URI
	 * 
	 * @param poi
	 *            the object to get the data.
	 * @return a list of {@link citysdk.tourism.client.parser.data.ImageContent}
	 *         where each element contains the following: the bytecode (base-64)
	 *         or URI of the thumbnail, or an empty list if none found.
	 */
	public static List<ImageContent> getThumbnails(POI poi) {
		List<ImageContent> thumbnails = new ArrayList<ImageContent>();
		if (poi == null)
			return thumbnails;

		ImageContent content = null;
		List<POITermType> links = poi.getLink();
		for (POITermType link : links) {
			if (link.getTerm().equals(Term.LINK_TERM_ICON.getTerm())) {
				if (!link.getHref().equals("")) {
					content = new ImageContent(link.getHref());
					content.isImgUri(true);
					thumbnails.add(content);
				} else if (!link.getValue().equals("")) {
					content = new ImageContent(link.getValue());
					content.isImgByteCode(true);
					thumbnails.add(content);
				}
			}
		}

		return thumbnails;
	}

	/**
	 * Gets a list of points of the POI object with the given term.
	 * 
	 * @param poi
	 *            the object to get the data.
	 * @param term
	 *            the term to be used.
	 * @return a list (of
	 *         {@link citysdk.tourism.client.parser.data.GeometryContent})
	 *         containing all geometries with the given term or an empty list.
	 */
	public static List<GeometryContent> getLocationGeometry(POI poi, Term term) {
		List<GeometryContent> list = new ArrayList<GeometryContent>();
		if (poi == null)
			return list;

		list.addAll(getLocationPoint(poi, term));
		list.addAll(getLocationLine(poi, term));
		list.addAll(getLocationPolygon(poi, term));

		return list;
	}

	/**
	 * Gets a list of points of the POI object with the given term. It only
	 * checks the {@link citysdk.tourism.client.poi.base.Point} geometry.
	 * 
	 * @param poi
	 *            the object to get the data.
	 * @param term
	 *            the term to be used.
	 * @return a list (of
	 *         {@link citysdk.tourism.client.parser.data.PointContent})
	 *         containing all points with the given term or an empty list.
	 */
	public static List<PointContent> getLocationPoint(POI poi, Term term) {
		List<PointContent> list = new ArrayList<PointContent>();
		if (poi == null)
			return list;

		Location location = poi.getLocation();
		PointContent point;
		if (location.hasPoints()) {
			List<Point> points = location.getPoint();

			for (Point p : points) {
				if (p.getTerm().equals(term.getTerm())) {
					String data[] = p.getPoint().getPosList().split(" ");
					point = new PointContent(new DataContent(data[0]),
							new DataContent(data[1]));
					list.add(point);
				}
			}
		}

		return list;
	}

	/**
	 * Gets a list of lines of the POI object with the given term. It only
	 * checks the {@link citysdk.tourism.client.poi.base.Line} geometry.
	 * 
	 * @param poi
	 *            the object to get the data.
	 * @param term
	 *            the term to be used.
	 * @return a list (of {@link citysdk.tourism.client.parser.data.LineContent}
	 *         ) containing all lines with the given term or an empty list.
	 */
	public static List<LineContent> getLocationLine(POI poi, Term term) {
		List<LineContent> list = new ArrayList<LineContent>();
		if (poi == null)
			return list;

		Location location = poi.getLocation();
		LineContent line;
		if (location.hasLines()) {
			List<Line> lines = location.getLine();

			for (Line l : lines) {
				if (l.getTerm().equals(term.getTerm())) {
					String data[] = l.getLineString().getPosList().split(",");
					String point1[] = data[0].split(" ");
					String point2[] = data[1].split(" ");
					line = new LineContent(new LocationContent(new DataContent(
							point1[0]), new DataContent(point1[1])),
							new LocationContent(new DataContent(point2[2]),
									new DataContent(point2[3])));
					list.add(line);
				}
			}
		}

		return list;
	}

	/**
	 * Gets a list of polygons of the POI object with the given term. It only
	 * checks the {@link citysdk.tourism.client.poi.base.Polygon} geometry.
	 * 
	 * @param poi
	 *            the object to get the data.
	 * @param term
	 *            the term to be used.
	 * @return a list (of
	 *         {@link citysdk.tourism.client.parser.data.PolygonContent})
	 *         containing all polygons with the given term or an empty list.
	 */
	public static List<PolygonContent> getLocationPolygon(POI poi, Term term) {
		List<PolygonContent> list = new ArrayList<PolygonContent>();
		if (poi == null)
			return list;

		Location location = poi.getLocation();
		PolygonContent polygon;
		if (location.hasPolygons()) {
			List<Polygon> polygons = location.getPolygon();

			for (Polygon p : polygons) {
				if (p.getTerm().equals(term.getTerm())) {
					polygon = new PolygonContent();
					String data[] = p.getSimplePolygon().getPosList()
							.split(",");
					for (int i = 0; i < data.length; i++) {
						String posList[] = data[i].split(" ");
						polygon.addLocation(new LocationContent(
								new DataContent(posList[0]), new DataContent(
										posList[1])));
					}
					list.add(polygon);
				}
			}
		}

		return list;
	}

	/**
	 * Gets the contacts in vCard format
	 * 
	 * @param poi
	 *            the object to get the data.
	 * @return a String in vCard format containing the information or null if none were found.
	 */
	public static String getContacts(POI poi) {
		if (poi == null)
			return null;

		POIBaseType address = poi.getLocation().getAddress();
		if (address.getValue().equals(""))
			return null;
		else 
			return address.getValue();
	}

	/**
	 * Returns an iCalendar of the given term.
	 * 
	 * @param poi
	 *            the object to get the data.
	 * @param term
	 *            the term to search for.
	 * @return a String in iCalendar format with the given term or null if none were found.
	 */
	public static String getCalendar(POI poi, Term term) {
		if (poi == null)
			return null;

		List<POITermType> list = poi.getTime();
		for (POITermType type : list) {
			if (type.getType().equals(CALENDAR_TAG)
					&& type.getTerm().equals(term.getTerm())) {
				return type.getValue();
			}
		}

		return null;
	}

	/**
	 * Gets all the URI images of the given POI object.
	 * 
	 * @param poi
	 *            the object to get the data.
	 * @return the list of URIs (
	 *         {@link citysdk.tourism.client.parser.data.ImageContent}) of the
	 *         images or an empty list if none were found.
	 */
	public static List<ImageContent> getImagesUri(POI poi) {
		List<ImageContent> images = new ArrayList<ImageContent>();
		if (poi == null)
			return null;

		List<POITermType> links = poi.getLink();
		for (POITermType link : links) {
			if (link.getTerm().equals(Term.LINK_TERM_RELATED.getTerm())
					&& link.getType().contains(IMAGE_TAG)) {
				ImageContent content = new ImageContent(link.getHref());
				content.isImgUri(true);
				images.add(content);
			}
		}

		return images;
	}

	/**
	 * Gets the relationship base with a given term.
	 * 
	 * @param poi
	 *            the object to get the data.
	 * @param term
	 *            the term used.
	 * @return a {@link citysdk.tourism.client.parser.data.DataContent}
	 *         containing the relationship base with the given term or null if
	 *         none was found.
	 */
	public static DataContent getRelationshipBase(POI poi, Term term) {
		if (poi == null)
			return null;

		if (poi.getLocation().hasRelationships()) {
			List<Relationship> list = poi.getLocation().getRelationship();
			for (Relationship relation : list) {
				if (relation.getTerm().equals(term.getTerm()))
					return new DataContent(relation.getBase());
			}
		}

		return new DataContent(null);
	}

	/**
	 * Gets the relationship id with a given term.
	 * 
	 * @param poi
	 *            the object to get the data.
	 * @param term
	 *            the term used.
	 * @return a {@link citysdk.tourism.client.parser.data.DataContent}
	 *         containing the relationship id with the given term or null if
	 *         none was found.
	 */
	public static DataContent getRelationshipId(POI poi, Term term) {
		if (poi == null)
			return new DataContent(null);

		if (poi.getLocation().hasRelationships()) {
			List<Relationship> list = poi.getLocation().getRelationship();
			for (Relationship relation : list) {
				if (relation.getTerm().equals(term.getTerm())) {
					if (relation.hasTargetPOI())
						return new DataContent(relation.getTargetPOI());
					else
						return new DataContent(relation.getTargetEvent());
				}
			}
		}

		return new DataContent(null);
	}

	/**
	 * Returns the link with a given term.
	 * 
	 * @param poi
	 *            the object to get the data.
	 * @param term
	 *            the term used
	 * @return a {@link citysdk.tourism.client.parser.data.DataContent}
	 *         containing a link or null if none found.
	 */
	public static DataContent getLink(POI poi, Term term) {
		if (poi == null)
			return new DataContent(null);

		List<POITermType> links = poi.getLink();
		for (POITermType link : links) {
			if (link.getTerm().equals(term.getTerm())) {
				return new DataContent(link.getHref());
			}
		}
		return new DataContent(null);
	}

	/**
	 * Gets the tags within the list of tags in a given language.
	 * 
	 * @param list
	 *            the list of tags.
	 * @param lang
	 *            the wanted language.
	 * @return a list of tags in the given language, or an empty list if none
	 *         was found.
	 */
	public static List<DataContent> getTags(ListTag list, Locale lang) {
		List<DataContent> tagList = new ArrayList<DataContent>();
		if (list == null)
			return tagList;

		for (int i = 0; i < list.getNumTags(); i++) {
			Tag tag = list.get(i);
			List<Tag> tagValues = tag.getTags();
			for (Tag t : tagValues) {
				if (parseLocale(t.getLang()).getLanguage().equals(
						lang.getLanguage()))
					tagList.add(new DataContent(t.getValue()));
			}
		}

		return tagList;
	}
}
