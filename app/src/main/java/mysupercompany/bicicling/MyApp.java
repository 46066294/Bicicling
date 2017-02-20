package mysupercompany.bicicling;

import android.app.Application;

import com.firebase.client.Firebase;


/**
 * Created by Mat on 08/02/2017.
 */

public class MyApp extends Application {

    private Firebase ref;

    public Firebase getRef() {
        return ref;
    }

    public void setRef(Firebase ref) {
        this.ref = ref;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        super.onCreate();
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);

        ref = new Firebase("https://todos-a80c7.firebaseio.com/");
    }
}