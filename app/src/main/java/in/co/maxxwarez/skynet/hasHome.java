package in.co.maxxwarez.skynet;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class hasHome extends AsyncTask<String, Void, Boolean> {

    private static final String TAG = "SkyNet";
    private boolean hasHome;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected Boolean doInBackground(String... strings) {
        Query query = ref.child("users").child(user.getUid()).child("home");

        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Log.i(TAG, "Snapshot Exists" + dataSnapshot.getValue()) ;
                    hasHome = true;
                }
                else
                {
                    
                    hasHome = false;
                    Log.i(TAG, "Snapshot Doesnot Exists") ;

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return hasHome;
    }

    @Override
    protected void onPostExecute(Boolean result) {

    }



}
