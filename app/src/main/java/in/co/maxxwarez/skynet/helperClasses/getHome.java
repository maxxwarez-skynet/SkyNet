package in.co.maxxwarez.skynet.helperClasses;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public  class getHome extends AsyncTask<String, Void, String> {
    public String key;
    private static final String TAG = "SkyNet";

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    @Override
    protected String doInBackground (String... strings) {

        Query query = ref.child("users").child(user.getUid()).child("homes");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot home : dataSnapshot.getChildren()) {

                        key = home.getKey();
                        Log.i(TAG, "Home Helper " + key);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.i(TAG, "Home Helper Return" + key);
        return key;
    }

    @Override
    protected void onPostExecute (String result) {
        Log.i(TAG, "Home Helper Res " + key);
    }
}