package in.co.maxxwarez.skynet.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import in.co.maxxwarez.skynet.R;
import in.co.maxxwarez.skynet.ui.commons.SetUpButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetHomeDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetHomeDetail extends Fragment implements View.OnClickListener {

    private static final String TAG = "SkyNet";
    EditText mhomeName;
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
        Log.i(TAG, "On Create");
        Bundle bs = this.getArguments();
        if(bs != null){
            Log.i(TAG, "IsLog" + bs.getString("homeName"));
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        Log.i(TAG, "On Create View");


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_home_detail, container, false);
        mhomeName = view.findViewById(R.id.homeName);
        Button b = view.findViewById(R.id.getLocation);
        b.setOnClickListener(this);
        mhomeName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                Log.i(TAG, "Text Changed " + s);
                if (s.toString().trim().length() <= 3) {
                    Log.i(TAG, "Text Changed IF " + s.toString().trim().length());
                    b.setVisibility(View.GONE);
                } else {
                    Log.i(TAG, "Text Changed ELSE" + s.toString().trim().length());
                    changeSettingsFragmentLabel();
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

    private void changeSettingsFragmentLabel () {
        FragmentManager fragmentManager = getParentFragmentManager();
        SetUpButton setUpButton = (SetUpButton) fragmentManager.findFragmentById(R.id.home_settings_list);
        setUpButton.flag = "two";
        setUpButton.remoteEvent("Next");
        setUpButton.homeName = String.valueOf(mhomeName.getText());
        Log.i(TAG, String.valueOf(mhomeName.getText()));

    }


    @Override
    public void onClick (View v) {
        int i = v.getId();
        if(i==R.id.getLocation){

            MapsFragment mapsFragment = new MapsFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.replace(R.id.home_details_list, mapsFragment).addToBackStack(null).commit();
        }

    }


}