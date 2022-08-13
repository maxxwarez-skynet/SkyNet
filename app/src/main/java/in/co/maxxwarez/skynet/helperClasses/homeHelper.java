package in.co.maxxwarez.skynet.helperClasses;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

@IgnoreExtraProperties
public class homeHelper {

    private static final String TAG = "SkyNet";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public String name;
    public String userID;
    public int order;

    public homeHelper () {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public homeHelper (String name, String userID, int order) {
        this.name = name;
        this.userID = userID;
        this.order = order;
    }

    public void addHome ( String homeID, String homeName) {
        Query query = ref.child("users").child(user.getUid());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ref.child("users").child(user.getUid()).child("homes").child(homeID).setValue(homeName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getHome ( ) {
        Query query = ref.child("users").child(user.getUid()).child("homes");


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}