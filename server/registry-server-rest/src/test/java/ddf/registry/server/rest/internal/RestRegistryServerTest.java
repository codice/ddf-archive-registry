/**
 * Copyright (c) Codice Foundation
 *
 * This is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details. A copy of the GNU Lesser General Public License
 * is distributed along with this program and can be found at
 * <http://www.gnu.org/licenses/lgpl.html>.
 *
 **/
package ddf.registry.server.rest.internal;

import ddf.registry.service.ServiceInfo;
import ddf.registry.service.ServiceRegistry;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests out the restful registry server
 */
public class RestRegistryServerTest {

    private static final String TYPE_1 = "Type 1";

    private static final String URL_1 = "/url1";

    private static final String DESCRIPTION_1 = "Description 1";

    private static final String TYPE_2 = "Type 2";

    private static final String URL_2 = "/url2";

    private static final String DESCRIPTION_2 = "Description 2";

    private static final String LOCAL_URL = "http://localhost:8181";

    /**
     * Tests that the server returns correct values back in JSON format.
     */
    @Test
    public void testGetServices() {
        ServiceRegistry serviceRegistry = createRegistry();
        RestRegistryServer registryServer = new RestRegistryServer(serviceRegistry);
        UriInfo uriInfo = mock(UriInfo.class);
        URI baseUri = URI.create(LOCAL_URL);
        when(uriInfo.getBaseUri()).thenReturn(baseUri);
        Response response = registryServer.getServices(uriInfo);
        Object entity = response.getEntity();
        assertNotNull(entity);
        JSONArray serviceArray = new JSONArray(entity.toString());
        assertEquals(2, serviceArray.length());
        JSONObject service1 = serviceArray.getJSONObject(0);
        assertEquals(TYPE_1, service1.get("type"));
        assertEquals(DESCRIPTION_1, service1.get("description"));
        assertEquals(LOCAL_URL + URL_1, service1.get("url"));
    }

    /**
     * Verify that the ping returns a proper response.
     */
    @Test
    public void testPing() {
        ServiceRegistry serviceRegistry = mock(ServiceRegistry.class);
        RestRegistryServer registryServer = new RestRegistryServer(serviceRegistry);
        Response response = registryServer.ping();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    private ServiceRegistry createRegistry() {
        ServiceRegistry serviceRegistry = mock(ServiceRegistry.class);
        ServiceInfo serviceInfo1 = new MockServiceInfo(TYPE_1, URL_1, DESCRIPTION_1,
                Collections.<String, String>emptyMap());
        ServiceInfo serviceInfo2 = new MockServiceInfo(TYPE_2, URL_2, DESCRIPTION_2,
                Collections.<String, String>emptyMap());
        when(serviceRegistry.getServices()).thenReturn(Arrays.asList(serviceInfo1, serviceInfo2));

        return serviceRegistry;
    }

    private class MockServiceInfo implements ServiceInfo {

        String type;

        String url;

        String description;

        Map<String, String> properties;

        public MockServiceInfo(String type, String url, String description,
                Map<String, String> properties) {
            this.type = type;
            this.url = url;
            this.description = description;
            this.properties = properties;
        }

        @Override public String getServiceType() {
            return type;
        }

        @Override public String getServiceRelativeUrl() {
            return url;
        }

        @Override public String getServiceDescription() {
            return description;
        }

        @Override public Map<String, String> getProperties() {
            return properties;
        }
    }

}
