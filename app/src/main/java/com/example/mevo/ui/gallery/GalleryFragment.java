package com.example.mevo.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mevo.R;
import com.example.mevo.databinding.FragmentGalleryBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    CardView add_patient,edit_patient,remove_patient;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);


        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        add_patient = binding.getRoot().findViewById(R.id.add_patient);
        edit_patient = binding.getRoot().findViewById(R.id.edit_patient);
        remove_patient = binding.getRoot().findViewById(R.id.remove_patient);
        add_patient.setOnClickListener(v -> {
            Snackbar.make(binding.getRoot(), "Add Patient", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .setAnchorView(R.id.fab).show();
        });
        View root = binding.getRoot();
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}