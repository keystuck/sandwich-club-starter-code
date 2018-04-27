package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        //String values for names
        final String NAME = "name";
        final String MAIN_NAME = "mainName";
        final String AKA = "alsoKnownAs";
        final String ORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMG = "image";
        final String INGRED = "ingredients";

        //variables for holding values
        String mainName;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<>();


        //initialize new JSONObject
        JSONObject sandwichJson;

        //try/catch block
        try {
            //convert input String to a JSONObject
            sandwichJson = new JSONObject(json);

            //get JSONObject containing mainName String and alsoKnownAs array
            JSONObject nameJSON = sandwichJson.getJSONObject(NAME);
            mainName = nameJSON.getString(MAIN_NAME);
            JSONArray akaNames = nameJSON.getJSONArray(AKA);

            //if array is empty, put an empty string; else, add all AKA names to list
            if (akaNames.length() == 0) {
                alsoKnownAs.add("");
            } else {
                for (int i = 0; i < akaNames.length(); i++) {
                    alsoKnownAs.add(akaNames.getString(i));
                }
            }

            //get place of origin, description, image URL
            placeOfOrigin = sandwichJson.getString(ORIGIN);
            description = sandwichJson.getString(DESCRIPTION);
            image = sandwichJson.getString(IMG);

            //get JSONArray containing list of ingredients
            JSONArray ingrList = sandwichJson.getJSONArray(INGRED);

            //if empty, empty string; else add all ingredients
            if (ingrList.length() == 0) {
                ingredients.add("");
            } else {
                for (int i = 0; i < ingrList.length(); i++) {
                    ingredients.add(ingrList.getString(i));
                }
            }
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}