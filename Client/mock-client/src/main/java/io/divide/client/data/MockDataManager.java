package io.divide.client.data;

import com.google.inject.Inject;
import io.divide.client.Config;

/**
 * Created by williamwebb on 4/6/14.
 */
public class MockDataManager extends DataManager {

    @Inject MockDataWebService mockDataWebService;

    @Inject
    public MockDataManager(Config config) {
        super(config);
    }

    @Override
    public DataWebService getWebService(){
        return mockDataWebService;
    }

    @Override
    public void initAdapter(Config config){};
}
