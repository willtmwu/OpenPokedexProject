package com.projects.garage.experimental.pokedex.network;

import com.google.common.collect.Table;
import com.projects.garage.experimental.pokedex.database.PokemonTypeChartTable;
import com.projects.garage.experimental.pokedex.parser.PokemonTypeChartParser;

import org.apache.http.HttpStatus;
import org.ccil.cowan.tagsoup.jaxp.SAXParserImpl;
import org.json.JSONArray;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by William TM Wu
 */
public class PokemonNetworkConnection {

    public PokemonNetworkConnection(){}

    public static void getTypeChart(){
        RestAdapter adapter = new RestAdapter
                .Builder()
                .setEndpoint(PokemonNetworkInterface.END_POINT)
                .setConverter(new HtmlTypeChartConverter())
                .build();

        PokemonNetworkInterface pni = adapter.create(PokemonNetworkInterface.class);
        pni.httpGetTypeChart(new Callback<String>() {
            @Override
            public void success(String htmlTypeChart, Response response) {
                //System.out.println(htmlTypeChart); // Need to be loaded back into a matrix form, Table Class Guava
                PokemonTypeChartTable pokt = new PokemonTypeChartTable(htmlTypeChart);

                //pass reference back to main, somehow
                //Or object creation, and manipulation instead of libs
            }

            @Override
            public void failure(RetrofitError error) {
                //may need to throw or pass error back to main thread to toast
            }
        });
    }

    /*  Parses the html body (webpage) of the response
    *   and extracts the html Pokemon type chart.
    * */
    static class HtmlTypeChartConverter implements Converter {

        @Override
        public Object fromBody(TypedInput body, Type type) throws ConversionException {
            String text = null;
            try {
                text = htmlChartParser(body.in());
            } catch (Exception ignored) {/*NOP*/ }
            return text;
        }

        @Override
        public TypedOutput toBody(Object object) {
            return null;
        }

        public static String htmlChartParser(InputStream in) throws SAXException, IOException {

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if(line.contains("<table class=\"type-table\">")) {
                    out.append(line);
                }
            }
            return out.toString();

            //Retain code for possible future HTML parsing using TagSoup
            /*SaxParserHandler saxParserHandler = new SaxParserHandler();
            SAXParserImpl.newInstance(null).parse(in, saxParserHandler);
            return saxParserHandler.getText();*/
        }
    }

    static class SaxParserHandler extends DefaultHandler{
        StringBuilder sb = new StringBuilder();
        boolean keep = false;

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (keep) {
                sb.append(ch, start, length);
            }
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
            if (localName.equalsIgnoreCase("table")) {
                keep = true;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (localName.equalsIgnoreCase("table")) {
                keep = false;
            }
        }

        public String getText() {
            return sb.toString();
        }
    }

}


