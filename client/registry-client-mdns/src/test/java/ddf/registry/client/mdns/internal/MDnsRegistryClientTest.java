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
package ddf.registry.client.mdns.internal;

import ddf.registry.client.rest.internal.RestRegistryClient;
import org.junit.Test;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import java.net.InetAddress;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MDnsRegistryClientTest {

    /**
     * Tests that the rest client is properly called when a new service is resolved.
     *
     * @throws Exception
     */
    @Test
    public void testServiceResolved() throws Exception {
        InetAddress address = InetAddress.getLocalHost();
        int port = 8181;
        String context = "/example";
        String fullAddress = "http:/" + address + ":" + port + context;

        JmDNS jmDNS = mock(JmDNS.class);
        RestRegistryClient restRegistryClient = mock(RestRegistryClient.class);
        MDnsRegistryClient registryClient = new MDnsRegistryClient(jmDNS, restRegistryClient);
        ServiceEvent event = mock(ServiceEvent.class);
        ServiceInfo info = mock(ServiceInfo.class);
        when(event.getInfo()).thenReturn(info);
        when(info.getPropertyString("lastUpdated"))
                .thenReturn(Long.toString(System.currentTimeMillis()));
        when(info.getApplication()).thenReturn("http");
        when(info.getInetAddresses()).thenReturn(new InetAddress[] {address});
        when(info.getPort()).thenReturn(8181);
        when(info.getPropertyString("context")).thenReturn(context);
        registryClient.serviceResolved(event);
        verify(restRegistryClient).setRegistryUrl(eq(fullAddress));
    }

    /**
     * Tests that the rest client is called with a null paramater when a service is removed.
     */
    @Test
    public void testServiceRemoved() {
        JmDNS jmDNS = mock(JmDNS.class);
        RestRegistryClient restRegistryClient = mock(RestRegistryClient.class);
        MDnsRegistryClient registryClient = new MDnsRegistryClient(jmDNS, restRegistryClient);
        ServiceEvent event = mock(ServiceEvent.class);
        registryClient.serviceRemoved(event);
        verify(restRegistryClient).setRegistryUrl(isNull(String.class));
    }

}
