package in.co.maxxwarez.skynet.ui.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import in.co.maxxwarez.skynet.R;
import in.co.maxxwarez.skynet.ui.commons.SetUpButton;

public class MapsFragment extends Fragment  {
    private static final String TAG = "SkyNet";
    String mhomeName;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady (GoogleMap googleMap) {
            FragmentManager fragmentManager = getParentFragmentManager();
            SetUpButton setUpButton = (SetUpButton) fragmentManager.findFragmentById(R.id.home_settings_list);

            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.setMyLocationEnabled(true);
            googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick () {
                    setUpButton.mbutton.setText("Tap on your location");
                    setUpButton.flag = "";
                    return false;
                }
            });
            googleMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick (@NonNull @NotNull Location location) {
                 //   Toast.makeText(getContext(), "Current location:\n" + location, Toast.LENGTH_LONG)
                 //           .show();
                   // LatLng myLocation = new LatLng(34, 251);
                    LatLng myLocation = new LatLng(location.getLatitude(),location.getLongitude());
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(myLocation).title("Marker at your " + mhomeName));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                    setUpButton.mbutton.setText("Done");
                    Log.i(TAG, "clicked map");
                    setUpButton.flag = "three";

                }
            });
            googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {
                }

                @Override
                public void onMarkerDrag(Marker marker) {
                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    LatLng latLng = marker.getPosition();
                    Toast.makeText(getContext(), "Current location:\n" + latLng, Toast.LENGTH_LONG)
                            .show();
                }
            });
           }
    };




    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater,
                              @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        Bundle b = this.getArguments();
        if(b != null){
            mhomeName =b.getString("homeName");
        }
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


}