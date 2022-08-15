package in.co.maxxwarez.skynet.helperClasses;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class GlobalVars extends Application {

    private static final String TAG = "SkyNet";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public GlobalVars() {

    }

    public AsyncTask<String, Void, String> getUserObject() {
        getHome getHome = new getHome();
        userObject = getHome.execute();
        Log.i(TAG, "Home Helper GlobalVars Main " + userObject.toString());
        return userObject;
    }

    public GlobalVars(AsyncTask<String, Void, String> userObject) {
        this.userObject = userObject;
    }

    private AsyncTask<String, Void, String> userObject;


    private class getHome extends AsyncTask<String, Void, String> {
        public String key;

        @Override
        protected String doInBackground (String... strings) {

            Query query = ref.child("users").child(user.getUid()).child("homes");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot home : dataSnapshot.getChildren()) {

                            key = home.getKey();
                            Log.i(TAG, "Home Helper GlobalVars Task " + key);
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            Log.i(TAG, "Home Helper GlobalVars Return " + key);
            return key;
        }

        @Override
        protected void onPostExecute (String result) {
            Log.i(TAG, "Home Helper GlobalVars Result " + key + " " + result);
        }
    }
}
