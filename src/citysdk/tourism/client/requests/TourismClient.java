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

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import citysdk.tourism.client.exceptions.InvalidParameterException;
import citysdk.tourism.client.exceptions.InvalidParameterTermException;
import citysdk.tourism.client.exceptions.InvalidResourceTermException;
import citysdk.tourism.client.exceptions.InvalidValueException;
import citysdk.tourism.client.exceptions.ResourceNotAllowedException;
import citysdk.tourism.client.exceptions.ServerErrorException;
import citysdk.tourism.client.exceptions.UnknownErrorException;
import citysdk.tourism.client.exceptions.VersionNotAvailableException;
import citysdk.tourism.client.parser.JsonParser;
import citysdk.tourism.client.poi.lists.ListEvent;
import citysdk.tourism.client.poi.lists.ListPOIS;
import citysdk.tourism.client.poi.lists.ListPointOfInterest;
import citysdk.tourism.client.poi.lists.ListRoute;
import citysdk.tourism.client.poi.lists.ListTag;
import citysdk.tourism.client.poi.lists.Resources;
import citysdk.tourism.client.poi.single.Category;
import citysdk.tourism.client.poi.single.Event;
import citysdk.tourism.client.poi.single.Hypermedia;
import citysdk.tourism.client.poi.single.HypermediaLink;
import citysdk.tourism.client.poi.single.POI;
import citysdk.tourism.client.poi.single.PointOfInterest;
import citysdk.tourism.client.poi.single.Route;
import citysdk.tourism.client.requests.uri.UriTemplate;
import citysdk.tourism.client.terms.ParameterTerms;
import citysdk.tourism.client.terms.ResourceTerms;
import citysdk.tourism.client.terms.Term;

/**
 * Stub used for the CitySDK Tourism API. It abstracts applications from the
 * different requests allowed in the API and returns the correct data objects
 * from such calls. The stub also provides a logging mechanism defaulting to 
 * INFO level messages.
 * 
 * @author Pedro Cruz
 * 
 */
public class TourismClient implements Cloneable {
	private String homeUrl;
	private JsonParser parser;
	private Resources resources;
	private String version;
	private Logger logger; 

	protected TourismClient() {
		this.version = null;
	}

	protected TourismClient(String homeUrl, Resources links) {
		this.homeUrl = homeUrl;
		this.resources = links;
		this.parser = new JsonParser(null);
		initLogging();
	}

	protected TourismClient(TourismClient client) {
		this.homeUrl = client.homeUrl;
		this.resources = client.resources;
		this.version = client.version;
		this.parser = new JsonParser(null);
		initLogging();
	}
	
	private void initLogging() {
		this.logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
		this.logger.setLevel(Level.INFO);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * Set the logging level for the logger
	 * @param level the wanted level
	 */
	public void setLoggingLevel(Level level) {
		ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(level);
        
        this.logger.addHandler(handler);
		this.logger.setLevel(level);
	}
	
	public String getVersion() {
		return version;
	}

	/**
	 * Set the version to use by the stub
	 * @param version the version to use
	 */
	public void useVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets all the available resources of a given version for the queried
	 * server.
	 * 
	 * @return a set of resources allowed for the server
	 * @throws VersionNotAvailableException
	 *             thrown if a previously set version is not available
	 */
	public Set<String> getAvailableResources()
			throws VersionNotAvailableException {
		if (resources == null)
			return new HashSet<String>();

		verifyVersion();
		return resources.getResources(version);
	}

	/**
	 * Verifies if the client has a given resource
	 * 
	 * @param resource
	 *            the resource to find
	 * @return <code>true</code> if the resource is present, <code>false</code>
	 *         otherwise
	 * @throws VersionNotAvailableException
	 *             thrown if the version is not supported
	 */
	public boolean hasResource(ResourceTerms resource)
			throws VersionNotAvailableException {
		if (resources == null)
			return false;

		verifyVersion();
		return resources.hasResource(version, resource);
	}

	/**
	 * Checks whether the server has any resources of POIs, Events or Routes in
	 * a given version.
	 * 
	 * @param resource
	 *            the term to search for. It must be RESOURCE_POIS,
	 *            RESOURCE_EVENTS or RESOURCE_ROUTES, otherwise it will return
	 *            false.
	 * @return <code>true</code> if the server has any given capacity of the
	 *         referred term, <code>false</code> otherwise.
	 * @throws VersionNotAvailableException
	 *             thrown if a previously set version is not available
	 * @throws InvalidResourceTermException
	 *             thrown if the resource term is not one of the presented terms
	 */
	public boolean hasAnyResourcesOf(ResourceTerms resource)
			throws VersionNotAvailableException, InvalidResourceTermException {
		if (!resource.hasChildren() || resources == null)
			return false;

		verifyVersion();
		validateResource(resource);
		List<String> links = resource.getChildren();
		for (String link : links) {
			if (this.resources.hasResource(version,
					ResourceTerms.fromString(link)))
				return true;
		}

		return false;
	}

	/**
	 * Checks whether a given resource is available for the queried server.
	 * 
	 * @param resource
	 *            the wanted resource
	 * @param parameter
	 *            a given search parameter.
	 * @return <code>true</code> if available, <code>false</code> otherwise.
	 * @throws VersionNotAvailableException
	 *             thrown if a previously set version is not available
	 */
	public boolean hasResourceParameter(ResourceTerms resource,
			ParameterTerms parameter) throws VersionNotAvailableException {
		if (resources == null)
			return false;

		verifyVersion();
		return resources.hasResourceParameter(version, resource, parameter);
	}

	/**
	 * Returns a list of POIs following the desired parameters. If such
	 * parameters are not valid terms for a POI, it will throw an
	 * {@link citysdk.tourism.client.exceptions.InvalidParameterException}.
	 * 
	 * @param parameterList
	 *            the parameters that should be followed.
	 * @return a {@link citysdk.tourism.client.poi.lists.ListPointOfInterest}.
	 * @throws InvalidParameterException
	 *             thrown if the given parameterList contains invalid terms.
	 * @throws IOException
	 *             thrown in case of socket errors.
	 * @throws ResourceNotAllowedException
	 *             thrown if the server does not support POIs listing
	 * @throws UnknownErrorException
	 *             thrown in case of unforeseen errors.
	 * @throws ServerErrorException
	 *             thrown if the server returns a code different from HTTP 200
	 *             OK.
	 * @throws VersionNotAvailableException
	 *             thrown if a previously set version is not available
	 */
	public ListPointOfInterest getPois(ParameterList parameterList)
			throws InvalidParameterException, IOException,
			ResourceNotAllowedException, UnknownErrorException,
			ServerErrorException, VersionNotAvailableException {
		String url = validateAndBuildUrl(ResourceTerms.FIND_POI, parameterList);
		parser.setJson(Request.getResponse(url));
		return parser.parseJsonAsListOfPois();
	}

	/**
	 * Returns a list of Events following the desired parameters. If such
	 * parameters are not valid queries for an Event, it will throw and
	 * {@link citysdk.tourism.client.exceptions.InvalidParameterException}.
	 * 
	 * @param parameterList
	 *            the parameters that should be followed.
	 * @return a {@link citysdk.tourism.client.poi.lists.ListEvent}.
	 * @throws InvalidParameterException
	 *             thrown if the given parameterList contains invalid terms.
	 * @throws IOException
	 *             thrown in case of socket errors.
	 * @throws ResourceNotAllowedException
	 *             thrown if the server does not support events listing
	 * @throws UnknownErrorException
	 *             thrown in case of unforeseen errors.
	 * @throws ServerErrorException
	 *             thrown if the server returns a code different from HTTP 200
	 *             OK.
	 * @throws VersionNotAvailableException
	 *             thrown if a previously set version is not available
	 */
	public ListEvent getEvents(ParameterList parameterList)
			throws InvalidParameterException, IOException,
			ResourceNotAllowedException, UnknownErrorException,
			ServerErrorException, VersionNotAvailableException {
		String url = validateAndBuildUrl(ResourceTerms.FIND_EVENT,
				parameterList);
		parser.setJson(Request.getResponse(url));
		return parser.parseJsonAsListOfEvents();
	}

	/**
	 * Returns a list of Routes following the desired parameters. If such
	 * parameters are not valid queries for a Route, it will throw and
	 * {@link citysdk.tourism.client.exceptions.InvalidParameterException}.
	 * 
	 * @param parameterList
	 *            the parameters that should be followed.
	 * @return a {@link citysdk.tourism.client.poi.lists.ListRoute}.
	 * @throws InvalidParameterException
	 *             thrown if the given parameterList contains invalid terms.
	 * @throws IOException
	 *             thrown in case of socket errors.
	 * @throws ResourceNotAllowedException
	 *             thrown if the server does not support routes listing
	 * @throws UnknownErrorException
	 *             thrown in case of unforeseen errors.
	 * @throws ServerErrorException
	 *             thrown if the server returns a code different from HTTP 200
	 *             OK.
	 * @throws VersionNotAvailableException
	 *             thrown if a previously set version is not available
	 */
	public ListRoute getRoutes(ParameterList parameterList)
			throws InvalidParameterException, IOException,
			ResourceNotAllowedException, UnknownErrorException,
			ServerErrorException, VersionNotAvailableException {
		String url = validateAndBuildUrl(ResourceTerms.FIND_ROUTE,
				parameterList);
		parser.setJson(Request.getResponse(url));
		return parser.parseJsonAsListOfRoutes();
	}

	/**
	 * Returns the list of {@link citysdk.tourism.client.poi.single.Category}
	 * available. The term should be either one of the following:
	 * <ul>
	 * <li>{@link citysdk.tourism.client.terms.ParameterTerms#POIS};</li>
	 * <li>{@link citysdk.tourism.client.terms.ParameterTerms#EVENTS};</li>
	 * <li>{@link citysdk.tourism.client.terms.ParameterTerms#ROUTES}.</li>
	 * </ul>
	 * 
	 * @param list
	 *            the parameters that should be followed. It should contain a
	 *            list with one of the above terms specified
	 * @return a {@link citysdk.tourism.client.poi.single.Category}.
	 * @throws IOException
	 *             thrown in case of socket errors.
	 * @throws UnknownErrorException
	 *             thrown in case of unforeseen errors.
	 * @throws InvalidParameterTermException
	 *             thrown if the term is not one of aforementioned valid terms.
	 * @throws ServerErrorException
	 *             thrown if the server returns a code different from HTTP 200
	 *             OK.
	 * @throws ResourceNotAllowedException
	 *             thrown if the server does not support category listing.
	 * @throws VersionNotAvailableException
	 *             thrown if a previously set version is not available.
	 */
	public Category getCategories(ParameterList list) throws IOException,
			UnknownErrorException, InvalidParameterTermException,
			ServerErrorException, ResourceNotAllowedException,
			VersionNotAvailableException {
		verifyVersion();
		validateTerm((String) list.getWithTerm(ParameterTerms.LIST).getValue());
		try {
			String url = validateAndBuildUrl(ResourceTerms.FIND_CATEGORIES,
					list);
			parser.setJson(Request.getResponse(url));
			return parser.parseJsonAsCategory();
		} catch (InvalidParameterException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Returns the list of {@link citysdk.tourism.client.poi.lists.ListTag}
	 * available. The term should be either one of the following:
	 * <ul>
	 * <li>{@link citysdk.tourism.client.terms.ParameterTerms#POIS};</li>
	 * <li>{@link citysdk.tourism.client.terms.ParameterTerms#EVENTS};</li>
	 * <li>{@link citysdk.tourism.client.terms.ParameterTerms#ROUTES}.</li>
	 * </ul>
	 * 
	 * @param list
	 *            the parameters that should be followed. It should contain a
	 *            list with one of the above terms specified
	 * @return a {@link citysdk.tourism.client.poi.lists.ListTag}.
	 * @throws IOException
	 *             thrown in case of socket errors.
	 * @throws UnknownErrorException
	 *             thrown in case of unforeseen errors.
	 * @throws InvalidParameterTermException
	 *             thrown if the term is not one of aforementioned valid terms.
	 * @throws ServerErrorException
	 *             thrown if the server returns a code different from HTTP 200
	 *             OK.
	 * @throws ResourceNotAllowedException
	 *             thrown if the server does not support tags listing.
	 * @throws VersionNotAvailableException
	 *             thrown if a previously set version is not available.
	 */
	public ListTag getTags(ParameterList list) throws IOException,
			UnknownErrorException, ServerErrorException,
			InvalidParameterTermException, ResourceNotAllowedException,
			VersionNotAvailableException {
		verifyVersion();
		validateTerm((String) list.getWithTerm(ParameterTerms.LIST).getValue());
		try {
			String url = validateAndBuildUrl(ResourceTerms.FIND_TAGS, list);
			parser.setJson(Request.getResponse(url));
			return parser.parseJsonAsTags();
		} catch (InvalidParameterException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Returns the complete description of a given POI.
	 * 
	 * @param poiBase
	 *            the base URL of the POI.
	 * @param poiId
	 *            the ID of the POI.
	 * @return a {@link citysdk.tourism.client.poi.single.PointOfInterest}
	 *         containing a complete description of the POI.
	 * @throws IOException
	 *             thrown in case of socket errors.
	 * @throws UnknownErrorException
	 *             thrown in case of unforeseen errors.
	 * @throws ServerErrorException
	 *             thrown if the server returns a code different from HTTP 200
	 *             OK.
	 */
	public PointOfInterest getPoi(String poiBase, String poiId)
			throws IOException, UnknownErrorException, ServerErrorException {
		String url = poiBase + poiId;
		parser.setJson(Request.getResponse(url));
		return parser.parseJsonAsPointOfInterest();
	}

	/**
	 * Returns the description of a given Event.
	 * 
	 * @param eventBase
	 *            the base URL of the Event.
	 * @param eventId
	 *            the ID of the Event.
	 * @return an {@link citysdk.tourism.client.poi.single.Event} containing a
	 *         description of the Event.
	 * @throws IOException
	 *             thrown in case of socket errors.
	 * @throws UnknownErrorException
	 *             thrown in case of unforeseen errors.
	 * @throws ServerErrorException
	 *             thrown if the server returns a code different from HTTP 200
	 *             OK.
	 */
	public Event getEvent(String eventBase, String eventId) throws IOException,
			UnknownErrorException, ServerErrorException {
		String url = eventBase + eventId;
		parser.setJson(Request.getResponse(url));
		return parser.parseJsonAsEvent();
	}

	/**
	 * Returns the description of a given Route and its POIs.
	 * 
	 * @param routeBase
	 *            the base URL of the Route.
	 * @param routeId
	 *            the ID of the Route.
	 * @return a {@link citysdk.tourism.client.poi.single.Route} containing a
	 *         description of the Route.
	 * @throws IOException
	 *             thrown in case of socket errors.
	 * @throws UnknownErrorException
	 *             thrown in case of unforeseen errors.
	 * @throws ServerErrorException
	 *             thrown if the server returns a code different from HTTP 200
	 *             OK.
	 */
	public Route getRoute(String routeBase, String routeId) throws IOException,
			UnknownErrorException, ServerErrorException {
		String url = routeBase + routeId;
		parser.setJson(Request.getResponse(url));
		return parser.parseJsonAsRoute();
	}

	/**
	 * Returns a generic object matching the given base and id
	 * 
	 * @param base
	 *            the base URI of the POI object
	 * @param id
	 *            the id of the POI object
	 * @return a generic POI object
	 * @throws IOException
	 *             thrown in case of socket errors.
	 * @throws UnknownErrorException
	 *             thrown in case of unforeseen errors.
	 * @throws ServerErrorException
	 *             thrown if the server returns a code different from HTTP 200
	 *             OK.
	 */
	public POI getGeneric(String base, String id) throws IOException,
			UnknownErrorException, ServerErrorException {
		String url = base + id;
		parser.setJson(Request.getResponse(url));
		return (POI) parser.parseJsonAsGeneric();
	}

	/**
	 * Returns a list of Points of Interest with the given relation with the POI
	 * identified by id. The relation should be either:
	 * <ul>
	 * <li>{@link citysdk.tourism.client.terms.Term#LINK_TERM_PARENT};</li>
	 * <li>{@link citysdk.tourism.client.terms.Term#LINK_TERM_CHILD}.</li>
	 * </ul>
	 * 
	 * @param id
	 *            the id of the poi to find the relations
	 * @param relation
	 *            the wanted relation with the poi
	 * @return a {@link citysdk.tourism.client.poi.lists.ListPointOfInterest}
	 * @throws IOException
	 *             thrown in case of socket errors.
	 * @throws ServerErrorException
	 *             thrown if the server returns a code different from HTTP 200
	 *             OK.
	 * @throws UnknownErrorException
	 *             thrown in case of unforeseen errors.
	 * @throws VersionNotAvailableException
	 *             thrown if a previously set version is not available
	 * @throws InvalidValueException
	 *             thrown if the relation is not one of the presented values
	 * @throws ResourceNotAllowedException
	 *             thrown if the server does not support POIs relationships
	 */
	public ListPointOfInterest getPoiRelation(String id, Term relation)
			throws VersionNotAvailableException, InvalidValueException,
			ResourceNotAllowedException, IOException, ServerErrorException,
			UnknownErrorException {
		verifyVersion();
		validateRelation(relation);
		try {
			ParameterList list = new ParameterList();
			list.add(new Parameter(ParameterTerms.ID, id));
			list.add(new Parameter(ParameterTerms.RELATION, relation.getTerm()));
			String url = validateAndBuildUrl(ResourceTerms.FIND_POI_RELATION,
					list);
			parser.setJson(Request.getResponse(url));
			return parser.parseJsonAsListOfPois();
		} catch (InvalidParameterException e) {
			e.printStackTrace();
		} catch (InvalidValueException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Returns a list of Events with the given relation with the POI identified
	 * by id. The relation should be either:
	 * <ul>
	 * <li>{@link citysdk.tourism.client.terms.Term#LINK_TERM_PARENT};</li>
	 * <li>{@link citysdk.tourism.client.terms.Term#LINK_TERM_CHILD}.</li>
	 * </ul>
	 * 
	 * @param id
	 *            the id of the event to find the relations
	 * @param relation
	 *            the wanted relation with the related event
	 * @return a {@link citysdk.tourism.client.poi.lists.ListEvent}
	 * @throws IOException
	 *             thrown in case of socket errors.
	 * @throws ServerErrorException
	 *             thrown if the server returns a code different from HTTP 200
	 *             OK.
	 * @throws UnknownErrorException
	 *             thrown in case of unforeseen errors.
	 * @throws VersionNotAvailableException
	 *             thrown if a previously set version is not available
	 * @throws InvalidValueException
	 *             thrown if the relation is not one of the presented values
	 * @throws ResourceNotAllowedException
	 *             thrown if the server does not support event relations
	 */
	public ListEvent getEventRelation(String id, Term relation)
			throws InvalidParameterTermException, IOException,
			ServerErrorException, VersionNotAvailableException,
			UnknownErrorException, InvalidValueException,
			ResourceNotAllowedException {
		verifyVersion();
		validateRelation(relation);
		ParameterList list = new ParameterList();
		try {
			list.add(new Parameter(ParameterTerms.ID, id));
			list.add(new Parameter(ParameterTerms.RELATION, relation.getTerm()));
			String url = validateAndBuildUrl(ResourceTerms.FIND_EVENT_RELATION,
					list);
			parser.setJson(Request.getResponse(url));
			return parser.parseJsonAsListOfEvents();
		} catch (InvalidParameterException e) {
			e.printStackTrace();
		} catch (InvalidValueException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Gets all POI-based objects containing the given code
	 * 
	 * @param code
	 *            a link to search for POI-based objects
	 * @return all POI-based objects matching the code
	 * @throws ResourceNotAllowedException
	 *             thrown if search by code is not available
	 * @throws VersionNotAvailableException
	 *             thrown if the given version is not available
	 * @throws IOException
	 *             thrown in case of socket errors
	 * @throws ServerErrorException
	 *             thrown in case of a server error (returning code other than
	 *             HTTP 200 OK)
	 * @throws UnknownErrorException
	 *             thrown in case of unforseen errors
	 */
	public ListPOIS getByCode(String code) throws ResourceNotAllowedException,
			VersionNotAvailableException, IOException, ServerErrorException,
			UnknownErrorException {
		ParameterList list = new ParameterList();
		try {
			list.add(new Parameter(ParameterTerms.CODE, code));
			String url = validateAndBuildUrl(ResourceTerms.FIND_CODE, list);
			parser.setJson(Request.getResponse(url));
			return parser.parseJsonAsListPOIS();
		} catch (InvalidParameterException e) {
			e.printStackTrace();
		} catch (InvalidValueException e) {
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * Validates the term by verifying if it is POIS, EVENTS or ROUTES
	 */
	private void validateTerm(String term) throws InvalidParameterTermException {
		if (term == null)
			throw new InvalidParameterTermException(
					"There should be a list with the value set to POIS, EVENTS or ROUTES");

		if (!term.equals(ParameterTerms.POIS.getTerm())
				&& !term.equals(ParameterTerms.EVENTS.getTerm())
				&& !term.equals(ParameterTerms.ROUTES.getTerm()))
			throw new InvalidParameterTermException(
					"List term must be either POIS, EVENTS or ROUTES");
	}

	/*
	 * Validates a given resource term
	 */
	private void validateResource(ResourceTerms resource)
			throws InvalidResourceTermException {
		if (resource == null)
			throw new InvalidResourceTermException(
					"Resource must be either RESOURCE_POIS, RESOURCE_EVENTS or RESOURCE_ROUTES");

		if (!resource.equalsTerm(ResourceTerms.RESOURCE_POIS.getTerm())
				&& !resource
						.equalsTerm(ResourceTerms.RESOURCE_EVENTS.getTerm())
				&& !resource
						.equalsTerm(ResourceTerms.RESOURCE_ROUTES.getTerm()))
			throw new InvalidResourceTermException(
					"Resource must be either RESOURCE_POIS, RESOURCE_EVENTS or RESOURCE_ROUTES");
	}

	/*
	 * Validates the relation by verifying the parenthood
	 */
	private void validateRelation(Term relation) throws InvalidValueException {
		if (relation == null)
			throw new InvalidValueException(
					"Relation should be either LINK_TERM_PARENT or LINK_TERM_CHILD");

		if (!relation.equalsTerm(Term.LINK_TERM_PARENT)
				&& !relation.equalsTerm(Term.LINK_TERM_CHILD))
			throw new InvalidValueException(
					"Relation should be either LINK_TERM_PARENT or LINK_TERM_CHILD");
	}

	/*
	 * Verifies if this client has a given API version
	 */
	private void verifyVersion() throws VersionNotAvailableException {
		if (!resources.hasVersion(version))
			throw new VersionNotAvailableException(version
					+ " is not available");
	}

	/*
	 * Validates the query list and builds the desired URL.
	 */
	private String validateAndBuildUrl(ResourceTerms resource,
			ParameterList parameterList) throws InvalidParameterException,
			ResourceNotAllowedException, VersionNotAvailableException {
		verifyVersion();
		verifyResource(resource);
		validateParameters(parameterList, resource);
		return buildQueryUrl(resource, parameterList,
				resources.getHypermediaWithVersion(version));
	}

	/*
	 * Checks the parameter availability in the server.
	 */
	private void verifyResource(ResourceTerms resource)
			throws ResourceNotAllowedException {
		if (!resources.hasResource(version, resource)) {
			throw new ResourceNotAllowedException(resource.getTerm()
					+ " is not allowed for this server");
		}
	}

	/*
	 * Validates the parameters with a given validator
	 */
	private void validateParameters(ParameterList parameterList,
			ResourceTerms resource) throws InvalidParameterException {
		if (parameterList == null)
			return;

		Hypermedia media = resources.getHypermediaWithVersion(version)
				.getLinks().get(resource.getTerm());
		if (media.isTemplated()) {
			for (int i = 0; i < parameterList.size(); i++) {
				Parameter p = parameterList.get(i);
				if (!UriTemplate.fromTemplate(media.getHref()).hasParameter(
						p.getTerm())) {
					throw new InvalidParameterException(p.getTerm()
							+ " is not a valid parameter");
				}
			}
		}
	}

	/*
	 * Given a query list, it will create the basic URL for the HTTP request,
	 * following the desired query.
	 */
	private String buildQueryUrl(ResourceTerms resource,
			ParameterList parameterList, HypermediaLink links) {
		String url = null;

		if (parameterList == null)
			return null;

		Map<String, Hypermedia> hypermedia = links.getLinks();
		Hypermedia media = hypermedia.get(resource.getTerm());
		if (media.isTemplated()) {
			UriTemplate template = UriTemplate.fromTemplate(media.getHref());
			for (int i = 0; i < parameterList.size(); i++) {
				Parameter parameter = parameterList.get(i);
				template.set(parameter.getTerm(), parameter.getValue());
			}

			url = template.build();
		} else {
			url = media.getHref();
		}

		return url;
	}
}
