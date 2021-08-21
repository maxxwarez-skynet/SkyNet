package in.co.maxxwarez.skynet.ui.commons;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.co.maxxwarez.skynet.helperClasses.homeHelper;
import in.co.maxxwarez.skynet.MainActivity;
import in.co.maxxwarez.skynet.R;
import in.co.maxxwarez.skynet.ui.devices.DeviceList;
import in.co.maxxwarez.skynet.ui.devices.DeviceSettingsList;
import in.co.maxxwarez.skynet.ui.devices.NewDeviceSetup;
import in.co.maxxwarez.skynet.ui.fragments.MapsFragment;
import in.co.maxxwarez.skynet.ui.home.SetHomeDetail;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetUpButton#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetUpButton extends Fragment implements View.OnClickListener {
    private static final String TAG = "SkyNet";
    private static String sID = "SkyNet-AutoConfig";
    public Button mbutton;
    public String flag = "one";
    public String homeName;
    public int selected = 0;
    public String mSSID;
    public String mChipID;

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

    //Handler handler = new Handler();
    Runnable checkSettingOn = new Runnable() {

        @Override
        public void run () {
            Log.i(TAG, "run: 2");
            if (isConnected()) {
                Log.i(TAG, "run: 3");
                mbutton.setText(mSSID);
                return;
            }
            //handler.postDelayed(this, 200);
        }
    };



    @Override
    public void onClick (View v) {
        Log.i(TAG, "SetUpButton On Click ");
        clicked();
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_up_button, container, false);
        mbutton = view.findViewById(R.id.set_up_button);
        Bundle b = getArguments();
        if (b != null) {
            if (b.get("flag") != null) {
                flag = b.getString("flag");
                Log.i(TAG, "no  flag" + b.get("flag"));
            }
            if (b.get("text") != null) {
                mbutton.setText(b.getString("text"));
                Log.i(TAG, "The Key");
            }

        }


        mbutton.setOnClickListener(this);
        return view;
    }

    public void remoteEvent (String next){
            Log.i(TAG, "Remote Event" + next);
            mbutton.setText(next);

    }

    private void clicked () {
        Log.i(TAG, "clicked " + flag);
        if (flag == "one") {
            SetHomeDetail setHomeDetail = SetHomeDetail.newInstance();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.add(R.id.home_details_list, setHomeDetail).commit();
        }
        if (flag == "two") {
            Log.i(TAG, "clicked two " + flag + " " + checkLocationPermission());
            if (checkLocationPermission()) {
                Log.i(TAG, "Inside checkLocationPermission True ");
                MapsFragment mapsFragment = new MapsFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.home_details_list, mapsFragment).commit();
                FragmentManager fm = getParentFragmentManager();
                fm.findFragmentById(R.id.homeList);
                NoSetUp noSetUp = (NoSetUp) fm.findFragmentById(R.id.homeList);
                noSetUp.mTextView.setText("Set Location for your home");
                FragmentManager fragmentManager1 = getParentFragmentManager();
                SetUpButton setUpButton = (SetUpButton) fragmentManager1.findFragmentById(R.id.home_settings_list);
                setUpButton.mbutton.setText("Press the Locate Me button to get your location");
            } else {
                Log.i(TAG, "Inside checkLocationPermission False ");
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        if (flag == "three") {
            Log.i(TAG, "clicked three " + flag);
            updateFirebase Fbase = new updateFirebase();
            Fbase.execute();
        }

        if (flag == "four") {
            Log.i(TAG, "clicked four " + flag);
            Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
            //panelIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(panelIntent);
            // handler.postDelayed(checkSettingOn, 1000);
        }

        if (flag == "fours") {
            NewDeviceSetup newDeviceSetup = new NewDeviceSetup();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.device_details_list, newDeviceSetup).commit();
        }

        if (flag == "five") {
            registerDevice registerDevice = new registerDevice();
            registerDevice.execute();
        }

    }

    @Override
    public void onResume () {

        super.onResume();
        Log.i(TAG, "onResume ");
        if (isConnected()) {
            NewDeviceSetup newDeviceSetup = new NewDeviceSetup();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.device_details_list, newDeviceSetup).commit();

        }
    }

    private void deviceList () {
        DeviceList deviceList = new DeviceList();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.deviceList, deviceList).commit();
        deviceSettingsList();
    }

    private boolean isConnected () {
        boolean status = false;

        WifiManager wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        mSSID = wifiInfo.getSSID();
        Log.i(TAG, "Inside getCurrentSSID " + wifiInfo.getSSID());

        if (mSSID.equals("\"SkyNet-AutoConfig\"")) {
            //if(mSSID.equals("\"AndroidWifi\"")){
            Log.i(TAG, "IF " + mSSID);
            status = true;
        }

        return status;
    }

    public boolean checkLocationPermission () {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.getActivity().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public void onRequestPermissionsResult (int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Inside case 1 ");

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.i(TAG, "Inside case 2 ");
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void deviceSettingsList () {
        DeviceSettingsList deviceSettingsList = new DeviceSettingsList();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.device_settings_list, deviceSettingsList).commit();
        deviceDetailsList();
    }

    private void deviceDetailsList () {
    }

    class updateFirebase extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground (String... strings) {

            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            String userID = user.getUid();
            homeHelper home = new homeHelper(homeName, userID, 0);
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

    class registerDevice extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground (String... strings) {

            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            ref.child("users").child(user.getUid()).child("deviceID").child(mChipID).setValue(mChipID);
            ref.child("devicUsermap").child(mChipID).setValue(user.getUid());
            return mChipID;
        }

        @Override
        protected void onPostExecute (String result) {
            deviceList();
        }
    }
}
