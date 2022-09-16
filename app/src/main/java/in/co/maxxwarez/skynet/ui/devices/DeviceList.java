package in.co.maxxwarez.skynet.ui.devices;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import in.co.maxxwarez.skynet.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeviceList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeviceList extends Fragment {
    private static final String TAG = "SkyNet";
    public String mDeviceID;
    View mView;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public DeviceList () {
        // Required empty public constructor
    }

    public static DeviceList newInstance () {
        DeviceList fragment = new DeviceList();
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
        mView = inflater.inflate(R.layout.fragment_device_list, container, false);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query queryDevice = ref.child("users").child(user.getUid()).child("deviceID");
        queryDevice.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot device : dataSnapshot.getChildren()) {
                        Log.i(TAG, "ChildrenCount " + dataSnapshot.getChildrenCount());
                        if (dataSnapshot.getChildrenCount() == 1) {
                            String deviceID = device.getKey();
                            mDeviceID = deviceID;
                            Log.i(TAG, "HomeList 1 " + deviceID + " " + mDeviceID);
                        } else {
                            //ToDo: Look for Order and set homeID with order = 0
                        }
                        String buttonID = device.getKey();
                        String buttonName = (String) device.child("name").getValue();
                        createDevice(buttonID, buttonName);
                        // createDevice(buttonID, buttonName);


                        Log.i(TAG, "Key " + buttonID + " Value " + dataSnapshot.toString());
                    }
                }

            }

            @Override
            public void onCancelled (DatabaseError databaseError) {

            }
        });


        return mView;
    }

    private void createDevice (String buttonID, String buttonName) {
        LinearLayout layout = (LinearLayout) mView.findViewById(R.id.device_icons);
        Button myDevice = new Button(getContext());
        myDevice.setText(buttonName);
        myDevice.setClickable(true);

        // myDevice.setId(Integer.parseInt(buttonID));
        myDevice.setTag(buttonID);
        myDevice.setOnClickListener(handleOnClickHome(buttonID, buttonName));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0, 0, 0);
        layoutParams.width = mView.getWidth();//-(mView.getWidth()/5);
        layoutParams.height = mView.getHeight();
        myDevice.setLayoutParams(layoutParams);
        //myDevice.setPadding(20,0,20,0);
        int ColorValue = Color.parseColor("#81a5e3");
        myDevice.setBackgroundColor(ColorValue);
        layout.addView(myDevice, layoutParams);

    }

    View.OnClickListener handleOnClickHome (final String buttonID, final String buttonName) {
        return new View.OnClickListener() {
            public void onClick (View v) {
                deviceClick(buttonID, buttonName);
            }

        };
    }

    private void deviceClick (String buttonID, String buttonName) {
        Log.i(TAG, "Clicked" + buttonID + " " + buttonName);
        DeviceDetail deviceDetail = new DeviceDetail();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.device_details_list, deviceDetail).commit();
        mDeviceID = buttonID;

    }

}