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
 * Use the {@link InstructionsDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstructionsDetail extends Fragment {


    public InstructionsDetail () {
        // Required empty public constructor
    }


    public static InstructionsDetail newInstance () {
        InstructionsDetail fragment = new InstructionsDetail();
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        Bundle b = getArguments();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_instructions_detail, container, false);
        TextView t = v.findViewById(R.id.instructions_Details);
        t.setText("Welcome to SkyNet. \n\n  Start your journey to Home Automation and Security. \n \n Set up your home to start configuring your application. Enter a name for your home and add the location. Press the button above to start.");

        if (b != null) {
            String title = b.getString("text");
            t.setText(title);
        }
        return v;
    }
}