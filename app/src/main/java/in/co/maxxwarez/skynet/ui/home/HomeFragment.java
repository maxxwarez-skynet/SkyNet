package in.co.maxxwarez.skynet.ui.home;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.HashMap;

import in.co.maxxwarez.skynet.R;
import in.co.maxxwarez.skynet.ui.fragments.DetailList;
import in.co.maxxwarez.skynet.ui.fragments.HomeList;
import in.co.maxxwarez.skynet.ui.fragments.InstructionsDetail;
import in.co.maxxwarez.skynet.ui.fragments.NoHomeSet;
import in.co.maxxwarez.skynet.ui.fragments.SetUpHome;
import in.co.maxxwarez.skynet.ui.fragments.SettingsList;

public class HomeFragment extends Fragment {
    private final static String TAG = "SkyNet";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    Query query = ref.child("users").child(user.getUid()).child("homes");

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    homeSet();
                }
                else
                {
                    noHomeSet();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }


    private void setupHome () {
        SetUpHome setHome = SetUpHome.newInstance();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.settingsList, setHome).commit();
        setUpInstructionsDetail();
    }

    public void noHomeSet(){
        NoHomeSet noHomeSet = NoHomeSet.newInstance();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeList, noHomeSet).commit();
        setupHome();
    }

    public void homeSet (){
        HomeList homeList =  HomeList.newInstance();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeList, homeList).commit();
        settingsList();
    }

    public void settingsList () {
        SettingsList settingsList = SettingsList.newInstance();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.settingsList, settingsList).commit();
        //detalisList();
    }

    private void detalisList () {
        DetailList detailList = DetailList.newInstance();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detailsList, detailList).commit();
    }

    public void setUpInstructionsDetail () {
        InstructionsDetail instructionsDetail = InstructionsDetail.newInstance();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detailsList, instructionsDetail).commit();
    }

}