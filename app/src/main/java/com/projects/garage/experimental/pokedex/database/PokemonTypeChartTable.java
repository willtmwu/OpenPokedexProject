package com.projects.garage.experimental.pokedex.database;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import com.projects.garage.experimental.pokedex.parser.PokemonTypeChartParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;

/**
 * Created by William TM Wu
 */
public class PokemonTypeChartTable {

    public PokemonTypeChartTable(){}

    public Table parseToTable(JSONArray jsonTypeChart){
        String[] pokemonTypes;
        String effectiveness = PokemonTypeChartParser.NORMAL_EFFECT_STRING;

        //Row, Column, Value => Attacking, Defending, Effectiveness
        Table<String, String, WeaknessModel> typeChartTable = HashBasedTable.create();

        try {
            String temp = jsonTypeChart.getJSONObject(0).getString("Types");
            pokemonTypes = (new Gson()).fromJson(temp, String[].class); // Need to maintain order

            //Begin to build a table
            for (int i = 1; i < jsonTypeChart.length(); i++) {
                JSONObject jsonObj = jsonTypeChart.getJSONObject(i);
                for (int j = 0; j < pokemonTypes.length; j++) {
                    typeChartTable.put( pokemonTypes[i-1], pokemonTypes[j],
                        new WeaknessModel( pokemonTypes[i-1], pokemonTypes[j],
                            jsonObj.getString(pokemonTypes[j]))
                    );
                }
            }

            //Testing
            //Collection<WeaknessModel> groundType = typeChartTable.column("Ground").values();
            //Collection<WeaknessModel> flyingType = typeChartTable.row("Flying").values();
            //Collection<WeaknessModel> rockType = typeChartTable.column("Rock").values();


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Table
        return null;
    }

    private class WeaknessModel{
        private String attack = "Normal"; // Row
        private String defend = "Normal"; // Column
        private String effectiveness = PokemonTypeChartParser.NORMAL_EFFECT_STRING;

        WeaknessModel(String row, String column, String effect){
            this.attack = row;
            this.defend = column;
            this.effectiveness = effect;
        }

        public String getAttack() {
            return attack;
        }

        public String getDefend() {
            return defend;
        }

        public String getEffectiveness() {
            return effectiveness;
        }

    }

}
