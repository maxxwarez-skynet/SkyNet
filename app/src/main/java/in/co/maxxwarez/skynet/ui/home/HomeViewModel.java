package in.co.maxxwarez.skynet.ui.home;

import android.view.View;
import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import in.co.maxxwarez.skynet.R;
import in.co.maxxwarez.skynet.userHelper;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Integer> mButton;

    public HomeViewModel() {
        userHelper user = new userHelper();
        mText = new MutableLiveData<>();
        mButton = new MutableLiveData<>();
        if (user.isLoggedIn()) {
            mText.setValue("You Are Signed In!");
            mButton.setValue(View.GONE);
        } else {
            mText.setValue("You Are NOT Signed In!");
            mButton.setValue(View.VISIBLE);
        }


    }


    public LiveData<String> getText() {
        return mText;
    }
    public  LiveData<Integer> setVisisble(){
        return mButton;
    }
}