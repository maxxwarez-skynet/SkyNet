package in.co.maxxwarez.skynet.ui.automation;

import android.os.Bundle;
import android.util.Log;
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
import in.co.maxxwarez.skynet.ui.commons.InstructionsDetail;
import in.co.maxxwarez.skynet.ui.commons.NoSetUp;

public class AutomationFragment extends Fragment {

    private final static String TAG = "SkyNet";
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Query queryDevice = ref.child("users").child(user.getUid()).child("deviceID");

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_automation, container, false);
        queryDevice.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot device : dataSnapshot.getChildren()) {
                        String deviceID = device.getKey();
                        final DatabaseReference refLogic = FirebaseDatabase.getInstance().getReference("logics");
                        Query logicsQuery = refLogic.orderByChild("device").equalTo(deviceID);

                        logicsQuery.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange (@NonNull DataSnapshot dataSnapshotL) {
                                if (dataSnapshotL.exists()) {
                                    Log.i(TAG, "ChildrenCount L " + dataSnapshotL.getChildrenCount());
                                    for (DataSnapshot logics : dataSnapshotL.getChildren()) {
                                        Log.i(TAG, "ChildrenCount L " + dataSnapshotL.getChildrenCount());
                                        String logicID = logics.getKey();
                                        Log.i(TAG, "ChildrenCount " + dataSnapshotL.getChildrenCount() + logicID);
                                    }
                                    logic_yes();

                                } else {
                                    Log.i(TAG, "ChildrenCount L Else ");
                                    logic_no();
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

        return v;
    }

    private void logic_yes () {
        Log.i(TAG, "Automation Yes");
    }

    private void logic_no () {
        NoSetUp noSetUp = NoSetUp.newInstance();
        Bundle args = new Bundle();
        args.putString("text", "You don't have an Automation setup");
        noSetUp.setArguments(args);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.automationList, noSetUp).commit();
        setUpButton();
    }

    private void setUpButton () {
        AutomationSetupButton automationSetupButton = AutomationSetupButton.newInstance();
        Bundle args = new Bundle();
        args.putString("text", "Start");
        args.putString("flag", "one");
        automationSetupButton.setArguments(args);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.automation_settings_list, automationSetupButton).commit();
        setUpInstructionsDetail();
    }

    public void setUpInstructionsDetail () {
        InstructionsDetail instructionsDetail = InstructionsDetail.newInstance();
        Bundle args = new Bundle();
        args.putString("text", "Here you can define your Home Automation Logics. \n Start your automation journey");
        instructionsDetail.setArguments(args);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.automation_details_list, instructionsDetail).commit();

    }
}