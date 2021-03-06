package io.divide.client.data;

import com.google.inject.Inject;
import io.divide.client.BackendObject;
import io.divide.client.auth.AuthManager;
import io.divide.client.GsonResponse;
import io.divide.shared.logging.Logger;
import io.divide.shared.server.DAO;
import io.divide.shared.transitory.TransientObject;
import io.divide.shared.transitory.query.Query;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.EncodedPath;
import retrofit.http.Header;
import rx.Observable;
import rx.Subscriber;

import java.util.Collection;

/**
 * Created by williamwebb on 4/6/14.
 */
public class MockDataWebService implements DataWebService {
    private static Logger logger = Logger.getLogger(MockDataWebService.class);

    @Inject private AuthManager authManager;
    @Inject private DAO<TransientObject,TransientObject> dao;

    @Override
    public Response get(@Header("Authorization") String authToken, @EncodedPath("objectType") String objectType, @Body Collection<String> keys) {
        verifyAuthToken(authToken);
        try {
            Collection<TransientObject> got = dao.get(objectType, keys.toArray(new String[keys.size()]));
            return new GsonResponse("",200,"",null, got).build();
        } catch (DAO.DAOException e) {
            return new GsonResponse("",e.getStatusCode(),e.getMessage(), null, null).build();
        }
    }

    @Override
    public Response query(@Header("Authorization") String authToken, @Body Query query) {
        verifyAuthToken(authToken);
        try {
            Collection<TransientObject> got = dao.query(query);
            return new GsonResponse("",200,"",null, got).build();
        } catch (DAO.DAOException e) {
            return new GsonResponse("",e.getStatusCode(),e.getMessage(), null, null).build();
        }    }

    @Override
    public <B extends BackendObject> Observable<Void> save(@Header("Authorization") String authToken, final @Body Collection<B> objects) {
        verifyAuthToken(authToken);
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    dao.save((B[]) objects.toArray());
                    subscriber.onNext(null);
                } catch (DAO.DAOException e) {
                    subscriber.onError(e);
                };
            }
        });
    }

    @Override
    public Observable<Integer> count(@Header("Authorization") String authToken, @EncodedPath("objectType") String objectType) {
        verifyAuthToken(authToken);
        return Observable.from(dao.count(objectType));
    }

    public void verifyAuthToken(String token){
        token = token.replace("CUSTOM ", "");

        if(token == null ||
            token.length() == 0 || //TODO token should be fixed length, verify against this also
            authManager == null ||
            authManager.getUser() == null ||
            authManager.getUser().getAuthToken() == null ||
            !authManager.getUser().getAuthToken().equals(token)){
            throw new RuntimeException("");
        }
    }
}
