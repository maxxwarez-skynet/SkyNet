package in.co.maxxwarez.skynet.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;

import in.co.maxxwarez.skynet.MainActivity;
import in.co.maxxwarez.skynet.R;
import in.co.maxxwarez.skynet.Sample;
import in.co.maxxwarez.skynet.ui.devices.DeviceFragment;

public class HomeFragment extends Fragment implements  View.OnClickListener{

    private static final String TAG_FRAGMENT = "Home";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        SignInButton signInButton = (SignInButton) root.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

       homeViewModel.setVisisble().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer v) {
                signInButton.setVisibility(v);
            }
        });


        return root;

    }


    @Override
    public void onClick(View v) {
        Intent myIntent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(myIntent);
    }
}