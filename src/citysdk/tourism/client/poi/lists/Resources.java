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
package citysdk.tourism.client.poi.lists;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import citysdk.tourism.client.poi.Deserializable;
import citysdk.tourism.client.poi.single.Hypermedia;
import citysdk.tourism.client.poi.single.HypermediaLink;
import citysdk.tourism.client.requests.uri.UriTemplate;
import citysdk.tourism.client.terms.ParameterTerms;
import citysdk.tourism.client.terms.ResourceTerms;

/**
 * Represents the resources provided by a server. It contains all the available
 * {@link citysdk.tourism.client.poi.single.HypermediaLink} given by server
 * alongside their API versions.
 * 
 * @author Pedro Cruz
 * 
 */
public class Resources implements Deserializable {
	private Map<String, HypermediaLink> resources;

	public Resources() {
		super();
		resources = new HashMap<String, HypermediaLink>();
	}

	/**
	 * Adds a {@link citysdk.tourism.client.poi.single.HypermediaLink}.
	 * 
	 * @param link
	 *            the {@link citysdk.tourism.client.poi.single.HypermediaLink}
	 *            to be added.
	 */
	public void addResource(HypermediaLink link) {
		if (!resources.containsKey(link.getVersion())) {
			resources.put(link.getVersion(), link);
		}
	}

	/**
	 * Checks if this Resource has a given resource in a given version.
	 * 
	 * @param version
	 *            the version of the link
	 * @param resource
	 *            the resource to be found.
	 * @return <code>true</code> if it has the given resource,
	 *         <code>false</code> otherwise.
	 */
	public boolean hasResource(String version, ResourceTerms resource) {
		if (version != null && resource != null
				&& resources.containsKey(version)) {
			return resources.get(version).getLinks().containsKey(resource.getTerm());
		}

		return false;
	}

	/**
	 * Checks the existance of a given version
	 * 
	 * @param version
	 *            version to check
	 * @return <code>true</code> if it is available, <code>false</code>
	 *         otherwise.
	 */
	public boolean hasVersion(String version) {
		if (version != null)
			return resources.containsKey(version);

		return false;
	}

	/**
	 * Checks whether a given resource in a given version allows the search
	 * using a given parameter
	 * 
	 * @param version
	 *            the wanted version
	 * @param resource
	 *            the name of the resource
	 * @param parameter
	 *            the search parameter
	 * @return <code>true</code> if it is allowed, <code>false</code> otherwise
	 */
	public boolean hasResourceParameter(String version, ResourceTerms resource,
			ParameterTerms parameter) {
		if (version == null || resource == null || parameter == null)
			return false;

		if (resources.containsKey(version)) {
			Map<String, Hypermedia> links = resources.get(version).getLinks();
			Hypermedia hypermedia;
			if ((hypermedia = links.get(resource.getTerm())) != null
					&& hypermedia.isTemplated()) {
				return UriTemplate.fromTemplate(hypermedia.getHref()).hasParameter(
						parameter.getTerm());
			}
		}

		return false;
	}

	/**
	 * Gets the {@link citysdk.tourism.client.poi.single.HypermediaLink} with
	 * the given version.
	 * 
	 * @param version
	 *            the version of the
	 *            {@link citysdk.tourism.client.poi.single.HypermediaLink}.
	 * @return the {@link citysdk.tourism.client.poi.single.HypermediaLink}
	 *         corresponding to the given version or null if the version is not
	 *         available
	 */
	public HypermediaLink getHypermediaWithVersion(String version) {
		if (version != null)
			return resources.get(version);

		return null;
	}

	/**
	 * Gets all the resources of a given version
	 * 
	 * @param version
	 *            the wanted version
	 * @return a set of resources of the given version
	 */
	public Set<String> getResources(String version) {
		Set<String> resource = new HashSet<String>();

		if (resources.containsKey(version))
			resource.addAll(resources.get(version).getLinks().keySet());

		return resource;
	}

	/**
	 * Gets all the versions stored
	 * 
	 * @return a set of versions
	 */
	public Set<String> getVersions() {
		return resources.keySet();
	}
}
