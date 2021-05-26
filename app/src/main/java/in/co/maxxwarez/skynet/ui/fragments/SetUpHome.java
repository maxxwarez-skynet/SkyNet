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

import in.co.maxxwarez.skynet.R;
import in.co.maxxwarez.skynet.SampleFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetUpHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetUpHome extends Fragment implements View.OnClickListener {
    private static final String TAG = "SkyNet";
    public SetUpHome () {
        // Required empty public constructor
    }


    public static SetUpHome newInstance () {
        SetUpHome fragment = new SetUpHome();
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
        View view = inflater.inflate(R.layout.fragment_set_up_home, container, false);
        Button b = view.findViewById(R.id.setHome);
        b.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick (View v) {
        Log.i(TAG, "SetUpHome On Click ");
        /*SetHomeDetail setHomeDetail = SetHomeDetail.newInstance();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.detailsList, setHomeDetail).addToBackStack(null).commit();
*/
        MapsFragment mapsFragment = new MapsFragment();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.detailsList, mapsFragment).addToBackStack(null).commit();

    }
}
