package in.co.maxxwarez.skynet.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import in.co.maxxwarez.skynet.R;
import in.co.maxxwarez.skynet.ui.devices.DeviceFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoDevice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoDevice extends Fragment implements View.OnClickListener {
    private final static String TAG = "SkyNet";

    public NoDevice () {
        // Required empty public constructor
    }

    public static NoDevice newInstance () {
        NoDevice fragment = new NoDevice();
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
        View v = inflater.inflate(R.layout.fragment_no_device, container, false);
        Button button_yes = v.findViewById(R.id.device_yes);
        Button button_no = v.findViewById(R.id.device_no);
        button_yes.setOnClickListener(this);
        button_no.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick (View v) {
        int i = v.getId();
        if (i == R.id.device_no)

            Log.i(TAG, "Clicked No " + i);
        if (i == R.id.device_yes) {
            DeviceFragment deviceFragment = new DeviceFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.replace(R.id.nav_host_fragment, deviceFragment).commit();
            Log.i(TAG, "Clicked Yes " + i);
        }

    }

}