package in.co.maxxwarez.skynet.ui.automation;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import in.co.maxxwarez.skynet.R;
import in.co.maxxwarez.skynet.helperClasses.automationHelper;
import in.co.maxxwarez.skynet.ui.commons.InstructionsDetail;


public class AutomationSetupButton extends Fragment implements View.OnClickListener {
    private static final String TAG = "SkyNet";
    public Button mbutton;
    public String flag;
    public String operatorType;
    public int mOperatorType;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String sourceDevice;
    private String addInput;
    private String addInputType;
    private String deviceID;
    private String addOP;
    private String destDevice;
    private Boolean addState;

    automationHelper automationHelper = new automationHelper();

    public AutomationSetupButton () {
        // Required empty public constructor
    }

    public static AutomationSetupButton newInstance () {
        AutomationSetupButton fragment = new AutomationSetupButton();
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
        View v = inflater.inflate(R.layout.fragment_automation_setup_button, container, false);
        mbutton = v.findViewById(R.id.set_up_button_automation);
        mbutton.setOnClickListener(this);
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
        return v;
    }

    @Override
    public void onClick (View v) {
        Log.i(TAG, "SetUpButton On Click ");
        clicked();

    }

    private void clicked () {
        Log.i(TAG, "clicked " + flag);

        if (flag == "one") {
            String s = "Select a Source to trigger an event. \n It can be your device or external parameter. \nExample: ";
            changeInstructions("Select Source", s, "two");
            return;

        }

        if (flag == "two") {
            final String[] list = {"Your Devices", "External Sources", "Fixed Parameters"};
            alertBuilderIP(list, "Select Source");
        }
        if (flag == "three") {
            Log.i(TAG, "Flag Three " + flag);

            if (addInputType == "Switch") {
                Log.i(TAG, "Flag Three Switch " + addInputType);
                operatorType = "Equals";
            }
            if (addInputType == "Sensor") {
                Log.i(TAG, "Flag Three Sensor " + addInputType);
                final String[] list = {"Equals", "Less Than", "Greater Than", "Between"};
                alertBuilderOperator(list, "Select Range for " + addInput);
            }
        }


        if (flag == "four") {
            if (addInputType == "Sensor") {
                addSensorValue();
            }

            if (addInputType == "Switch") {
                //  addSwitchValue();
            }

        }
        if (flag == "five") {
            getDeviceListOP(new MyCallback() {

                @Override
                public void onCallback (String[] value) {

                    alertBuilderOP(value, "Device List");
                }
            });

        }
        if (flag == "six") {
            final String[] list = {"Off", "On"};
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this.getContext());
            builder.setTitle("Select State");
            builder.setItems(list, new DialogInterface.OnClickListener() {
                @Override
                public void onClick (DialogInterface dialog, int which) {
                    if (which == 0) {
                        addState = false;
                        automationHelper.setState(false);
                    }
                    if (which == 1) {
                        addState = true;
                        automationHelper.setState(true);
                    }

                    String s = "You are almost there! Click Done to complete your automation.";
                    changeInstructions("Done", s, "seven");
                }
            });
            builder.show();
        }
        if (flag == "seven") {
            addConfig();
        }

    }

    private void changeInstructions (String buttonText, String textViewText, String flagValue) {
        mbutton.setText(buttonText);
        FragmentManager fragmentManager = getParentFragmentManager();
        InstructionsDetail instructionsDetail = (InstructionsDetail) fragmentManager.findFragmentById(R.id.automation_details_list);
        instructionsDetail.mTextView.setText(textViewText);
        flag = flagValue;
    }

    private void alertBuilderIP (final String[] list, String title) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle(title);
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialog, int which) {
                getDeviceList(new MyCallback() {

                    @Override
                    public void onCallback (String[] value) {

                        alertBuilderDevice(value, "Device List");
                    }
                });

            }
        });
        builder.show();
    }

    public void getDeviceList (final MyCallback myCallback) {
        final ArrayList<String> result = new ArrayList<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("users").child(user.getUid()).child("deviceID");
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshots : dataSnapshot.getChildren()) {
                    result.add((String) dataSnapshots.getValue());
                }
                String frnames[] = result.toArray(new String[result.size()]);
                myCallback.onCallback(frnames);
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void alertBuilderDevice (final String[] list, String title) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this.getContext());
        builder.setTitle(title);
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialog, int which) {

                alertBuilderSource(list[which]);
                sourceDevice = list[which];
                /*getIPList(new MyCallback() {

                    @Override
                    public void onCallback(String[] value) {

                        alertBuilderIP(value,"IP List");
                    }
                }, list[which]);*/

            }
        });
        builder.show();
    }

    private void alertBuilderSource (final String s) {
        final String[] list = {"Sensor", "Switch"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this.getContext());
        builder.setTitle("Select Channel");
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialog, int which) {
                if (which == 0) {
                    getSRList(new MyCallback() {

                        @Override
                        public void onCallback (String[] value) {

                            alertBuilderIP(value, "Sensor List", "Sensor");
                        }
                    }, s);
                }
                if (which == 1) {
                    getSWList(new MyCallback() {

                        @Override
                        public void onCallback (String[] value) {

                            alertBuilderIP(value, "Switch List", "Switch");
                        }
                    }, s);

                }

            }
        });
        builder.show();
    }

    private void alertBuilderIP (final String[] list, String title, final String ipType) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this.getContext());
        builder.setTitle(title);
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialog, int which) {

                addInput = list[which];
                addInputType = ipType;
                Log.i(TAG, "Config: Selection " + addInput + " " + addInputType);
                String s = "You have selected " + addInputType + " " + addInput + "\n. Any changes to value will triger an event as selected.";
                changeInstructions("Set Range ", s, "three");
                //mbutton.setText("Set Value");

            }
        });
        builder.show();
    }

    public void getSWList (final MyCallback myCallback, String s) {
        final ArrayList<String> result = new ArrayList<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("Device").child(s).child("State");
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshots : dataSnapshot.getChildren()) {
                    result.add(dataSnapshots.getKey());

                }
                String frnames[] = result.toArray(new String[result.size()]);
                myCallback.onCallback(frnames);
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {
            }
        });


    }

    public void getSRList (final MyCallback myCallback, String s) {
        final ArrayList<String> result = new ArrayList<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("Device").child(s).child("Data");
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshots : dataSnapshot.getChildren()) {
                    //JSONObject response = (JSONObject) dataSnapshot.getChildren();
                    //result.add(dataSnapshots.getKey());
                    //result.add(String.valueOf(dataSnapshots.getValue()));
                    //Map<String,String> td=(HashMap<String, String>)dataSnapshot.getKey();
                    // Log.i(TAG, "Config Data Value " + td );
                    //Data data = dataSnapshot.getValue(Data.class);
                    result.add(dataSnapshots.getKey());
                    Log.i(TAG, "Config Data Class " + " " + result);

                    //result.add(data);
                }
                String frnames[] = result.toArray(new String[result.size()]);
                myCallback.onCallback(frnames);
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void addSensorValue () {
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this.getContext());
        alert.setTitle("Title");
        alert.setMessage("Message :");

// Set an EditText view to get user input
        final EditText input = new EditText(this.getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        alert.setView(input);
        getSensorValue(new MyCallbackInt() {

            @Override
            public void onCallback (long[] value) {

                //input.setText((int) value[0]);
                input.setText(String.valueOf(value[0]));
            }
        }, sourceDevice);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int whichButton) {
                String textValue = input.getText().toString();
                Float value = Float.valueOf(textValue);
                Log.i(TAG, "Config Pin Value : " + value);
                automationHelper.setRangeA(value);
                String s = "Select Your Destination Device";
                changeInstructions("Select Destination", s, "five");
                return;
            }
        });

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick (DialogInterface dialog, int which) {
                        String value = input.getText().toString();
                        Log.d(TAG, "Config Pin Value : " + value);
                        // TODO Auto-generated method stub
                        return;
                    }
                });
        alert.show();
    }

    public void getSensorValue (final MyCallbackInt myCallback, String s) {
        String ip = addInput;
        Log.i(TAG, "getSensorValue " + s);
        ///final ArrayList<String> result = new ArrayList<>();
        final long[] result = new long[1];
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("Device").child(s).child("Data").child(ip);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshots : dataSnapshot.getChildren()) {
                    Log.i(TAG, "Value is FOR  " + dataSnapshot.getChildrenCount());
                    if (dataSnapshots.getKey().equals("val"))
                    // result.add(dataSnapshots.getValue().toString());
                    {
                        Log.i(TAG, "Value is 0 " + dataSnapshots.getValue());
                        result[0] = (long) dataSnapshots.getValue();
                        Log.i(TAG, "Value is 1 " + result[0]);
                        // Log.i(TAG,"Value is " + dataSnapshots.getValue() + result[0]);
                    }

                }
                // String frnames[]=result.toArray(new String[result.size()]);
                myCallback.onCallback(result);
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void alertBuilderOperator (final String[] list, String title) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this.getContext());
        builder.setTitle(title);
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialog, int which) {
                if (which == 0) {
                    automationHelper.setOperator(0);
                    mOperatorType = 0;
                }
                if (which == 1) {
                    automationHelper.setOperator(-1);
                    mOperatorType = -1;
                }
                if (which == 2) {
                    automationHelper.setOperator(1);
                    mOperatorType = 1;
                }
                if (which == 3) {
                    automationHelper.setOperator(2);
                    mOperatorType = 2;
                }
                operatorType = (list[which]);

                String s = "Select the value for " + addInputType;
                changeInstructions("Set Value ", s, "four");
            }
        });
        builder.show();
    }

    public void getDeviceListOP (final MyCallback myCallback) {
        final ArrayList<String> result = new ArrayList<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        //  Query query = ref.child("Device").orderByChild("home").equalTo("homeID");
        Query query = ref.child("users").child(user.getUid()).child("deviceID");
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshots : dataSnapshot.getChildren()) {
                    result.add(dataSnapshots.getKey());
                }
                String frnames[] = result.toArray(new String[result.size()]);
                myCallback.onCallback(frnames);
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void alertBuilderOP (final String[] list, String title) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this.getContext());
        builder.setTitle(title);
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialog, int which) {
                destDevice = list[which];
                automationHelper.setDevice(list[which]);
                getSWList(new MyCallback() {

                    @Override
                    public void onCallback (String[] value) {

                        alertBuilderOPSW(value, "Switch List", "Switch");
                    }
                }, list[which]);

            }
        });
        builder.show();
    }

    private void alertBuilderOPSW (final String[] list, String title, final String ipType) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this.getContext());
        builder.setTitle(title);
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialog, int which) {
                Log.i(TAG, "Config: Selection " + list[which]);
                addOP = list[which];
                automationHelper.setNode(list[which]);
                String s = "Set the state of " + addOP;
                changeInstructions("Set State", s, "six");
            }
        });
        builder.show();
    }

    private void addConfig () {
        //TODO Add Update to Firebase DB
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        String logicID = ref.child("logics").push().getKey();
        ref.child("logics").child(logicID).setValue(automationHelper);
        Log.i(TAG, "Config Push " + logicID + " " + addInputType);
        if (addInputType == "Switch") {
            logicID = ref.child("logic").push().getKey();
            ref.child("Device").child(sourceDevice).child("State").child(addInput).child("logic").child(logicID).setValue(true);
            Log.i(TAG, "Config Logic Value SW: " + destDevice + " " + addOP + " " + addInput + " " + logicID);
        }
        if (addInputType == "Sensor") {
            ref.child("Device").child(sourceDevice).child("Data").child(addInput).child("logic").child(logicID).setValue(true);
            Log.i(TAG, "Config Logic Value SR: " + destDevice + " " + addOP + " " + " " + logicID);
        }
        AutomationFragment automationFragment = new AutomationFragment();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, automationFragment).commit();

    }


    public interface MyCallback {
        void onCallback (String[] value);
    }

    public interface MyCallbackInt {
        void onCallback (long[] value);
    }

}