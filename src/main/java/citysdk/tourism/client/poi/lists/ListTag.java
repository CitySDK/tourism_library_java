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
package citysdk.tourism.client.poi.lists;

import java.util.List;

import citysdk.tourism.client.poi.single.Tag;

/**
 * Represents a list of {@link citysdk.tourism.client.poi.single.Tag}.
 * 
 * @author Pedro Cruz
 *
 */
public class ListTag extends POIS<Tag> {
	/**
	 * Creates an empty list of {@link citysdk.tourism.client.poi.single.Tag}.
	 */
	public ListTag(){
		super();
	}
	
	/**
	 * Adds a {@link citysdk.tourism.client.poi.single.Tag}
	 * @param tag the {@link citysdk.tourism.client.poi.single.Tag} to be added
	 */
	public void addTag(Tag tag) {
		super.add(tag);
	}

	/**
	 * Gets all the tags
	 * @return the list of {@link citysdk.tourism.client.poi.single.Tag}
	 */
	public List<Tag> getTags() {
		return super.getList();
	}
	
	/**
	 * Gets a given {@link citysdk.tourism.client.poi.single.Tag}
	 * @param i the index of the {@link citysdk.tourism.client.poi.single.Tag}
	 * @return the {@link citysdk.tourism.client.poi.single.Tag} stored within the given index or null
	 */
	@Override
	public Tag get(int i) {
		return super.get(i);
	}

	/**
	 * Returns the number of tags stored
	 * @return the total number of tags
	 */
	public int getNumTags() {
		return super.size();
	}
}
