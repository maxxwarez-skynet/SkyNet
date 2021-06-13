package in.co.maxxwarez.skynet.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.co.maxxwarez.skynet.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailList extends Fragment {

    public DetailList () {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DetailList newInstance () {
        DetailList fragment = new DetailList();
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
        View v = inflater.inflate(R.layout.fragment_detail_list, container, false);

        return v;
    }
}