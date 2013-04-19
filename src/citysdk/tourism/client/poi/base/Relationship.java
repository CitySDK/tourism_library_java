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

/**
 * Representation of the Relationship class of the UML diagram found in:
 * <a href="http://www.w3.org/2010/POI/documents/Core/core-20111216.html#poi-data-model">http://www.w3.org/2010/POI/documents/Core/core-20111216.html#poi-data-model.</a>
 * 
 * <p>The {@link Relationship} entity is derived from {@link POITermType} and establishes 1-to-1 or 
 * 1-to-many relationships between POIs</p>
 * 
 * @author Pedro Cruz
 *
 */
public class Relationship extends POITermType {
	private String targetPOI;
	private String targetEvent;
	
	/**
	 * Initializes an empty {@link Relationship}.
	 */
	public Relationship() {
		super();
		targetPOI = null;
		targetEvent = null;
	}

	/**
	 * Returns which target is a given POI related with.
	 * @return the POI id related with another POI
	 */
	public String getTargetPOI() {
		return targetPOI;
	}
	
	/**
	 * Returns which target is a given Event related with.
	 * @return the Event id related with another Event
	 */
	public String getTargetEvent() {
		return targetEvent;
	}

	/**
	 * Sets the POI that a given POI is related with.
	 * @param targetPOI POI id related with another POI/Event
	 */
	public void setTargetPOI(String targetPOI) {
		this.targetPOI = targetPOI;
	}

	/**
	 * Sets the Event that a given Event is related with.
	 * @param targetEvent Event id related with another POI/Event
	 */
	public void setTargetEvent(String targetEvent) {
		this.targetEvent = targetEvent;
	}
	
	/**
	 * Checks whether this relationship has a event as a target
	 * @return <code>true</code> if has an Event as a target, <code>false</code> otherwise
	 */
	public boolean hasTargetEvent() {
		return targetEvent != null;
	}
	
	/**
	 * Checks whether this relationship has a POI as a target
	 * @return <code>true</code> if has a POI as a target, <code>false</code> otherwise
	 */
	public boolean hasTargetPOI() {
		return targetPOI != null;
	}
}
