package com.projects.garage.experimental.pokedex.network;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by William TM Wu
 */
public interface PokemonNetworkInterface {

    public static final String END_POINT = "http://pokemondb.net";

    @GET("/type")
    void httpGetTypeChart(Callback<String> htmlTypeChart);

}
