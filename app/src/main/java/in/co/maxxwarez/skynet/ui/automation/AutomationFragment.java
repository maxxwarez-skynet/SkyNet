package in.co.maxxwarez.skynet.ui.automation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import in.co.maxxwarez.skynet.R;

public class AutomationFragment extends Fragment {

    private AutomationViewModel automationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        automationViewModel =
                new ViewModelProvider(this).get(AutomationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_automation, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        automationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
}