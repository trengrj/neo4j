/**
 * Copyright (c) 2002-2014 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.server.web;

import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.neo4j.test.Mute;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.neo4j.test.Mute.muteAll;

public class JettyThreadCalculatorTest
{

    @Test
    public void shouldHaveCorrectAmountOfThreads()
    {

        JettyThreadCalculator jtc = new JettyThreadCalculator(1);
        assertEquals("Wrong acceptor value for 1 core", 1, jtc.getAcceptors());
        assertEquals("Wrong selector value for 1 core", 1, jtc.getSelectors());
        assertEquals("Wrong maxThreads value for 1 core", 8, jtc.getMaxThreads());
        assertEquals("Wrong minThreads value for 1 core", 2, jtc.getMinThreads());
        assertEquals("Wrong capacity value for 1 core", 480000, jtc.getMaxCapacity());

        jtc = new JettyThreadCalculator(4);
        assertEquals("Wrong acceptor value for 4 cores", 1, jtc.getAcceptors());
        assertEquals("Wrong selector value for 4 cores", 2, jtc.getSelectors());
        assertEquals("Wrong maxThreads value for 4 cores", 8, jtc.getMaxThreads());
        assertEquals("Wrong minThreads value for 4 cores", 2, jtc.getMinThreads());
        assertEquals("Wrong capacity value for 4 cores", 480000, jtc.getMaxCapacity());

        jtc = new JettyThreadCalculator(16);
        assertEquals("Wrong acceptor value for 16 cores", 2, jtc.getAcceptors());
        assertEquals("Wrong selector value for 16 cores", 3, jtc.getSelectors());
        assertEquals("Wrong maxThreads value for 16 cores", 11, jtc.getMaxThreads());
        assertEquals("Wrong minThreads value for 16 cores", 4, jtc.getMinThreads());
        assertEquals("Wrong capacity value for 16 cores", 660000, jtc.getMaxCapacity());

        jtc = new JettyThreadCalculator(64);
        assertEquals("Wrong acceptor value for 64 cores", 4, jtc.getAcceptors());
        assertEquals("Wrong selector value for 64 cores", 8, jtc.getSelectors());
        assertEquals("Wrong maxThreads value for 64 cores", 52, jtc.getMaxThreads());
        assertEquals("Wrong minThreads value for 64 cores", 12, jtc.getMinThreads());
        assertEquals("Wrong capacity value for 64 cores", 3120000, jtc.getMaxCapacity());


    }


}