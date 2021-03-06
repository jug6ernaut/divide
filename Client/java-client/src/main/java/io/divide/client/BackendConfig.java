package io.divide.client;

/**
 * Created by williamwebb on 4/4/14.
 */
public final class BackendConfig extends Config<Backend>{

    /**
     * Default @see Config implementation used by Divide. Used for the default implementation returning a @see Backend object.
     * @return
     */

    @Override
    public Class<Backend> getModuleType() {
        return Backend.class;
    }

    public BackendConfig(String fileSavePath, String url){
        this(fileSavePath, url, BackendModule.class);
    }

    public <ModuleType extends BackendModule> BackendConfig(String fileSavePath, String url, Class<ModuleType> moduleClass){
        super(fileSavePath,url,moduleClass);
    }
}
