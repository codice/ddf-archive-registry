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
package ddf.registry.api;

/**
 * Allows dynamic services to register an association between their service type and factory identifier.
 * This association is needed when generating services using configuration admin.
 */
public interface DynamicServiceIdentifier {

    /**
     * Retrieves the factory identifier for this service.
     *
     * @return The factory PID that can be used to create new services instances.
     */
    String getFactoryIdentifier();

    /**
     * Retrieves the corresponding service type for this service.
     *
     * @return Service type that defines this service.
     */
    String getServiceType();

}
