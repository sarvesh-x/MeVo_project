package com.example.mevo.ui.patients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mevo.R;
import com.example.mevo.databinding.FragmentPatientsBinding;

public class PatientsFragment extends Fragment {

private FragmentPatientsBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        PatientsViewModel patientsViewModel =
                new ViewModelProvider(this).get(PatientsViewModel.class);

        binding = FragmentPatientsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LinearLayout create,edit,remove;
        create = binding.getRoot().findViewById(R.id.create_patient);
        remove = binding.getRoot().findViewById(R.id.remove_patient);
        edit = binding.getRoot().findViewById(R.id.edit_patient);

        create.setOnClickListener(v -> {
            Toast.makeText(binding.getRoot().getContext(),"Create",Toast.LENGTH_LONG).show();

        });

        remove.setOnClickListener(v -> {
            Toast.makeText(binding.getRoot().getContext(),"Remove",Toast.LENGTH_LONG).show();
        });

        edit.setOnClickListener(v -> {
            Toast.makeText(binding.getRoot().getContext(),"Edit",Toast.LENGTH_LONG).show();
        });

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}