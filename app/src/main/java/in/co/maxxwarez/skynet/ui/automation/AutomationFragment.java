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

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

import in.co.maxxwarez.skynet.R;
import in.co.maxxwarez.skynet.ui.commons.InstructionsDetail;
import in.co.maxxwarez.skynet.ui.commons.NoSetUp;

public class AutomationFragment extends Fragment {

    private final static String TAG = "SkyNet";
    private FirebaseFunctions mFunctions;
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Query queryDevice = ref.child("users").child(user.getUid()).child("deviceID");

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {

        mFunctions = FirebaseFunctions.getInstance();
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
                                    //logic_yes1();
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

    private void logic_yes1 () {
        testOnCall()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete (@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Exception e = task.getException();
                            if (e instanceof FirebaseFunctionsException) {
                                FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                                FirebaseFunctionsException.Code code = ffe.getCode();
                                Object details = ffe.getDetails();
                            }

                            // [START_EXCLUDE]
                            Log.w(TAG, "addMessage:onFailure", e);
                            return;
                            // [END_EXCLUDE]
                        }

                        // [START_EXCLUDE]
                        String result = task.getResult();
                        Log.i(TAG, "Result " + result);
                        // [END_EXCLUDE]
                    }
                });


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

    private Task<String> testOnCall () {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("text", "text");
        data.put("push", "true");

        return mFunctions
                .getHttpsCallable("testOnCall")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then (@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        String result = (String) task.getResult().getData();
                        return result;
                    }
                });
    }
}