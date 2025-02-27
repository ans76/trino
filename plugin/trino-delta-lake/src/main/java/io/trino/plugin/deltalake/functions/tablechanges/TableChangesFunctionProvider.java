/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.plugin.deltalake.functions.tablechanges;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.trino.plugin.base.classloader.ClassLoaderSafeConnectorTableFunction;
import io.trino.plugin.deltalake.DeltaLakeMetadataFactory;
import io.trino.spi.ptf.ConnectorTableFunction;

import static java.util.Objects.requireNonNull;

public class TableChangesFunctionProvider
        implements Provider<ConnectorTableFunction>
{
    private final DeltaLakeMetadataFactory deltaLakeMetadataFactory;

    @Inject
    public TableChangesFunctionProvider(DeltaLakeMetadataFactory deltaLakeMetadataFactory)
    {
        this.deltaLakeMetadataFactory = requireNonNull(deltaLakeMetadataFactory, "deltaLakeMetadataFactory is null");
    }

    @Override
    public ConnectorTableFunction get()
    {
        return new ClassLoaderSafeConnectorTableFunction(
                new TableChangesFunction(deltaLakeMetadataFactory),
                getClass().getClassLoader());
    }
}
