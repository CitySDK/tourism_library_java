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
 * Representation of the {@link Location} class of the UML diagram found in:
 * <a href="http://www.w3.org/2010/POI/documents/Core/core-20111216.html#poi-data-model">http://www.w3.org/2010/POI/documents/Core/core-20111216.html#poi-data-model.</a>
 * 
 * <p>A {@link Location} is composed of:</p>
 * <ul>
 * <li>a group of {@link Point}, {@link Line}, {@link Polygon} representing the {@link Location}'s Geometry (such as: entry/exit points);</li>
 * <li>a group of {@link Relationship} representing the relationship of this {@link Location} with other {@link citysdk.tourism.client.poi.single.PointOfInterest} or {@link citysdk.tourism.client.poi.single.Event};</li>
 * <li>address representing the civic address of the {@link Location} in vCard format.</li>
 * </ul>
 * 
 * @author Pedro Cruz
 *
 */
public class Location extends POIBaseType {
	private List<Point> point;
	private List<Line> line;
	private List<Polygon> polygon;
	private POIBaseType address;
	private List<Relationship> relationship;

	/**
	 * Initializes an empty {@link Location}, with no geometries or addresses.
	 */
	public Location() {
		point = new ArrayList<Point>();
		line = new ArrayList<Line>();
		polygon = new ArrayList<Polygon>();
		address = new POIBaseType();
		relationship = new ArrayList<Relationship>();
	}
	
	/**
	 * Adds a {@link Point} to the {@link Location}.
	 * @param point the {@link Point} to be added.
	 */
	public void addPoint(Point point) {
		this.point.add(point);
	}
	
	/**
	 * Adds a {@link Line} to the {@link Location}.
	 * @param line the {@link Line} to be added.
	 */
	public void addLine(Line line) {
		this.line.add(line);
	}
	
	/**
	 * Adds a {@link Polygon} to the {@link Location}.
	 * @param polygon the {@link Polygon} to be added.
	 */
	public void addPolygon(Polygon polygon) {
		this.polygon.add(polygon);
	}

	/**
	 * Adds a {@link Relationship} to the {@link Location}.
	 * @param relationship the {@link Relationship} to be added.
	 */
	public void addRelationship(Relationship relationship) {
		this.relationship.add(relationship);
	}
	
	/**
	 * Gets all points of the {@link Location}.
	 * @return a list of {@link Point} of the {@link Location}.
	 */
	public List<Point> getPoint() {
		return point;
	}

	/**
	 * Gets all lines of the {@link Location}.
	 * @return a list of {@link Line} of the {@link Location}.
	 */
	public List<Line> getLine() {
		return line;
	}

	/**
	 * Gets all the polygons of the {@link Location}.
	 * @return a list of {@link Polygon} of the {@link Location}.
	 */
	public List<Polygon> getPolygon() {
		return polygon;
	}
	
	/**
	 * Gets all the relationships of the {@link Location}.
	 * @return a list of {@link Relationship} of the {@link Location}.
	 */
	public List<Relationship> getRelationship() {
		return relationship;
	}

	/**
	 * Gets the address of the {@link Location}.
	 * @return the address of the {@link Location}.
	 */
	public POIBaseType getAddress() {
		return address;
	}

	/**
	 * Sets the address of the {@link Location}.
	 * @param address the new address for the {@link Location}.
	 */
	public void setAddress(POIBaseType address) {
		this.address = address;
	}
	
	/**
	 * Checks if the {@link Location} has any points.
	 * @return <code>true</code> if there are points in the {@link Location}, <code>false</code> otherwise.
	 */
	public boolean hasPoints() {
		return !point.isEmpty();
	}
	
	/**
	 * Checks if the {@link Location} has any lines.
	 * @return <code>true</code> if there are lines in the {@link Location}, <code>false</code> otherwise
	 */
	public boolean hasLines() {
		return !line.isEmpty();
	}
	
	/**
	 * Checks if the {@link Location} has any polygons.
	 * @return <code>true</code> if there are polygons in the {@link Location}, <code>false</code> otherwise
	 */
	public boolean hasPolygons() {
		return !polygon.isEmpty();
	}
	
	/**
	 * Checks if the {@link Location} has any relationships.
	 * @return <code>true</code> if there are relationships in the {@link Location}, <code>false</code> otherwise
	 */
	public boolean hasRelationships() {
		return !relationship.isEmpty();
	}
}
