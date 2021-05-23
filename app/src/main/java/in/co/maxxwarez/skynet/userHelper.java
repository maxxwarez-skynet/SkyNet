package in.co.maxxwarez.skynet;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class userHelper {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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

    public  boolean isLoggedIn(){
        if (user != null)
            return true;
        else
            return false;
    }
}
