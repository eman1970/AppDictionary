package com.example.appdictionary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class SelectedWordActivity extends AppCompatActivity {

    TextView tvSelectedWord;
    TextView tvSvenskaKort;
    TextView tvRyskaKort;
    ImageView imageView;
    TextView tvLinkSV;
    TextView tvLinkRY;
    WordModel wordModel;
    Intent intent;
    ImageButton button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_selected_word );
        tvSelectedWord = findViewById(R.id.tvSelectedWord);
        tvSvenskaKort = findViewById(R.id.tvSvenskaKort);
        tvRyskaKort = findViewById(R.id.tvRyskaKort);
        imageView = findViewById(R.id.wikiImg);
        tvLinkSV = findViewById(R.id.tvLinkSV);
        tvLinkRY = findViewById(R.id.tvLinkRY);
        intent = getIntent();
        button = findViewById( R.id.button );



        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });




        if(intent != null){

            wordModel = (WordModel) intent.getSerializableExtra("data");
            String selectedWord = wordModel.getOrdSvenska() +" / "+ wordModel.getOrdRyska();
            String svenskaKort = wordModel.getSvenskaKort();
            String ryskaKort =wordModel.getRyskaKort();
            String imgURl = wordModel.getImgURL();
            String linkSV = wordModel.getLinkSV();
            String linkRY = wordModel.getLinkRY();

            Glide.with(this).load(imgURl).centerCrop().into(imageView);
            tvSelectedWord.setText(selectedWord);
            tvSvenskaKort.setText(svenskaKort);
            tvRyskaKort.setText(ryskaKort);
            tvLinkSV.setText(linkSV);
            tvLinkRY.setText(linkRY);


        }


    }




}