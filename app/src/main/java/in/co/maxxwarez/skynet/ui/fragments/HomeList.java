package in.co.maxxwarez.skynet.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.co.maxxwarez.skynet.R;

public class HomeList extends Fragment {

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
        return inflater.inflate(R.layout.fragment_home_list, container, false);
    }
}