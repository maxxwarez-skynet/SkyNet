package in.co.maxxwarez.skynet.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import in.co.maxxwarez.skynet.MainActivity;
import in.co.maxxwarez.skynet.R;
import in.co.maxxwarez.skynet.ui.devices.DeviceFragment;

public class DeviceList_Available extends Fragment {

    private static final String TAG = "SkyNet";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    View mView;
    TextView textView;
    //public String mHomeID;
    public DeviceList_Available() {
        // Required empty public constructor
    }

    public static DeviceList_Available newInstance () {
        DeviceList_Available fragment = new DeviceList_Available();
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
        mView = inflater.inflate(R.layout.fragment_device_list_home, container, false);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query queryDevice = ref.child("users").child(user.getUid()).child("deviceID");
        queryDevice.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot device : dataSnapshot.getChildren()) {


                        HashMap<String, String> devices;
                        String deviceID = device.getKey();
                        devices = (HashMap<String,String>) device.getValue();
                        for (HashMap.Entry<String, String> entry : devices.entrySet()) {
                            String key = entry.getKey();
                            Object value = entry.getValue();
                            boolean equals = key.equals("active");
                            if(key.equals("active") && (value.equals("true"))){
                                Log.i(TAG, "Device Home 4 " + value);
                            }
                        }


                        Query queryHome = ref.child("Device").child(device.getKey());
                        queryHome.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange (@NonNull DataSnapshot dataSnapshotHome) {

                                if (dataSnapshotHome.child("home").exists()) {}

                                else {
                                    textView = mView.findViewById(R.id.textView8);
                                    textView.setText("Available Devices");
                                    String buttonID = deviceID;
                                    String buttonName = deviceID;
                                    String bName = (String) dataSnapshotHome.child("Info").child("name").getValue();
                                    createAvailableDevice(buttonID, bName);

                                    Log.i(TAG, "Device Not Attached to Home Ava " + deviceID + " " + bName);
                                }

                            }

                            @Override
                            public void onCancelled (DatabaseError databaseError) {

                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled (DatabaseError databaseError) {

            }
        });

        return mView;
    }

    private void createAvailableDevice (String buttonID, String buttonName) {
        Log.i(TAG, "createAvailableDevice  ");
        LinearLayout layout = (LinearLayout) mView.findViewById(R.id.device_available);
        Button availableDevices = new Button(getContext());
        availableDevices.setText(buttonName);
        availableDevices.setClickable(true);
        availableDevices.setTag(buttonID);
        availableDevices.setOnClickListener(handleOnClickDevice(buttonID, buttonName));
        layout.addView(availableDevices);
    }

    private View.OnClickListener handleOnClickDevice(String buttonID, String buttonName) {
        //mHomeID = buttonID;
        return new View.OnClickListener() {
            public void onClick (View v) {
                deviceClick(buttonID, buttonName);
            }

        };
    }

    private void deviceClick(String buttonID, String buttonName) {
        FragmentManager fragmentManager = getParentFragmentManager();
        HomeList homeList = (HomeList) fragmentManager.findFragmentById(R.id.homeList);
        Log.i(TAG, "Clicked" + buttonID + " " + homeList.mHomeID);
        attachDeviceToHome(buttonID, homeList.mHomeID);
    }

    private void attachDeviceToHome(String buttonID, String mHomeID) {
        Log.i(TAG, "Clicked" + buttonID + " " +mHomeID);
        Query query = ref.child("Device").child(buttonID).child("home");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    ref.child("Device").child(buttonID).child("home").setValue(mHomeID);
                    ref.child("homes").child(mHomeID).child("devices").child(buttonID).child("active").setValue(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*DeviceList_Home deviceList_home = new DeviceList_Home();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.home_details_list, deviceList_home).commit();
*/
        DeviceFragment deviceFragment = new DeviceFragment();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.nav_host_fragment, deviceFragment).commit();
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(1).setChecked(true);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.changeActionBarText("Devices");
    }
}