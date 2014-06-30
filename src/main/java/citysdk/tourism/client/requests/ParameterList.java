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

import java.util.ArrayList;
import java.util.List;

import citysdk.tourism.client.terms.ParameterTerms;

/**
 * A list of {@link Parameter} used for the {@link TourismClient} stub when performing HTTP requests.
 * 
 * @author Pedro Cruz
 *
 */
public class ParameterList {
	private List<Parameter> parameters;
	
	/**
	 * Initializes an empty list of {@link Parameter}.
	 */
	public ParameterList() {
		this.parameters = new ArrayList<Parameter>();
	}
	
	/**
	 * Adds a new parameter, if it does not exist already.
	 * @param parameter {@link Parameter} to be added.
	 */
	public void add(Parameter parameter) {
		if(!parameters.contains(parameter))
			parameters.add(parameter);
	}
	
	/**
	 * Gets the {@link Parameter} in the given index.
	 * @param i index of the {@link Parameter}.
	 * @return {@link Parameter} in index i or null.
	 */
	public Parameter get(int i) {
		if(i < parameters.size())
			return parameters.get(i);
		
		return null;
	}
	
	/**
	 * Gets the {@link Parameter} with the given term.
	 * @param term the term of the {@link Parameter}.
	 * @return {@link Parameter} with the given term or null.
	 */
	public Parameter getWithTerm(ParameterTerms term) {
		for(Parameter parameter : parameters) {
			if(parameter.getParameterTerm().equalsTerm(term.getTerm()))
				return parameter;
		}
		
		return null;
	}
	
	/**
	 * Replaces or adds a given {@link Parameter}.
	 * @param parameter the {@link Parameter} to be replaced/added.
	 */
	public void replace(Parameter parameter) {
		int index;
		if((index = parameters.indexOf(parameter)) > 0) {
			parameters.set(index, parameter);
		} else {
			add(parameter);
		}
	}
	
	/**
	 * Removes all {@link Parameter}.
	 */
	public void removeAll() {
		if(!parameters.isEmpty())
			parameters.removeAll(parameters);
	}
	
	/**
	 * Gets the number of parameters in the parameter list.
	 * @return the number of parameters.
	 */
	public int size() {
		return parameters.size();
	}
	
	/**
	 * Get the array representation of the parameters.
	 * @return an array of parameters.
	 */
	public Parameter[] toArray() {
		return parameters.toArray(new Parameter[parameters.size()]);
	}
}
