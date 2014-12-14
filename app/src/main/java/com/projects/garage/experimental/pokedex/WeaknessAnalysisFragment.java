package com.projects.garage.experimental.pokedex;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by William on 14/12/2014.
 */
public class WeaknessAnalysisFragment extends Fragment {
    public WeaknessAnalysisFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weakness_analysis, container, false);
        return rootView;
    }
}
