package com.projects.garage.experimental.pokedex.parser;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by William on 11/12/2014.
 */
public class PokemonTypeChartParser {

    private static final String ROW_TAG = "<tr>";
    private static final String COLUMN_TAG = "<td";
    private static final String TYPE_COLUMN_TAG = "<th>";

    public static final String NO_EFFECT_STRING = "no effect";
    public static final String HALF_EFFECT_STRING = "not very effective";
    public static final String NORMAL_EFFECT_STRING = "normal effectiveness";
    public static final String SUPER_EFFECT_STRING = "super-effective";

    private static final double HALF_EFFECT_INT = 0.5;
    private static final int    NORMAL_EFFECT_INT = 1;
    private static final int    SUPER_EFFECT_INT = 2;

    public PokemonTypeChartParser(){}

    public static JSONArray htmlTypeChartParser(String htmlTypeChart){
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;

        String[] rows;
        String[] columns;

        List<String> pokemonTypes = new ArrayList<>();
        String effectiveness = NORMAL_EFFECT_STRING;

        //Extract Pokemon types from ROW 1
        rows = htmlTypeChart.split(ROW_TAG);
        for(int i=1; i<rows.length; i++){
            if(i == 1){
                pokemonTypes = parsePokemonType(rows[i]);
                jsonObject = new JSONObject();

                try {
                    //jsonObject.put("Types", pokemonTypes);
                    jsonObject.put("Types", (new Gson()).toJson(pokemonTypes));
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                jsonObject = new JSONObject();

                columns = rows[i].split(COLUMN_TAG);
                for (int j = 1; j < columns.length; j++) {
                    if(columns[j].contains(NO_EFFECT_STRING)){
                        effectiveness = NO_EFFECT_STRING;
                    } else if(columns[j].contains(HALF_EFFECT_STRING)){
                        effectiveness = HALF_EFFECT_STRING;
                    } else if (columns[j].contains(NORMAL_EFFECT_STRING)) {
                        effectiveness = NORMAL_EFFECT_STRING;
                    } else if (columns[j].contains(SUPER_EFFECT_STRING)){
                        effectiveness = SUPER_EFFECT_STRING;
                    }

                    try {
                        jsonObject.put(pokemonTypes.get(j-1), effectiveness);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                /*Each JSONObject in the array corresponds to a row in the table
                * While row in the the table is ordered according to the Type object
                */
                jsonArray.put(jsonObject);
            }
        }

        //System.out.println(jsonArray);
        return jsonArray;
    }

    public static List<String> parsePokemonType(String pokemonTypeString){
        List<String> pokemonTypes = new ArrayList<>();
        String[] stringTypes = pokemonTypeString.split(TYPE_COLUMN_TAG);

        for (int i = 1; i < stringTypes.length; i++) {
            String[] splitOne = stringTypes[i].split("title=");
            String[] splitTwo = splitOne[1].split("\"");
            pokemonTypes.add(splitTwo[1]);
        }

        return pokemonTypes;
    }

}
