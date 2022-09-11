package in.co.maxxwarez.skynet.ui.devices;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import in.co.maxxwarez.skynet.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeviceDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeviceDetail extends Fragment {
    private static final String TAG = "SkyNet";
    public String deviceID;
    public String swStt;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();



    public DeviceDetail() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DeviceDetail newInstance(String param1, String param2) {
        DeviceDetail fragment = new DeviceDetail();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_device_detail, container, false);
        // Inflate the layout for this fragment
        Switch sw = (Switch) v.findViewById(R.id.switch0);
        
        Log.i(TAG, "SwitchState " + "state");

        DatabaseReference mReference = ref.child("users").child(user.getUid()).child("deviceID");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.i(TAG, "Key= " + ds.getKey() + " Value= " + ds.getValue());

                    deviceID = ds.getKey();
                    Log.i(TAG, "SwitchState deviceID " + deviceID);
                    DatabaseReference dReference = ref.child("Device").child(deviceID).child("State").child("switch0");
                    dReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.i(TAG, "SwitchState1 " + snapshot.getValue());
                                swStt = String.valueOf(snapshot.getValue());
                                if(swStt.equals("true"))
                                    sw.setChecked(true);
                                else
                                    sw.setChecked(false);

                            Log.i(TAG, "SwitchState000 " + swStt);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError error) {

            }
        });

        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                    ref.child("Device").child(deviceID).child("State").child("switch0").setValue(true);
                // The toggle is enabled
                Log.i(TAG, "SwitchState 222" +"true" + deviceID);
            } else {
                ref.child("Device").child(deviceID).child("State").child("switch0").setValue(false);
                Log.i(TAG, "SwitchState 222" + "false" + deviceID);
            }
        });

        return v;
    }



}