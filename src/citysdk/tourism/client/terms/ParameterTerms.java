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

import citysdk.tourism.client.requests.Parameter;

/**
 * The available terms for used for {@link Parameter}. Such parameters are also valid
 * to use for {@link citysdk.tourism.client.poi.single.HypermediaLink}.
 * 
 * @author Pedro Cruz
 */
public enum ParameterTerms {
	/**
	 * Used for category list values
	 */
	POIS("poi", null),
	
	/**
	 * Used for category list values
	 */
	EVENTS("event", null),
	
	/**
	 * Used for category list values
	 */
	ROUTES("route", null),
	
	/**
	 * Just used to group the search parameters
	 */
	SEARCH_PARAMETERS("", null),
	/**
	 * A poi base
	 */
	BASE("base", SEARCH_PARAMETERS),
	/**
	 * A poi id
	 */
	ID("id", SEARCH_PARAMETERS),
	/**
	 * The search parameter using categories
	 */
	CATEGORY("category", SEARCH_PARAMETERS),
	/**
	 * The search parameter using tags
	 */
	TAG("tag", SEARCH_PARAMETERS),
	/**
	 * The search parameter using complete
	 */
	COMPLETE("complete", SEARCH_PARAMETERS),
	/**
	 * The search parameter using minimal
	 */
	MINIMAL("minimal", SEARCH_PARAMETERS),
	/**
	 * The search parameter using coords
	 */
	COORDS("coords", SEARCH_PARAMETERS),
	/**
	 * The search parameter using code
	 */
	CODE("code", SEARCH_PARAMETERS),
	/**
	 * The search parameter using limit
	 */
	LIMIT("limit", SEARCH_PARAMETERS),
	/**
	 * The search parameter using offset
	 */
	OFFSET("offset", SEARCH_PARAMETERS),
	/**
	 * The search parameter using name
	 */
	NAME("name", SEARCH_PARAMETERS),
	/**
	 * The search parameter using time
	 */
	TIME("time", SEARCH_PARAMETERS),
	/**
	 * The search parameter using relations
	 */
	RELATION("relation", SEARCH_PARAMETERS),
	/**
	 * The search parameter using list
	 */
	LIST("list", SEARCH_PARAMETERS);
	
	
	private final String name;
	private final ParameterTerms parent;
	private final List<String> children = new ArrayList<String>();
	
	private ParameterTerms(String s, ParameterTerms p) {
        name = s;
        parent = p;
        if (this.parent != null) {
            this.parent.addChild(this);
        }
    }
	
	private void addChild(ParameterTerms parameterTerms) {
		children.add(parameterTerms.getTerm());
	}
	
	public List<String> getChildren() {
		return children;
	}
	
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public boolean equalsTerm(String term){
        return (term == null) ? false : name.equals(term);
    }

    public String getTerm(){
       return name;
    }
}
