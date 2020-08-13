package com.example.valijafari_finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class ShowInformationFilm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_information_film);
        InformationFilmFragment teamFragment = new InformationFilmFragment();

        Bundle args = new Bundle();
        args.putSerializable("dto_OmdbClassDetail",  getIntent().getSerializableExtra("dto_OmdbClassDetail"));
        teamFragment.setArguments(args);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, teamFragment);
        ft.commit();
    }
}