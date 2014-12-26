package com.projects.garage.experimental.pokedex;

import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.os.Bundle;

import com.projects.garage.experimental.pokedex.database.PokemonTypeChartTable;

/**
 * Created by William on 14/12/2014.
 */
public class WeaknessAnalysisFragment extends Fragment {

    //Check for table object, if not found check for table file in data/data, if not found download parse, save then load
    PokemonTypeChartTable weaknessTable; // Will reload or something on creation

    public WeaknessAnalysisFragment(){}

    /*
    barProgressDialog.setTitle("Downloading Image ...");
    45
            barProgressDialog.setMessage("Download in progress ...");
    46
            barProgressDialog.setProgressStyle(barProgressDialog.STYLE_HORIZONTAL);
    47
            barProgressDialog.setProgress(0);
    48
            barProgressDialog.setMax(20);
    49
            barProgressDialog.show();
.dismiss*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weakness_analysis, container, false);
        return rootView;
    }

    public void loadWeaknessTable(){
        //final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity().getApplicationContext(), "Please wait ...", "Loading Data ...", true);
        //ringProgressDialog.setCancelable(false);

        ProgressDialog progressDialog = new ProgressDialog(getActivity().getApplicationContext());
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Loading Data...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.show();

        //Check if PokemonTypeChartTable.txt exists in internal storage, if not download and store
        //Store the htmlTypeChart




    }

}
