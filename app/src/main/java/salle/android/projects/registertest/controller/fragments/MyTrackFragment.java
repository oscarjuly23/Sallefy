package salle.android.projects.registertest.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import salle.android.projects.registertest.R;

public class MyTrackFragment extends Fragment {

    public static final String TAG = MyTrackFragment.class.getName();

    public MyTrackFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library_canciones, container, false);
    }
}
