package in.co.maxxwarez.skynet;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Home {

    public String name;
    public String userID;
    public int order;

    public Home(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Home(String name, String userID, int order) {
        this.name = name;
        this.userID = userID;
        this.order = order;
    }

}