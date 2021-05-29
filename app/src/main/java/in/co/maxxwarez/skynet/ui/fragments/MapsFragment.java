package in.co.maxxwarez.skynet.ui.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

import in.co.maxxwarez.skynet.R;

public class MapsFragment extends Fragment  {
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
            //LatLng myLocation = new LatLng(34, 251);
            //LatLng myLocation = new LatLng(location.getLatitude(),location.getLongitude());
            //googleMap.addMarker(new MarkerOptions().position(myLocation).title("Marker in Sydney"));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.setMyLocationEnabled(true);
            googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick () {
                    return false;
                }
            });
            googleMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick (@NonNull @NotNull Location location) {
                    Toast.makeText(getContext(), "Current location:\n" + location, Toast.LENGTH_LONG)
                            .show();
                   // LatLng myLocation = new LatLng(34, 251);
                    LatLng myLocation = new LatLng(location.getLatitude(),location.getLongitude());
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(myLocation).title("Marker at your " + mhomeName));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                    SetHomeDetail setHomeDetail = new SetHomeDetail();
                   FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);fragmentTransaction.replace(R.id.detailsList, setHomeDetail).addToBackStack(null).commit();

                    Bundle b = new Bundle();
                    b.putString("homeName", "String.valueOf(mhomeName.getText())");
                    setHomeDetail.setArguments(b);
                    fragmentTransaction.replace(R.id.detailsList, setHomeDetail);


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
           // LatLng sydney = new LatLng(34, 251);
          //  googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
           // googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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