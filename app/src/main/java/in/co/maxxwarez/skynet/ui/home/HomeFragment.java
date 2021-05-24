package in.co.maxxwarez.skynet.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import in.co.maxxwarez.skynet.BlankFragment;
import in.co.maxxwarez.skynet.R;
import in.co.maxxwarez.skynet.SampleFragment;
import in.co.maxxwarez.skynet.firebaseHelper;
import in.co.maxxwarez.skynet.ui.fragments.DetailList;
import in.co.maxxwarez.skynet.ui.fragments.NoHomeSet;
import in.co.maxxwarez.skynet.ui.fragments.SetUpHome;
import in.co.maxxwarez.skynet.ui.fragments.SettingsList;
import in.co.maxxwarez.skynet.userHelper;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    in.co.maxxwarez.skynet.userHelper user = new userHelper();
    in.co.maxxwarez.skynet.firebaseHelper fbase = new firebaseHelper();

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        if(fbase.hasHome()){

            }
        else{
            noHomeSet();
            setupHome();
        }

        return root;
    }

    private void setupHome () {
        SetUpHome setHome = SetUpHome.newInstance();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.settingsList, setHome).addToBackStack(null).commit();
    }

    public void noHomeSet(){
        NoHomeSet noHomeSet = NoHomeSet.newInstance();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeList, noHomeSet).addToBackStack(null).commit();
    }
    public void settingsList(){
        SettingsList settingsList = SettingsList.newInstance();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.settingsList, settingsList).addToBackStack(null).commit();
    }
    public void detailList(){
        DetailList detailList = DetailList.newInstance();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detailsList,detailList ).addToBackStack(null).commit();
    }

}