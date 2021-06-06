package in.co.maxxwarez.skynet.ui.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import in.co.maxxwarez.skynet.R;

public class HomeList extends Fragment implements View.OnClickListener{
    private final static String TAG = "SkyNet";
    View mView;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public String homeID;

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
        Query query = ref.child("users").child(user.getUid()).child("homes");
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot home : dataSnapshot.getChildren()){
                       String buttonID = home.getKey();
                       String buttonName = (String) home.getValue();
                        createDevice(buttonID, buttonName);

                       Log.i(TAG, "Key " +  buttonID + " Value " + buttonName);
                    }

                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return mView;
    }

    private void createDevice (String buttonID, String buttonName) {
        LinearLayout layout = (LinearLayout) mView.findViewById(R.id.home_icons);
        Button myDevice = new Button(getContext());
        myDevice.setText(buttonName);
        myDevice.setClickable(true);

       // myDevice.setId(Integer.parseInt(buttonID));
        myDevice.setTag(buttonID);
        myDevice.setOnClickListener(handleOnClickHome(buttonID, buttonName));

        ViewGroup.LayoutParams layoutParams = mView.getLayoutParams();
        layoutParams.width = mView.getWidth();
        layoutParams.height = mView.getHeight();
        myDevice.setLayoutParams(layoutParams);
        layout.addView(myDevice);
        int ColorValue = Color.parseColor("#81a5e3");
        myDevice.setBackgroundColor(ColorValue);

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

    }


    @Override
    public void onClick (View v) {

    }
}