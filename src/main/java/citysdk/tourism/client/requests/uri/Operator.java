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
package citysdk.tourism.client.requests.uri;

/**
 * The operator used in the URI Template
 * 
 * @author Pedro Cruz
 *
 */
public enum Operator {
	NULL_OP("", "", "", false),
	DEFAULT_OP("", "", ",", false),
	PLUS_OP("+", "", ",", false),
	SHARP_OP("#", "#", ",", false),
	DOT_OP(".", ".", ".", false),
	SLASH_OP("/", "/", "/", false),
	SEMICOLON_OP(";", ";", ";", true),
	QUERY_OP("?", "?", "&", true),
	FORM_OP("&", "&", "&", true);
	
	private final String value;
	private final String operator;
	private final String separator;
	private final boolean named;
	
	private Operator(String value, String operator, String separator, boolean named) {
		this.value = value;
		this.operator = operator;
		this.separator = separator;
		this.named = named;
	}

	public String getOperator() {
		return operator;
	}

	public String getSeparator() {
		return separator;
	}
	
	public boolean hasSeparator() {
		return !separator.equals("");
	}
	
	public boolean isNamed() {
		return named;
	}
	
	@Override
	public String toString() {
		return operator;
	}
	
	public static Operator fromOpCode(String op) {
		for(Operator value : Operator.values()) {
			if(op.equals(value.value))
				return value;
		}
		
		return NULL_OP;
	}
}
