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
package citysdk.tourism.client.requests;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import citysdk.tourism.client.exceptions.InvalidParameterException;
import citysdk.tourism.client.exceptions.InvalidValueException;
import citysdk.tourism.client.terms.ParameterTerms;

/**
 * It is used to indicate what parameters should the URI have upon a HTTP
 * request. Its term should be one of the terms shown in {@link ParameterTerms}
 * or it will throw an
 * {@link citysdk.tourism.client.exceptions.InvalidParameterException} and its
 * value should be the search value.
 * 
 * @author Pedro Cruz
 * 
 */
public class Parameter {
	private ParameterTerms term;
	private Object value;

	/**
	 * Constructor first validates the name before actually creating the
	 * Parameter. Its validation verifies if it is one of the terms found in
	 * {@link ParameterTerms}. If it is not found in the aforementioned enum,
	 * then it will throw an
	 * {@link citysdk.tourism.client.exceptions.InvalidParameterException}.
	 * 
	 * @param term
	 *            the term of the parameter
	 * @param value
	 *            the value of the parameter used for search. It can be either a
	 *            primitive class (Number, CharSequence or Boolean) implementing
	 *            toString() or a Collection or a Map.
	 * @throws InvalidParameterException
	 *             thrown if the parameter term is invalid
	 * @throws InvalidValueException
	 *             thrown if value is not one of the mentioned classes
	 */
	public Parameter(ParameterTerms term, Object value)
			throws InvalidParameterException, InvalidValueException {
		validateName(term);
		validateValue(value);
		this.term = term;
		this.value = value;
	}

	/*
	 * Checks the validity of the name (by using the available search parameters)
	 */
	private void validateName(ParameterTerms term)
			throws InvalidParameterException {
		List<String> parameters = ParameterTerms.SEARCH_PARAMETERS.getChildren();
		for (String parameter : parameters) {
			if (parameter.equals(term.getTerm())) {
				return;
			}
		}

		throw new InvalidParameterException(term
				+ " is not a valid query name value");
	}

	/*
	 * Checks if the type of the object is valid
	 */
	private void validateValue(Object obj) throws InvalidValueException {
		if (obj != null && ((obj instanceof Collection)
				|| (obj instanceof Map) || isSimpleType(obj)))
			return;
		
		throw new InvalidValueException("Value must be a primitive");
	}

	private boolean isSimpleType(Object obj) {
		if (obj.getClass().isPrimitive() 
				|| obj instanceof Number || obj instanceof CharSequence
				|| obj instanceof Boolean || obj instanceof String)
			return true;
		else
			return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (obj instanceof Parameter) {
			return ((Parameter) obj).getTerm().equals(this.getTerm());
		} else
			return false;
	}

	public ParameterTerms getParameterTerm() {
		return term;
	}

	public String getTerm() {
		return term.getTerm();
	}

	public Object getValue() {
		return value;
	}
}
