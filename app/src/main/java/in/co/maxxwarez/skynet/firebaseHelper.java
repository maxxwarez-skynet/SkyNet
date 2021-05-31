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

public class firebaseHelper {
    private static final String TAG = "SkyNet";
    private boolean hasHome;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public boolean hasHome(){

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

    class updateFirebase extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String homeID = ref.child("home").push().getKey();
            //ref.child("users").child(user.getUid()).child("home").child(homeID).setValue(homeValue);
            //  ref.child("users").child(currentUser.getUid()).child("home").child(homeID).child("ID").setValue(homeID);
            ref.child("homes").child(homeID).setValue("homeValue");
            return homeID;
        }
    }

    class getHome extends AsyncTask<String, Void, Boolean> {

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

    }

}