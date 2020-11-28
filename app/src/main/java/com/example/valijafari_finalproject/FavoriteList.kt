package com.example.valijafari_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteList extends AppCompatActivity implements   Adapter_omdb_api.ItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);
        search();
    }

    @Override
    protected void onResume() {
        super.onResume();
        search();
    }

    public void search(){
        OmdbRepository database = new OmdbRepository(this, "FilmYab1", null, 1);
        OmdbClass dto_OmdbClass = database.getAllOmdbInformation_Favorite();
        try
        {
            dto_OmdbClass.getSearch().get(0).getImdbID();
            RecyclerView recyclerViewJson = findViewById(R.id.mainRecycleView);
            recyclerViewJson.setVisibility(RecyclerView.VISIBLE);

            TextView txtempty=findViewById(R.id.empty);
            txtempty.setVisibility(TextView.GONE);
        }
        catch (Exception ex){
            Toast.makeText(FavoriteList.this, "فیلمی را جزو علاقه مندی های خود انتخاب نکرده اید", Toast.LENGTH_SHORT).show();
            RecyclerView recyclerViewJson = findViewById(R.id.mainRecycleView);
            recyclerViewJson.setVisibility(RecyclerView.GONE);

            TextView txtempty=findViewById(R.id.empty);
            txtempty.setVisibility(TextView.VISIBLE);
        }
        fillAdapter(dto_OmdbClass);
    }

    public void fillAdapter(OmdbClass dto_OmdbClass){
        RecyclerView recyclerViewJson = findViewById(R.id.mainRecycleView);
        recyclerViewJson.setHasFixedSize(true);
        //recyclerViewJson.LayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerViewJson.setLayoutManager(layoutManager);
        recyclerViewJson.setLayoutManager(new LinearLayoutManager(FavoriteList.this));
        Adapter_omdb_api adapterJson = new Adapter_omdb_api(dto_OmdbClass.getSearch(), FavoriteList.this);
        adapterJson.setClickListener(this);
        recyclerViewJson.setAdapter(adapterJson);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewJson.getContext(), new LinearLayoutManager(this).getOrientation());
        recyclerViewJson.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onItemClick(String imdbID) {
        searchDetail(imdbID);
    }

    public void searchDetail(String imdbID){
        OmdbRepository database = new OmdbRepository(this, "FilmYab1", null, 1);
        OmdbDetailClass dto_omdbDetailClass = database.GetRowOmdbInformation(imdbID);
//        if (dto_omdbDetailClass.getResponse().toUpperCase().equals("false".toUpperCase())){
//            Toast.makeText(FavoriteList.this, "مشکل", Toast.LENGTH_SHORT).show();
//        }
        fillAdapterDetail(dto_omdbDetailClass);
    }

    public void fillAdapterDetail(OmdbDetailClass dto_OmdbClassDetail){
        Intent intent = new Intent(FavoriteList.this, ShowInformationFilm.class);
        intent.putExtra("dto_OmdbClassDetail", dto_OmdbClassDetail);
        startActivity(intent);
    }
}