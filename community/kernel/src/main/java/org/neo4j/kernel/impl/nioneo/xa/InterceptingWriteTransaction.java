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
package org.neo4j.kernel.impl.nioneo.xa;

import java.util.List;

import org.neo4j.kernel.api.labelscan.LabelScanStore;
import org.neo4j.kernel.impl.api.KernelTransactionImplementation;
import org.neo4j.kernel.impl.api.index.IndexingService;
import org.neo4j.kernel.impl.core.CacheAccessBackDoor;
import org.neo4j.kernel.impl.locking.LockService;
import org.neo4j.kernel.impl.nioneo.store.NeoStore;
import org.neo4j.kernel.impl.nioneo.xa.command.Command;
import org.neo4j.kernel.impl.transaction.xaframework.TransactionInterceptor;
import org.neo4j.kernel.impl.transaction.xaframework.XaLogicalLog;

public class InterceptingWriteTransaction extends NeoStoreTransaction
{
    private final TransactionInterceptor interceptor;

    InterceptingWriteTransaction( long lastCommittedTxWhenTransactionStarted, XaLogicalLog log,
                                  NeoStore neoStore, CacheAccessBackDoor cacheAccess,
                                  IndexingService indexingService, LabelScanStore labelScanStore,
                                  TransactionInterceptor interceptor, IntegrityValidator validator,
                                  KernelTransactionImplementation kernelTransaction, LockService locks,
                                  NeoStoreTransactionContext context )
    {
        super( lastCommittedTxWhenTransactionStarted, log, neoStore, cacheAccess, indexingService,
                labelScanStore, validator, kernelTransaction, locks, context );
        this.interceptor = interceptor;
    }

    @Override
    protected void intercept( List<Command> commands )
    {
        super.intercept( commands );
        for ( Command command : commands )
        {
            command.accept( interceptor );
        }
        interceptor.complete();
    }
}
