package io.divide.client.debug.auth;

import com.google.inject.Inject;
import io.divide.client.Config;
import io.divide.client.auth.AccountStorage;
import io.divide.client.auth.AuthManager;
import io.divide.client.auth.AuthWebService;
import io.divide.shared.server.DAO;
import io.divide.shared.web.transitory.TransientObject;

import java.security.NoSuchAlgorithmException;

/**
 * Created by williamwebb on 4/6/14.
 */
public class MockAuthManager extends AuthManager {

    @Inject MockAuthWebServer mockAuthWebService;
    @Inject DAO<TransientObject,TransientObject> db;

    @Inject
    public MockAuthManager(Config config, AccountStorage storage) throws NoSuchAlgorithmException {
        super(config,storage);

//        mockAuthWebService = new MockAuthWebServer(this, db);
    }


    @Override
    public AuthWebService getWebService(){
        return mockAuthWebService;
    }

    @Override
    public void initAdapter(Config config){}

}
