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
package citysdk.tourism.client.requests.uri;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simplified implementation of the URI Template (<a target="_blank"
 * href="http://tools.ietf.org/html/rfc6570">RFC6570</a>) used to build the
 * server URIs.
 * 
 * @author Pedro Cruz
 * 
 */
public class UriTemplate {
	private static final Pattern TEMPLATE_REGEX = Pattern
			.compile("\\{[^{}]+\\}");
	private static final Pattern VAR_REGEX = Pattern.compile("((?:\\w+))");
	private static final char CHAR_EXPLODE = '*';
	private static final char CHAR_MODIFIER = ':';

	private String template;
	private Map<String, Object> values;
	private static UriTemplate uriTemplate = null;

	private UriTemplate() {
	}

	private UriTemplate(String template) {
		this.template = template;
		this.values = new HashMap<String, Object>();
	}

	/**
	 * Generate a UriTemplate with a given template form.
	 * 
	 * @param template
	 *            the wanted template
	 * @return {@link UriTemplate}
	 */
	public static UriTemplate fromTemplate(String template) {
		if(uriTemplate == null)
			uriTemplate = new UriTemplate(template);
		else
			uriTemplate.template = template;
		
		return uriTemplate;
	}

	/**
	 * Set the values of each template
	 * 
	 * @param name
	 *            the name of the template
	 * @param value
	 *            the value of the template. Supported objects: simple objects
	 *            that implement toString() method, Collections and Maps.
	 * @return a {@link UriTemplate}
	 */
	public UriTemplate set(String name, Object value) {
		if (!values.containsKey(name))
			values.put(name, value);

		return this;
	}

	/**
	 * Checks whether the URI contains the given name in the templated area.
	 * This method is case sensitive.
	 * 
	 * @param name
	 *            the name to find
	 * @return <code>true</code> if the templated area contains the name,
	 *         <code>false</code> otherwise
	 */
	public boolean hasParameter(String name) {
		Matcher templateMatcher = TEMPLATE_REGEX.matcher(template);
		while (templateMatcher.find()) {
			String template = templateMatcher.group();
			Matcher varMatcher = VAR_REGEX.matcher(template);
			while (varMatcher.find()) {
				String value = varMatcher.group();
				if (name.equals(value)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Builds the desired URI. This method resets previously set values.
	 * 
	 * @return a String containing the expanded URI Template into a URI
	 */
	public String build() {
		String uri = template;

		Matcher templateMatcher = TEMPLATE_REGEX.matcher(template);
		if (!templateMatcher.find())
			return uri;

		templateMatcher.reset();
		while (templateMatcher.find()) {
			String template = templateMatcher.group();
			Operator op = Operator.fromOpCode(template.substring(1, 2));

			String parameters = op.getOperator();
			Matcher varMatcher = VAR_REGEX.matcher(template);
			while (varMatcher.find()) {
				String value = varMatcher.group();
				if (template.charAt(varMatcher.end()) == CHAR_EXPLODE) {
					parameters += explode(value, op);
				} else if (template.charAt(varMatcher.end()) == CHAR_MODIFIER) {
					varMatcher.find();
					parameters += modify(value, varMatcher.group(), op);
				} else {
					parameters += expand(value, op);
				}
			}

			if (op.hasSeparator() && parameters.endsWith(op.getSeparator()))
				parameters = parameters.substring(0, parameters.length() - 1);

			uri = uri.replace(template, parameters);
		}

		values.clear();
		return uri;
	}

	/*
	 * Simple expansion: {var}
	 */
	private String expand(String value, Operator op) {
		String parameters = "";
		if (values.containsKey(value)) {
			Object obj = values.get(value);
			if (obj instanceof Collection) {
				return expandCollection(value, op, (Collection<?>) obj);
			} else if (obj instanceof Map) {
				return expandMap(value, op, (Map<?, ?>) obj);
			} else {
				return expandSimple(value, op, obj);
			}
		}

		return parameters;
	}

	/*
	 * Variable modification: {var:40}
	 */
	private String modify(String value, String size, Operator op) {
		String parameters = "";
		if (values.containsKey(value)) {
			String v = encode(((String) values.get(value)).substring(0,
					Integer.parseInt(size)));
			if (op.isNamed())
				parameters += value + "=" + v;
			else
				parameters += v;

			parameters += op.getSeparator();
		}

		return parameters;
	}

	/*
	 * Variable explosion: {var*}
	 */
	private String explode(String value, Operator op) {
		String parameters = "";
		if (values.containsKey(value)) {
			Object obj = values.get(value);
			if (obj instanceof Collection) {
				return explodeCollection(value, op, (Collection<?>) obj);
			} else if (obj instanceof Map) {
				return explodeMap(op, (Map<?, ?>) obj);
			} else {
				return expandSimple(value, op, obj);
			}
		}

		return parameters;
	}

	/*
	 * Replace the {var} into its string value
	 */
	private String expandSimple(String value, Operator op, Object obj) {
		String parameters = "";
		obj = encode(obj);
		if (op.isNamed())
			parameters += value + "=" + obj;
		else
			parameters += obj.toString();

		parameters += op.getSeparator();
		return parameters;
	}

	/*
	 * Replace the {var*} into its mapping
	 */
	private String explodeMap(Operator op, Map<?, ?> map) {
		String parameters = "";
		Set<?> keys = map.keySet();
		Iterator<?> it = keys.iterator();
		String separator = op.getSeparator();

		while (it.hasNext()) {
			String name = it.next().toString();
			Object ob = map.get(name);
			if (op.isNamed()) {
				parameters += name.toString() + "=" + encode(ob.toString()) + separator;
			} else {
				parameters += name.toString() + op.getSeparator()
						+ encode(ob.toString()) + separator;
			}
		}

		parameters = parameters.substring(0, parameters.length() - 1);
		parameters += op.getSeparator();
		return parameters;
	}

	/*
	 * Replace the {var} into its mapping
	 */
	private String expandMap(String value, Operator op, Map<?, ?> map) {
		String parameters = "";
		Set<?> keys = map.keySet();
		Iterator<?> it = keys.iterator();
		String separator = ",";
		if (op.isNamed())
			parameters += value + "=";

		while (it.hasNext()) {
			String name = it.next().toString();
			Object ob = map.get(name);
			parameters += name.toString() + "," + encode(ob.toString()) + separator;
		}

		parameters = parameters.substring(0, parameters.length() - 1);
		parameters += op.getSeparator();
		return parameters;
	}

	/*
	 * Replace {var*} into its collection
	 */
	private String explodeCollection(String value, Operator op, Collection<?> collection) {
		String parameters = "";
		Iterator<?> it = collection.iterator();
		String separator = op.getSeparator();

		while (it.hasNext()) {
			Object ob = it.next();
			if (op.isNamed()) {
				parameters += value + "=" + encode(ob.toString()) + separator;
			} else {
				parameters += encode(ob.toString()) + separator;
			}
		}

		parameters = parameters.substring(0, parameters.length() - 1);
		parameters += op.getSeparator();
		return parameters;
	}

	/*
	 * Replace the {var} into its collection
	 */
	private String expandCollection(String value, Operator op, Collection<?> collection) {
		String parameters = "";
		Iterator<?> it = collection.iterator();
		String separator = ",";
		if (op.isNamed())
			parameters += value + "=";

		while (it.hasNext()) {
			Object ob = it.next();
			parameters += encode(ob.toString()) + separator;
		}

		parameters = parameters.substring(0, parameters.length() - 1);
		parameters += op.getSeparator();
		return parameters;
	}
	
	private static String encode(Object input) {
        try {
			return URLEncoder.encode(input.toString(), "utf-8").replace("+", "%20");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        
        return input.toString();
    }
}
