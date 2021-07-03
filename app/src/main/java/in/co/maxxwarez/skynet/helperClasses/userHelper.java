package in.co.maxxwarez.skynet.helperClasses;

import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static in.co.maxxwarez.skynet.R.string.signed_in_name;

public class userHelper {
    private static final String TAG = "SkyNet";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public userHelper () {
    }

    public String getUID () {
        return user.getUid();
    }

    public String getEmailID () {
        return user.getEmail();
    }

    public String getDisplayName () {
        return user.getDisplayName();
    }

    public boolean isLoggedIn () {
        if (user != null)
            return true;
        else
            return false;
    }

    public void createUser () {
        Query query = ref.child("users").child(this.getUID());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    ref.child("users").child(user.getUid()).child("name").setValue(user.getDisplayName());
                    ref.child("users").child(user.getUid()).child("email").setValue(user.getEmail());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}