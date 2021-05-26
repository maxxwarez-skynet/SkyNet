package in.co.maxxwarez.skynet.ui.fragments;

import android.Manifest;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import in.co.maxxwarez.skynet.FetchAddressTask;
import in.co.maxxwarez.skynet.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetHomeDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetHomeDetail extends Fragment implements View.OnClickListener {

    private static final String TAG = "SkyNet";
    private TextView mAddress;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private AnimatorSet mRotateAnim;
    private boolean mTrackingLocation;

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String TRACKING_LOCATION_KEY = "tracking_location";
    public SetHomeDetail () {
        // Required empty public constructor
    }

    public static SetHomeDetail newInstance () {
        SetHomeDetail fragment = new SetHomeDetail();
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the FusedLocationClient.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_home_detail, container, false);
        EditText homeName = view.findViewById(R.id.homeName);
        Button b = view.findViewById(R.id.getLocation);
        b.setOnClickListener(this);
        mAddress = view.findViewById(R.id.addressBox);
        homeName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                Log.i(TAG, "Text Changed " + s);
                if (s.toString().trim().length() <= 3) {
                    Log.i(TAG, "Text Changed IF " + s.toString().trim().length());
                    b.setVisibility(View.GONE);
                } else {
                    Log.i(TAG, "Text Changed ELSE" + s.toString().trim().length());
                    b.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged (CharSequence s, int start, int count,
                                           int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged (Editable s) {
                // TODO Auto-generated method stub

            }
        });
        return view;
    }



    @Override
    public void onClick (View v) {
        int i = v.getId();
        if(i==R.id.getLocation){
            Log.i(TAG, "On Click");
        }

        startTrackingLocation();
    }

    private void startTrackingLocation() {
        Log.i(TAG, "Start Tracking Location");
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            mTrackingLocation = true;
            mFusedLocationClient.requestLocationUpdates
                    (getLocationRequest(),
                            mLocationCallback,
                            null /* Looper */);

            // Set a loading text while you wait for the address to be
            // returned

        }
    }
    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }
}