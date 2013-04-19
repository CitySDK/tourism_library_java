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

import java.util.Date;

/**
 * Representation of the POIBaseType class of the UML diagram found in:
 * <a href="http://www.w3.org/2010/POI/documents/Core/core-20111216.html#poi-data-model">http://www.w3.org/2010/POI/documents/Core/core-20111216.html#poi-data-model.</a>
 * 
 * <p>This is the base class of other classes and it is composed of:</p>
 * <ul>
 * <li>id - a unique identifier for this location. Can be a URI fragment;</li>
 * <li>value - the information content;</li>
 * <li>base - base URI;</li>
 * <li>href - an absolute reference to the content type;</li>
 * <li>type - MIME type [<a href="http://www.ietf.org/rfc/rfc2046.txt">RFC2046</a>];</li>
 * <li>lang - language type[<a href="http://www.ietf.org/rfc/rfc3066.txt">RFC3066</a>];</li>
 * <li>created - time at which this POIBaseType was created;</li>
 * <li>updated - time at which this POIBaseType was updated;</li>
 * <li>deleted - time at which this POIBaseType was deleted;</li>
 * <li>author - the author of this POIBaseType. Represented by a {@link POITermType};</li>
 * <li>license - the license restrictions of this information. Represented by a {@link POITermType}.</li>
 * </ul>
 * 
 * @author Pedro Cruz
 *
 */
public class POIBaseType {
	private String id;
	private String value;
	private String href;
	private String type;
	private String lang;
	private String base;
	private Date created;
	private Date updated;
	private Date deleted;
	private POITermType author;
	private POITermType license;
	
	/**
	 * Creates an empty POIBaseType.
	 */
	public POIBaseType() {
		id = new String("");
		value = new String("");
		href = new String("");
		type = new String("");
		lang = new String("");
		base = new String("");
		created = new Date();
		updated = new Date();
		deleted = new Date();
		author = null;
		license = null;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getHref() {
		return href;
	}
	
	public void setHref(String href) {
		this.href = href;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getLang() {
		return lang;
	}
	
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public String getBase() {
		return base;
	}
	
	public void setBase(String base) {
		this.base = base;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getDeleted() {
		return deleted;
	}

	public void setDeleted(Date deleted) {
		this.deleted = deleted;
	}

	public POITermType getAuthor() {
		return author;
	}

	public void setAuthor(POITermType author) {
		this.author = author;
	}

	public POITermType getLicense() {
		return license;
	}

	public void setLicense(POITermType license) {
		this.license = license;
	}
}
