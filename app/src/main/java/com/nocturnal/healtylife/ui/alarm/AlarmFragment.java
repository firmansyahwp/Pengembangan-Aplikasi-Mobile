package com.nocturnal.healtylife.ui.alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.nocturnal.healtylife.R;

public class AlarmFragment extends Fragment {

    private AlarmViewModel mViewModel;

    private TextView alarm_time;
    private Button alarm_pick_time;

    public static AlarmFragment newInstance() {
        return new AlarmFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarm_fragment, container, false);

        alarm_time = view.findViewById(R.id.alarm_time);
        alarm_pick_time = view.findViewById(R.id.alarm_pick_time);

        alarm_pick_time.setOnClickListener(alarmPickTimeOnClick());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AlarmViewModel.class);
        // TODO: Use the ViewModel
    }

    private View.OnClickListener alarmPickTimeOnClick() {
        return view -> {
            DialogFragment dialogFragment = new TimePickerFragment(alarm_time);
            dialogFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
        };
    }
}
