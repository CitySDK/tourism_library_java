/*
 * COPYRIGHT NOTICE:
 *
 * This file is part of CitySDK WP5 Tourism Library.
 *
 * CitySDK WP5 Tourism Library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CitySDK WP5 Tourism Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with CitySDK WP5 Tourism Library. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2013, IST
 */
package citysdk.tourism.client.parser;

/**
 * An enum containing each language-supported field of a {@link citysdk.tourism.client.poi.single.POI}.
 * 
 * @author Pedro Cruz
 *
 */
public enum Field {
	FIELD_LABEL("getLabels"),
	FIELD_DESCRIPTION("getDescriptions");
	
	private final String name;
	
	private Field(String s) {
        name = s;
    }

    public String getField(){
       return name;
    }
}
