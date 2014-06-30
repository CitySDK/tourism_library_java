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

import citysdk.tourism.client.parser.DataReader;

/**
 * Terms used within the JSON message. These can be used to read data (with {@link DataReader})
 * so to get the values of given fields within a {@link citysdk.tourism.client.poi.single.POI} based
 * class.
 * 
 * @author Pedro Cruz
 *
 */
public enum Term {
	/**
	 * Author terms
	 */
	AUTHOR_TERM_PRIMARY("primary"),
	AUTHOR_TERM_SECONDARY("secondary"),
	AUTHOR_TERM_CONTRIBUTER("contributer"),
	AUTHOR_TERM_EDITOR("editor"),
	AUTHOR_TERM_PUBLISHER("publisher"),
	
	/**
	 * Label terms
	 */
	LABEL_TERM_PRIMARY("primary"),
	LABEL_TERM_NOTE("note"),
	
	/**
	 * Time terms
	 */
	TIME_TERM_START("start"),
	TIME_TERM_END("end"),
	TIME_TERM_INSTANT("instant"),
	TIME_TERM_OPEN("open"),
	
	/**
	 * Link terms
	 */
	LINK_TERM_SOURCE("source"),			  /**POI source information*/
	LINK_TERM_ALTERNATE("alternate"),	  /**a identical POI. Often used as a permalink*/
	LINK_TERM_CANONICAL("canonical"),	  /**the preferred version of a set of POIs with highly 
										     similar content. For example, there could be many 
										     different perceptions of a neighborhood boundary 
										     POI, but the city's neighborhood map could be the 
										     canonical version of this POI.*/
	LINK_TERM_COPYRIGHT("copyright"),	  /**a copyright statement that applys to the link's 
										     context*/
	LINK_TERM_DESCRIBEDBY("describedby"), /**more information about this POI*/
	LINK_TERM_EDIT("edit"),				  /**a resource that can be used to edit the POI's 
											 context*/
	LINK_TERM_ENCLOSURE("enclosure"),	  /**a related resource that is potentially large 
											 and might require special handling*/
	LINK_TERM_ICON("icon"),
	LINK_TERM_LATEST_VERSION("latest-version"), /**points to a resource containing the latest 
												   version*/
	LINK_TERM_LICENSE("license"),			/**a license for this POI*/
	LINK_TERM_RELATED("related"),			/**a related resource*/
	LINK_TERM_SEARCH("search"),				/**a resource that can be used to search through 
											   the link's context and related resources*/
	LINK_TERM_PARENT("parent"),				/**a parent POI, often the enclosing geographic 
											   entity, or the entity this POI in under the 
											   domain of (such as a field office-corporate 
											   headquarters relationship)*/
	LINK_TERM_CHILD("child"),				/**a child POI, often a geography entity enclosed
	 										   or under the domain of this POI*/
	LINK_TERM_HISTORIC("historic"),			/**links to a POI or other web resource that 
											   describes this place at a previous point in time*/
	LINK_TERM_FUTURE("future"),				/**links to a POI or other web resource that 
											   describes this place at a later point in time*/
	
	/**
	 * Point terms
	 */
	POINT_TERM_CENTER("center"),
	POINT_TERM_NAVIGATION_POINT("navigation point"),
	POINT_TERM_ENTRANCE("entrance"),
	
	/**
	 * Relationship terms	
	 */
	RELATIONSHIP_TERM_EQUALS("equals"),
	RELATIONSHIP_TERM_DISJOINT("disjoint"),
	RELATIONSHIP_TERM_CROSSES("crosses"),
	RELATIONSHIP_TERM_OVERLAPS("overlaps"),
	RELATIONSHIP_TERM_WITHIN("within"),
	RELATIONSHIP_TERM_CONTAINS("contains"),
	RELATIONSHIP_TERM_TOUCHES("touches");
	
	private final String name;
	
	private Term(String s) {
        name = s;
    }
	
	public boolean equalsTerm(Term linkTerm){
        return (linkTerm == null)? false : name.equals(linkTerm.name);
    }

    public String getTerm(){
       return name;
    }
}
