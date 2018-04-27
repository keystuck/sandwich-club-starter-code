package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        int position = DEFAULT_POSITION;
        if (intent != null && intent.hasExtra(EXTRA_POSITION)) {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView akaTextView = findViewById(R.id.also_known_tv);
        StringBuilder akaString = new StringBuilder();
        ArrayList<String> akaList = (ArrayList<String>)sandwich.getAlsoKnownAs();
        for (String name : akaList) {
            akaString.append(name);
            akaString.append(", ");
        }
        if (akaString.length() != 0){

            akaTextView.setText(akaString.substring(0, akaString.length()-2));
        }
        else akaTextView.setText("");


        TextView ingredTextView =  findViewById(R.id.ingredients_tv);
        StringBuilder ingredString = new StringBuilder();
        ArrayList<String> ingredList = (ArrayList<String>)sandwich.getIngredients();
        for (String ingred : ingredList){
            ingredString.append(ingred);
            ingredString.append(", ");
        }
        if (ingredString.length() != 0){
            ingredTextView.setText(ingredString.substring(0, ingredString.length()-2));
        }
        else ingredTextView.setText("");

        TextView placeTextView = findViewById(R.id.origin_tv);
        placeTextView.setText(sandwich.getPlaceOfOrigin());

        TextView descTextView = findViewById(R.id.description_tv);
        descTextView.setText(sandwich.getDescription());
    }
}
