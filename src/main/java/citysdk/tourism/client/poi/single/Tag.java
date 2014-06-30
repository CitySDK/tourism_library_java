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

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a {@link Tag} object.
 * 
 * <p>A {@link Tag} is used to characterize {@link PointOfInterest}, {@link Event} or {@link Route}.</p>
 * 
 * @author Pedro Cruz
 *
 */
public class Tag extends POI {
	private List<Tag> tags;
	
	/**
	 * Initializes an empty {@link Tag}
	 */
	public Tag() {
		super();
		tags = null;
	}
	
	/**
	 * Gets all the tags.
	 * @return a list of {@link Tag} values
	 */
	public List<Tag> getTags() {
		return tags;
	}

	/**
	 * Adds a {@link Tag} value
	 * @param tag {@link Tag} value to be added
	 */
	public void addTag(Tag tag) {
		if(tags == null)
			tags = new ArrayList<Tag>();
		
		tags.add(tag);
	}
	
	/**
	 * Gets the {@link Tag} in index i
	 * @param i the {@link Tag} index
	 * @return the {@link Tag} at index i or null
	 */
	public Tag getTag(int i) {
		if(i < getNumTags())
			return tags.get(i);
		else
			return null;
	}
	
	/**
	 * Gets the number of {@link Tag} values
	 * @return the total number of {@link Tag} values
	 */
	public int getNumTags() {
		if(tags != null)
			return tags.size();
		else
			return 0;
	}
}
