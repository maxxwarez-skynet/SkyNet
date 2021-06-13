package in.co.maxxwarez.skynet.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import in.co.maxxwarez.skynet.R;

public class SetUpDevice extends Fragment implements View.OnClickListener {
    private static final String TAG = "SkyNet";
    public String flag = "one";
    public String deviceID;
    public int selected = 0;
    Button mbutton;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public SetUpDevice () {
        // Required empty public constructor
    }

    public static SetUpDevice newInstance () {
        SetUpDevice fragment = new SetUpDevice();
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
        View v = inflater.inflate(R.layout.fragment_set_up_device, container, false);

        mbutton = v.findViewById(R.id.setHome);
        mbutton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick (View v) {
        Log.i(TAG, "SetDevice On Click ");
        clicked();
    }

    private void clicked () {
        Log.i(TAG, "clicked " + flag);
    }

}