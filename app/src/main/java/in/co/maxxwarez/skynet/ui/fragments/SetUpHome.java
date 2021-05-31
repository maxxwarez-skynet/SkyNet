package in.co.maxxwarez.skynet.ui.fragments;

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

import in.co.maxxwarez.skynet.MainActivity;
import in.co.maxxwarez.skynet.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetUpHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetUpHome extends Fragment implements View.OnClickListener {
    private static final String TAG = "SkyNet";
    Button mbutton;
    public  String flag = "one";
    public String homeName;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public SetUpHome () {
        // Required empty public constructor
    }

    public static SetUpHome newInstance () {
        SetUpHome fragment = new SetUpHome();
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
        View view = inflater.inflate(R.layout.fragment_set_up_home, container, false);
        mbutton  = view.findViewById(R.id.setHome);
        mbutton.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick (View v) {
        Log.i(TAG, "SetUpHome On Click ");
        clicked();
    }

    private void clicked () {
        Log.i(TAG, "clicked " + flag);
        if(flag == "one"){
            SetHomeDetail setHomeDetail = SetHomeDetail.newInstance();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.add(R.id.detailsList, setHomeDetail).commit();
        }
        if(flag == "two") {
            Log.i(TAG, "clicked two " + flag);
            MapsFragment mapsFragment = new MapsFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.add(R.id.detailsList, mapsFragment).commit();
            FragmentManager fm = getParentFragmentManager();
            fm.findFragmentById(R.id.homeList);
            NoHomeSet noHomeSet = (NoHomeSet) fm.findFragmentById(R.id.homeList);
            noHomeSet.mTextView.setText("Set Location for your home");
            FragmentManager fragmentManager1 = getParentFragmentManager();
            SetUpHome setUpHome = (SetUpHome) fragmentManager1.findFragmentById(R.id.settingsList);
            setUpHome.mbutton.setText("Press the Locate Me button to get your location");

        }
        if(flag == "three"){
            Log.i(TAG, "clicked three " + flag);
            updateFirebase Fbase = new updateFirebase();
            Fbase.execute();
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
            String homeID = ref.child("home").push().getKey();
            ref.child("users").child(user.getUid()).child("home").child(homeID).setValue(homeName);
            ref.child("homes").child(homeID).setValue(homeName);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);
        }
    }

}
