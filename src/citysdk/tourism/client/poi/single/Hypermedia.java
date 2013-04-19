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

/**
 * Includes the href of the hypermedia and if it is a URI template as presented in
 * <a target="_blank" href="http://tools.ietf.org/html/rfc6570">RFC6570</a>.
 * 
 * @author Pedro Cruz
 *
 */
public class Hypermedia {
	private String href;
	private boolean templated;
	
	/**
	 * Empty constructor
	 */
	public Hypermedia() { 
		this.setHref("");
		this.setTemplated(false);
	}
	
	public Hypermedia(String href, boolean templated) {
		this.setHref(href);
		this.setTemplated(templated);
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public boolean isTemplated() {
		return templated;
	}

	public void setTemplated(boolean templated) {
		this.templated = templated;
	}
}
