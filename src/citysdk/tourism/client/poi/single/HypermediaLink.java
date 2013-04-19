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
package citysdk.tourism.client.poi.single;

import java.util.HashMap;
import java.util.Map;

/**
 * A HypermediaLink is a link in which a given client can make REST HTTP requests.
 * <p>It is composed of</p>
 * <ul>
 * <li>a version;</li>
 * <li>the available hypermedia links;</li>
 * </ul>
 * Some terminologies can be found in {@link citysdk.tourism.client.terms.ParameterTerms} (for parameters) and
 * {@link citysdk.tourism.client.terms.ResourceTerms} (for resources)
 * 
 * @author Pedro Cruz
 *
 */
public class HypermediaLink {
	private String version;
	private Map<String, Hypermedia> links;
	
	/**
	 * Initializes an empty {@link HypermediaLink}.
	 */
	public HypermediaLink() {
		links = new HashMap<String, Hypermedia>();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Map<String, Hypermedia> getLinks() {
		return links;
	}

	/**
	 * Adds an {@link Hypermedia} link to the group of available links
	 * @param media 
	 * 		the media term to be added
	 * @param link
	 * 		the {@link Hypermedia} containing the link
	 */
	public void addHypermedia(String media, Hypermedia link) {
		if(!links.containsKey(media))
			links.put(media, link);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		
		if(!(obj instanceof HypermediaLink))
			return false;
		
		HypermediaLink link = (HypermediaLink) obj;
		return link.version.equals(this.version);
	}
}
