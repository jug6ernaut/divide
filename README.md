Backend
===========

Open Source Parse Alternative. Providing a background/database agnostic server and android client.

#### Server

```java
public class SomeApplication extends AuthApplication<OrientDBDao> {

    public SomeApplication() {
    	// dao class
        super(OrientDBDao.class, "someKeyForSynchronousEncryption");
    }
    
    public SomeApplication() {
        // dao instance
        super(new OrientDBDao(), "someKeyForSynchronousEncryption");
    }
}
```

#### Client(android)

```java
public class MyApplication extends Application {

    @Override
    public void onCreate(){
        Backend.init(this,"http://authenticator-test.appspot.com/api/");
    }
}
```

#### Create and Save Object

```java
BackendObject object = new BackendObject();

// add values, will be serialized with GSON
object.put("somePrimative1",1);          // int
object.put("somePrimative2",1L);         // long
object.put("somePrimative3","whatwhat"); // String
object.put("someObject",new Object());   // Some POJO...ect

// store remotely async
BackendServices
	.remote()
	.save(object)
	.subscribe();
        
// store locally async
BackendServices
	.local()
	.save(object)
	.subscribe();
```


#### Perform Query

```java
// create Query
Query query = new QueryBuilder()
    .select()
    .from(BackendObject.class)
    .limit(10).build();

// run query against remote server
BackendServices.remote()
    .query(BackendObject.class, query)
    .subscribe(new Action1<Collection<BackendObject>>() {
           @Override
           public void call(Collection<BackendObject> objects) {
 	     		// do something with objects
           }
     });
```
