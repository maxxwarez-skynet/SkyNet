package in.co.maxxwarez.skynet.ui.automation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AutomationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AutomationViewModel () {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}