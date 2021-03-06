/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.wildfly.naming.client.remote;

import java.net.URI;

import javax.naming.Context;

import org.jboss.remoting3.Endpoint;
import org.wildfly.common.selector.Selector;
import org.wildfly.naming.client.NamingProvider;
import org.wildfly.naming.client.util.FastHashtable;

/**
 * A naming provider supporting JBoss Remoting-based transport.
 *
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public final class RemoteNamingProvider implements NamingProvider {

    static final Selector.Getter<Endpoint> ENDPOINT_GETTER = Selector.selectorGetterFor(Endpoint.class);

    /**
     * Construct a new instance.
     */
    public RemoteNamingProvider() {
    }

    public boolean supportsUriScheme(final String providerScheme, final String nameScheme) {
        final Endpoint endpoint = ENDPOINT_GETTER.getSelector().get();
        return endpoint != null && endpoint.isValidUriScheme(providerScheme) && nameScheme == null;
    }

    public Context createRootContext(final String nameScheme, final URI providerUri, final FastHashtable<String, Object> env) {
        return new RemoteContext(nameScheme, providerUri, env);
    }
}
