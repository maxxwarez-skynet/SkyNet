package in.co.maxxwarez.skynet.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import in.co.maxxwarez.skynet.R;

public class DeviceList_Home extends Fragment {

    private static final String TAG = "SkyNet";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    View mView;
    TextView textView;
    public DeviceList_Home () {
        // Required empty public constructor
    }

    public static DeviceList_Home newInstance () {
        DeviceList_Home fragment = new DeviceList_Home();
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
                        Log.i(TAG, "Device Home " + device);
                         String deviceID = device.getKey();


                        Query queryHome = ref.child("Device").child(device.getKey());
                        queryHome.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange (@NonNull DataSnapshot dataSnapshotHome) {

                                if (dataSnapshotHome.child("home").exists()) {
                                    String buttonID = device.getKey();
                                    String buttonName = (String) device.getValue();
                                    createAttachedDevice(buttonID, buttonName);
                                    Log.i(TAG, "Device Attached to Home " + device.getValue());

                                } else {
                                    textView = mView.findViewById(R.id.textView8);
                                    textView.setText("Available Devices");
                                    String buttonID = deviceID;
                                    String buttonName = deviceID;
                                    String bName = (String) dataSnapshotHome.child("Info").child("name").getValue();
                                    createAvailableDevice(buttonID, bName);

                                    Log.i(TAG, "Device Not Attached to Home " + deviceID + bName);
                                }

                            }

                            @Override
                            public void onCancelled (DatabaseError databaseError) {

                            }
                        });


                        Log.i(TAG, "ChildrenCount " + dataSnapshot.getChildrenCount());
                        //String deviceID = device.getKey();
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
        Button myHome = new Button(getContext());
        myHome.setText(buttonName);
        myHome.setClickable(true);
        layout.addView(myHome);
    }

    private void createAttachedDevice (String buttonID, String buttonName) {
        /*LinearLayout layout = (LinearLayout) mView.findViewById(R.id.device_attached);
        Button myHome = new Button(getContext());
        myHome.setText(buttonName);
        myHome.setClickable(true);*/
    }
}