package in.co.maxxwarez.skynet.ui.fragments;

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

public class HomeList extends Fragment implements View.OnClickListener {
    private final static String TAG = "SkyNet";
    View mView;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    public String mHomeID;

    public HomeList () {
        // Required empty public constructor
    }


    public static HomeList newInstance () {
        HomeList fragment = new HomeList();
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
        mView = inflater.inflate(R.layout.fragment_home_list, container, false);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query queryHomes = ref.child("users").child(user.getUid()).child("homes");
        queryHomes.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot home : dataSnapshot.getChildren()) {
                        Log.i(TAG, "ChildrenCount " + dataSnapshot.getChildrenCount());
                        if (dataSnapshot.getChildrenCount() == 1) {
                            String HomeID = home.getKey();
                            mHomeID = HomeID;
                            checkDevice(mHomeID);
                            Log.i(TAG, "HomeList 1 " + HomeID + " " + mHomeID);
                        } else {
                            //ToDo: Look for Order and set homeID with order = 0
                        }
                        String buttonID = home.getKey();
                        String buttonName = (String) home.getValue();
                        createHomes(buttonID, buttonName);
                        // createDevice(buttonID, buttonName);


                        Log.i(TAG, "Key " + buttonID + " Value " + buttonName);
                    }
                }

            }

            @Override
            public void onCancelled (DatabaseError databaseError) {

            }
        });


        return mView;
    }

    private void checkDevice (String HomeID) {
        Log.i(TAG, "HomeList 2 " + HomeID);
        Query queryHomes = ref.child("homes").child(HomeID).child("devices");
        queryHomes.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Overview overview = Overview.newInstance();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.detailsList, overview).commit();
                } else {
                    NoDevice noDevice = NoDevice.newInstance();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.detailsList, noDevice).commit();

                }

            }

            @Override
            public void onCancelled (DatabaseError databaseError) {

            }
        });
    }

    private void fragmentLoader (String fragmentName) {

    }

    private void createHomes (String buttonID, String buttonName) {
        LinearLayout layout = (LinearLayout) mView.findViewById(R.id.home_icons);
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
    View.OnClickListener handleOnClickHome(final String buttonID, final String buttonName){
        return new View.OnClickListener() {
            public void onClick(View v) {
                homeClick (buttonID, buttonName);
            }

        };
    }
    private void homeClick(String buttonID, String buttonName){
        Log.i(TAG, "Clicked" + buttonID + " " + buttonName);
        mHomeID = buttonID;

    }


    @Override
    public void onClick (View v) {

    }


}