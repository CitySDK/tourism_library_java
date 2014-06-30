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
 * Representation of a {@link Category} object.
 * 
 * <p>A {@link Category} is used to characterize {@link PointOfInterest}, {@link Event} or {@link Route}. 
 * It can contain its own set of sub-categories.
 * 
 * @author Pedro Cruz
 *
 */
public class Category extends POI {
	private List<Category> subCategories;
	
	public Category() {
		super();
		subCategories = null;
	}

	/**
	 * Gets all the sub categories of the {@link Category}.
	 * @return a list of {@link Category}.
	 */
	public List<Category> getSubCategories() {
		return subCategories;
	}

	/**
	 * Adds a {@link Category} to this {@link Category}.
	 * @param category {@link Category} to be added.
	 */
	public void addCategory(Category category) {
		if(subCategories == null)
			subCategories = new ArrayList<Category>();
		
		subCategories.add(category);
	}
	
	/**
	 * Gets the category at a given index
	 * @param i an index
	 * @return the category at index i
	 */
	public Category getCategory(int i) {
		if(subCategories == null)
			return null;
		else
			return subCategories.get(i);
	}
	
	/**
	 * Gets the number of sub categories in the {@link Category}.
	 * @return the total number of sub-categories.
	 */
	public int getNumCategories() {
		if(subCategories != null)
			return subCategories.size();
		else
			return 0;
	}
	
	/**
	 * Checks whether this {@link Category} has sub-categories.
	 * @return <code>true</code> if it has sub-categories, <code>false</code> otherwise
	 */
	public boolean hasSubCategories() {
		return getNumCategories() > 0;
	}
}
