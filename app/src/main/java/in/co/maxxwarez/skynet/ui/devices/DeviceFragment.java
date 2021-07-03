package in.co.maxxwarez.skynet.ui.devices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import in.co.maxxwarez.skynet.R;
import in.co.maxxwarez.skynet.ui.commons.NoSetUp;
import in.co.maxxwarez.skynet.ui.commons.SetUpButton;
import in.co.maxxwarez.skynet.ui.commons.InstructionsDetail;

public class DeviceFragment extends Fragment {
    private final static String TAG = "SkyNet";
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Query query = ref.child("users").child(user.getUid()).child("deviceID");

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_device, container, false);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    device_yes();
                } else {
                    device_no();
                }
            }

            @Override
            public void onCancelled (DatabaseError databaseError) {

            }
        });

        return v;
    }

    private void device_yes () {
        DeviceList deviceList = new DeviceList();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.deviceList, deviceList).commit();
        deviceSettingsList();

    }

    private void deviceSettingsList () {
        DeviceSettingsList deviceSettingsList = new DeviceSettingsList();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.device_settings_list, deviceSettingsList).commit();
        ;
        deviceDetailsList();
    }

    private void deviceDetailsList () {
    }


    public void device_no () {
        NoSetUp noSetUp = NoSetUp.newInstance();
        Bundle args = new Bundle();
        args.putString("text", "You don't have a Device setup");
        noSetUp.setArguments(args);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.deviceList, noSetUp).commit();
        setUpButton();
    }

    private void setUpButton () {
        SetUpButton setUpButton = SetUpButton.newInstance();
        Bundle args = new Bundle();
        args.putString("text", "Start");
        args.putString("flag", "four");
        setUpButton.setArguments(args);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.device_settings_list, setUpButton).commit();
        setUpInstructionsDetail();
    }


    public void setUpInstructionsDetail () {
        InstructionsDetail instructionsDetail = InstructionsDetail.newInstance();
        Bundle args = new Bundle();
        args.putString("text", "Power on your device and wait till the blue light starts blinking fast");
        instructionsDetail.setArguments(args);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.device_details_list, instructionsDetail).commit();

    }
}