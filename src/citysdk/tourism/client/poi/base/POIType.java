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
package citysdk.tourism.client.poi.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of the POIType class of the UML Diagram found in:
 * <a href="http://www.w3.org/2010/POI/documents/Core/core-20111216.html#poi-data-model">http://www.w3.org/2010/POI/documents/Core/core-20111216.html#poi-data-model.</a>
 * 
 * <p>The {@link POIType} entity is an abstract entity derived from {@link POIBaseType} and adds entities 
 * for describing, labeling, categorizing and indicating the time span of a POI or group of POIs. 
 * The entity also incudes child entities for linking to other POIs, external web resources or
 * metadata.</p>
 * 
 * <p>It is composed of:</p>
 * <ul> 
 * <li>labels - a human-readable name. Multiple names are used for synonyms and multiple languages;</li>
 * <li>descriptions - a human-readable description that can be discriminated with the language attribute;</li>
 * <li>categories - a categorical classification;</li>
 * <li>times - a fixed time or sequence of times using iCalendar;</li>
 * <li>links - a link to another POI or web resource.</li>
 * </ul>
 * 
 * @author Pedro Cruz
 *
 */
public class POIType extends POIBaseType {
	private List<POITermType> label;
	private List<POIBaseType> description;
	private List<POITermType> category;
	private List<POITermType> time;
	private List<POITermType> link;
	
	/**
	 * Creates an empty POIType.
	 */
	public POIType() {
		super();
		label = new ArrayList<POITermType>();
		description = new ArrayList<POIBaseType>();
		time = new ArrayList<POITermType>();
		link = new ArrayList<POITermType>();
		category = new ArrayList<POITermType>();
	}
	
	/**
	 * Adds a category to the POIType.
	 * @param category the category to be added.
	 */
	public void addCategory(POITermType category) {
		this.category.add(category);
	}
	
	/**
	 * Adds a label to the POIType.
	 * @param label the label to be added.
	 */
	public void addLabel(POITermType label) {
		this.label.add(label);
	}
	
	/**
	 * Adds a description to the POIType.
	 * @param description the description to be added.
	 */
	public void addDescription(POIBaseType description) {
		this.description.add(description);
	}
	
	/**
	 * Adds a time to the POIType.
	 * @param time the time to be added.
	 */
	public void addTime(POITermType time) {
		this.time.add(time);
	}
	
	/**
	 * Adds a link to the POIType.
	 * @param link the link to be added.
	 */
	public void addLink(POITermType link) {
		this.link.add(link);
	}
	
	/**
	 * Gets all the labels of the POIType.
	 * @return a list of labels of the POIType.
	 */
	public List<POITermType> getLabel() {
		return label;
	}

	/**
	 * Gets all the descriptions of the POIType.
	 * @return a list of descriptions of the POIType.
	 */
	public List<POIBaseType> getDescription() {
		return description;
	}

	/**
	 * Gets all the times of the POIType.
	 * @return a list of times of the POIType.
	 */
	public List<POITermType> getTime() {
		return time;
	}

	/**
	 * Gets all the links of the POIType.
	 * @return a list of links of the POIType.
	 */
	public List<POITermType> getLink() {
		return link;
	}

	/**
	 * Gets all the categories of the POIType.
	 * @return a list of categories of the POIType.
	 */
	public List<POITermType> getCategory() {
		return category;
	}
	
	/**
	 * Checks if there are labels in the POIType.
	 * @return <code>true</code> if there are labels in the POIType, <code>false</code> otherwise
	 */
	public boolean hasLabels() {
		return !label.isEmpty();
	}
	
	/**
	 * Checks if there are descriptions in the POIType.
	 * @return <code>true</code> if there are descriptions in the POIType, <code>false</code> otherwise.
	 */
	public boolean hasDescriptions() {
		return !description.isEmpty();
	}
	
	/**
	 * Checks if there are times in the POIType.
	 * @return <code>true</code> if there are times in the POIType, <code>false</code> otherwise.
	 */
	public boolean hasTimes() {
		return !time.isEmpty();
	}
	
	/**
	 * Checks if there are links in the POIType.
	 * @return <code>true</code> if there are links in the POIType, <code>false</code> otherwise.
	 */
	public boolean hasLinks() {
		return !link.isEmpty();
	}
	
	/**
	 * Checks if there are categories in the POIType.
	 * @return <code>true</code> if there are categories in the POIType, <code>false</code> otherwise
	 */
	public boolean hasCategories() {
		return !category.isEmpty();
	}
}
