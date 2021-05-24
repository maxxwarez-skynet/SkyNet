package in.co.maxxwarez.skynet.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.co.maxxwarez.skynet.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetHomeDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetHomeDetail extends Fragment {


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
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_home_detail, container, false);
    }
}