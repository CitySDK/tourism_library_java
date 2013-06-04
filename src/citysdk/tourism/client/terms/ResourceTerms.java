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
package citysdk.tourism.client.terms;

import java.util.ArrayList;
import java.util.List;

/**
 * Terms used for resources
 * 
 * @author Pedro Cruz
 *
 */
public enum ResourceTerms {
	/**
	 * Just used to group the POI resource parameters
	 */
	RESOURCE_POIS("resource-pois", null),
	/**
	 * The term for the search of a POI
	 */
	FIND_POI("find-poi", RESOURCE_POIS),
	/**
	 * The term for the search of a POI through the use of relations
	 */
	FIND_POI_RELATION("find-poi-relation", RESOURCE_POIS),

	/**
	 * Just used to group the Event resource parameters
	 */
	RESOURCE_EVENTS("resource-events", null),
	/**
	 * The term for the search of an Event
	 */
	FIND_EVENT("find-event", RESOURCE_EVENTS),
	/**
	 * The term for the search of an Event through the use of relations
	 */
	FIND_EVENT_RELATION("find-event-relation", RESOURCE_EVENTS),

	/**
	 * Just used to group the Route resource parameters
	 */
	RESOURCE_ROUTES("resource-routes", null),
	/**
	 * The term for the search of Routes
	 */
	FIND_ROUTE("find-route", RESOURCE_ROUTES),

	/**
	 * The term for the search for the list of categories
	 */
	FIND_CATEGORIES("find-categories", null),
	/**
	 * The term for the search for the list of tags
	 */
	FIND_TAGS("find-tags", null),
	/**
	 * The term for the search for QR or barcodes
	 */
	FIND_CODE("find-code", null);

	private final String name;
	private final ResourceTerms parent;
	private final List<String> children = new ArrayList<String>();

	private ResourceTerms(String s, ResourceTerms r) {
		name = s;
		parent = r;
		if (this.parent != null) {
			this.parent.addChild(this);
		}
	}

	private void addChild(ResourceTerms r) {
		children.add(r.getTerm());
	}

	public List<String> getChildren() {
		return children;
	}

	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public boolean equalsTerm(String term) {
		return (term == null) ? false : name.equals(term);
	}

	public String getTerm() {
		return name;
	}

	public static ResourceTerms fromString(String term) {
		if (term != null) {
			for (ResourceTerms resource : ResourceTerms.values()) {
				if (resource.equalsTerm(term)) {
					return resource;
				}
			}
		}
		throw new IllegalArgumentException("No constant with text " + term
				+ " found");
	}
}
