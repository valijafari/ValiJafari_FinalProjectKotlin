package com.example.valijafari_finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class InformationFilmFragment extends Fragment {

    OmdbDetailClass dto_OmdbClassDetail= new OmdbDetailClass() ;
    boolean ExsitsFilm=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_information_film, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dto_OmdbClassDetail = (OmdbDetailClass) this.getArguments().getSerializable("dto_OmdbClassDetail");

        TextView txtyearDetail = view.findViewById(R.id.yeardetail);
        TextView txtTitleDetail = view.findViewById(R.id.titledetail);

        TextView txtruntimeDetail = view.findViewById(R.id.runtimedetail);
        TextView txtGenreDetail = view.findViewById(R.id.Genredetail);
        TextView txtWriterDetail = view.findViewById(R.id.Writerdetail);
        TextView txtActorsDetail = view.findViewById(R.id.Actorsdetail);
        TextView txtLanguageDetail = view.findViewById(R.id.Languagedetail);
        TextView txtimdbRatingDetail = view.findViewById(R.id.imdbRatingdetail);

        ImageView poster = view.findViewById(R.id.poster);
        ImageView FavoriteimageView = view.findViewById(R.id.AddToFavorite);

        txtyearDetail.setText(dto_OmdbClassDetail.getYear().toString());
        txtTitleDetail.setText(dto_OmdbClassDetail.getTitle().toString());

        txtruntimeDetail.setText(dto_OmdbClassDetail.getRuntime().toString());
        txtGenreDetail.setText(dto_OmdbClassDetail.getGenre().toString());
        txtWriterDetail.setText(dto_OmdbClassDetail.getWriter().toString());
        txtActorsDetail.setText(dto_OmdbClassDetail.getActors().toString());
        txtLanguageDetail.setText(dto_OmdbClassDetail.getLanguage().toString());
        txtimdbRatingDetail.setText(dto_OmdbClassDetail.getImdbRating().toString());

        Glide.with(view.getContext()).load(dto_OmdbClassDetail.getPoster()).into(poster);

        OmdbRepository database = new OmdbRepository(view.getContext(), "FilmYab1", null, 1);
        if  (database.ExsitsFilm(dto_OmdbClassDetail.getImdbID().toString())==true)
        {
            ExsitsFilm=true;
            FavoriteimageView.setImageResource(R.drawable.uninstall);
        }
        else
        {
            ExsitsFilm=false;
            FavoriteimageView.setImageResource(R.drawable.tagicon);
        }

        FavoriteimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OmdbRepository database = new OmdbRepository(v.getContext(), "FilmYab1", null, 1);
                if  (ExsitsFilm) {
                    database.DeleteOmdbInformation( dto_OmdbClassDetail.getImdbID().toString());
                    ImageView FavoriteimageView = v.findViewById(R.id.AddToFavorite);
                    FavoriteimageView.setImageResource(R.drawable.tagicon);
                    Toast.makeText(v.getContext(), "این فیلم از لیست علاقه مندی های شما حذف شد.", Toast.LENGTH_LONG).show();
                    ExsitsFilm=false;
                }
                else {
                    database.insertOmdbInformation(dto_OmdbClassDetail.getTitle().toString(), dto_OmdbClassDetail.getImdbID().toString(),
                            dto_OmdbClassDetail.getYear().toString(), dto_OmdbClassDetail.getPoster().toString(),dto_OmdbClassDetail.getRuntime().toString()
                            ,dto_OmdbClassDetail.getGenre().toString(),dto_OmdbClassDetail.getWriter().toString(),dto_OmdbClassDetail.getActors().toString(),
                            dto_OmdbClassDetail.getLanguage().toString(),dto_OmdbClassDetail.getImdbRating().toString());
                    ImageView FavoriteimageView = v.findViewById(R.id.AddToFavorite);
                    FavoriteimageView.setImageResource(R.drawable.uninstall);
                    Toast.makeText(v.getContext(), "این فیلم به لیست علاقه مندی های شما اضافه شد.", Toast.LENGTH_LONG).show();
                    ExsitsFilm=true;
                }


            }
        });
    }

}