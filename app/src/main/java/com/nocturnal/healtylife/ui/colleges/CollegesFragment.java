package com.nocturnal.healtylife.ui.colleges;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.nocturnal.healtylife.R;

import java.util.ArrayList;
import java.util.List;

public class CollegesFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollegesViewModel mViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    public static CollegesFragment newInstance() {
        return new CollegesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.colleges_fragment, container, false);
        recyclerView = view.findViewById(R.id.colleges_recycler_view);
        loadColleges();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CollegesViewModel.class);
        // TODO: Use the ViewModel
    }

    private void loadColleges() {
        db.collection("kampus")
                .orderBy("name")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        System.err.println("Listen failed:" + e);
                        return;
                    }

                    List<College> colleges = new ArrayList<>();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        if (doc.get("name") != null && doc.get("logo") != null) {
                            colleges.add(new College(doc.getString("name"), doc.getString("logo"), doc.getString("web")));
                        }
                    }

                    layoutManager = new LinearLayoutManager(getActivity());
                    adapter = new RecyclerAdapter(colleges, getActivity());

                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                });
    }

}
