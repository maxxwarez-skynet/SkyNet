package in.co.maxxwarez.skynet.ui.commons;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.co.maxxwarez.skynet.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoSetUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoSetUp extends Fragment {
    public TextView mTextView;

    public NoSetUp () {
        // Required empty public constructor
    }

    public static NoSetUp newInstance () {
        NoSetUp fragment = new NoSetUp();
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
        View v = inflater.inflate(R.layout.fragment_no_setup, container, false);
        mTextView = v.findViewById(R.id.noSetUp);
        Bundle b = getArguments();
        if (b != null) {
            String t = b.getString("text");
            mTextView.setText(t);
        }

        return v;
    }
}