package in.co.maxxwarez.skynet;

import android.app.Application;

public class SkyNet extends Application {

public  class homeFrame{

    String state;
    String caller;
    public void setState (String state) {
        this.state = state;
    }

    public void setCaller (String caller) {
        this.caller = caller;
    }

    public String getState () {
        return state;
    }

    public String getCaller () {
        return caller;
    }
}

public class settingsFrame{

}

public class detailsFrame{

}
}
