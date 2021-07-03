package in.co.maxxwarez.skynet.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.co.maxxwarez.skynet.R;

public class HomeSettingsList extends Fragment {

    public HomeSettingsList () {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeSettingsList newInstance () {
        HomeSettingsList fragment = new HomeSettingsList();
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
        return inflater.inflate(R.layout.fragment_home_settings_list, container, false);
    }
}