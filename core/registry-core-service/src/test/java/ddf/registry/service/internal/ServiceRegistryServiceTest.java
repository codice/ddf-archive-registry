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
package ddf.registry.service.internal;

import ddf.registry.api.RegistrableService;
import ddf.registry.service.ServiceInfo;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests out the ServiceRegistryService class.
 */
public class ServiceRegistryServiceTest {

    private static final String TYPE_1 = "Type 1";

    private static final String URL_1 = "http://example.com/url1";

    private static final String DESCRIPTION_1 = "Description 1";

    private static final String TYPE_2 = "Type 2";

    private static final String URL_2 = "http://example.com/url2";

    private static final String DESCRIPTION_2 = "Description 2";

    @Test
    public void testGetServices() {
        RegistrableService service1 = getService(TYPE_1, URL_1, DESCRIPTION_1);
        RegistrableService service2 = getService(TYPE_2, URL_2, DESCRIPTION_2);
        List<RegistrableService> serviceList = Arrays.asList(service1, service2);

        ServiceRegistryService service = new ServiceRegistryService(serviceList);
        List<ServiceInfo> serviceInfoList = service.getServices();
        assertEquals(2, serviceInfoList.size());
        ServiceInfo serviceInfo1 = serviceInfoList.get(0);
        assertEquals(TYPE_1, serviceInfo1.getServiceType());
        assertEquals(URL_1, serviceInfo1.getServiceRelativeUrl());
        assertEquals(DESCRIPTION_1, serviceInfo1.getServiceDescription());

        ServiceInfo serviceInfo2 = serviceInfoList.get(1);
        assertEquals(TYPE_2, serviceInfo2.getServiceType());
        assertEquals(URL_2, serviceInfo2.getServiceRelativeUrl());
        assertEquals(DESCRIPTION_2, serviceInfo2.getServiceDescription());
    }

    /**
     * Checks that with no registrable services an empty list of service infos is returned.
     */
    @Test
    public void testGetEmptyServices() {
        ServiceRegistryService service = new ServiceRegistryService(
                Collections.<RegistrableService>emptyList());
        List<ServiceInfo> serviceInfoList = service.getServices();
        assertTrue(serviceInfoList.isEmpty());
    }

    private RegistrableService getService(String type, String url, String description) {
        RegistrableService service = mock(RegistrableService.class);
        when(service.getServiceType()).thenReturn(type);
        when(service.getServiceRelativeUrl()).thenReturn(url);
        when(service.getServiceDescription()).thenReturn(description);
        when(service.getProperties()).thenReturn(Collections.<String, String>emptyMap());
        return service;
    }
}
