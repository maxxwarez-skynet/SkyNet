package in.co.maxxwarez.skynet.ui.commons;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.co.maxxwarez.skynet.Home;
import in.co.maxxwarez.skynet.MainActivity;
import in.co.maxxwarez.skynet.R;
import in.co.maxxwarez.skynet.ui.fragments.MapsFragment;
import in.co.maxxwarez.skynet.ui.fragments.SetHomeDetail;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetUpButton#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetUpButton extends Fragment implements View.OnClickListener {
    private static final String TAG = "SkyNet";
    public Button mbutton;
    public String flag = "one";
    public String homeName;
    public int selected = 0;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public SetUpButton () {
        // Required empty public constructor
    }

    public static SetUpButton newInstance () {
        SetUpButton fragment = new SetUpButton();
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_up_button, container, false);
        mbutton = view.findViewById(R.id.set_up_button);
        Bundle b = getArguments();
        if (b != null) {
            String t = b.getString("text");
            mbutton.setText(t);
        }
        if (b.get("flag") != null) {
            flag = (String) b.get("flag");
            Log.i(TAG, "no  flag" + b.get("flag"));
        }
        if (b.get("text") != null) {
            Log.i(TAG, "The Key");
        }

        mbutton.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick (View v) {
        Log.i(TAG, "SetUpButton On Click ");
        clicked();
    }

    private void clicked () {
        Log.i(TAG, "clicked " + flag);
        if(flag == "one"){
            SetHomeDetail setHomeDetail = SetHomeDetail.newInstance();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.add(R.id.home_details_list, setHomeDetail).commit();
        }
        if(flag == "two") {
            Log.i(TAG, "clicked two " + flag);
            MapsFragment mapsFragment = new MapsFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.add(R.id.home_details_list, mapsFragment).commit();
            FragmentManager fm = getParentFragmentManager();
            fm.findFragmentById(R.id.homeList);
            NoSetUp noSetUp = (NoSetUp) fm.findFragmentById(R.id.homeList);
            noSetUp.mTextView.setText("Set Location for your home");
            FragmentManager fragmentManager1 = getParentFragmentManager();
            SetUpButton setUpButton = (SetUpButton) fragmentManager1.findFragmentById(R.id.home_settings_list);
            setUpButton.mbutton.setText("Press the Locate Me button to get your location");

        }
        if (flag == "three") {
            Log.i(TAG, "clicked three " + flag);
            updateFirebase Fbase = new updateFirebase();
            Fbase.execute();
        }

        if (flag == "four") {
            Log.i(TAG, "clicked four " + flag);
        }
    }

    public void remoteEvent (String next){
            Log.i(TAG, "Remote Event" + next);
            mbutton.setText(next);

    }

    class updateFirebase extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            String userID  = user.getUid();
            Home home = new Home(homeName, userID, 0);
            ref.child("homes").push().setValue(home);
            // String homeID = ref.child("homes").push().getKey();
            // ref.child("users").child(user.getUid()).child("homes").child(homeID).setValue(homeName);
            // ref.child("homes").child(homeID).child("name").setValue(homeName);
            // ref.child("homes").child(homeID).child("userID").setValue(userID);
            //ToDo: Add query to check and update order.:Can be handled in functions
            // ref.child("homes").child(homeID).child("order").setValue("");
            return null;
        }

        @Override
        protected void onPostExecute (String result) {
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);
        }
    }

    class updateFirebase2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground (String... strings) {

            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            String userID = user.getUid();
            Home home = new Home(homeName, userID, 0);
            ref.child("homes").push().setValue(home);
            // String homeID = ref.child("homes").push().getKey();
            // ref.child("users").child(user.getUid()).child("homes").child(homeID).setValue(homeName);
            // ref.child("homes").child(homeID).child("name").setValue(homeName);
            // ref.child("homes").child(homeID).child("userID").setValue(userID);
            //ToDo: Add query to check and update order.:Can be handled in functions
            // ref.child("homes").child(homeID).child("order").setValue("");
            return null;
        }

        @Override
        protected void onPostExecute (String result) {
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);
        }
    }

}
