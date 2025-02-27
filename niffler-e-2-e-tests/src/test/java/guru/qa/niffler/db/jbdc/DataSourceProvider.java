package guru.qa.niffler.db.jbdc;

import com.p6spy.engine.spy.P6DataSource;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.db.ServiceDB;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum DataSourceProvider {
    INSTANCE;

    private static final Config cfg = Config.getInstance();

    private final Map<ServiceDB, DataSource> dataSourceStore = new ConcurrentHashMap<>();

    public DataSource getDataSource(ServiceDB db) {
        return dataSourceStore.computeIfAbsent(db, key -> {
            PGSimpleDataSource sd = new PGSimpleDataSource();
            sd.setURL(key.getUrl());
            sd.setUser(cfg.databaseUser());
            sd.setPassword(cfg.databasePassword());
            // By default
//            return sd;
            // If use P6Spy for logging
            P6DataSource ds =  new P6DataSource(sd);
            return ds;
        });
    }
}
