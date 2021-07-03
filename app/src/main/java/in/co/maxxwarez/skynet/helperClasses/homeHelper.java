package in.co.maxxwarez.skynet.helperClasses;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class homeHelper {

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

}