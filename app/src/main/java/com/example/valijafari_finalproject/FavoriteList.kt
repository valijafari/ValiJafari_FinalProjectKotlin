package com.example.valijafari_finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.valijafari_finalproject.Adapter_omdb_api.ItemClickListener

class FavoriteList : AppCompatActivity(), ItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)
        search()
    }

    override fun onResume() {
        super.onResume()
        search()
    }

    fun search() {
        val database = OmdbRepository(this, "FilmYab1", null, 1)
        val dto_OmdbClass = database.allOmdbInformation_Favorite
        try {
            dto_OmdbClass!!.search?.get(0)?.imdbID
            val recyclerViewJson = findViewById<RecyclerView>(R.id.mainRecycleView)
            recyclerViewJson.visibility = RecyclerView.VISIBLE
            val txtempty = findViewById<TextView>(R.id.empty)
            txtempty.visibility = TextView.GONE
        } catch (ex: Exception) {
            Toast.makeText(this@FavoriteList, "فیلمی را جزو علاقه مندی های خود انتخاب نکرده اید", Toast.LENGTH_SHORT).show()
            val recyclerViewJson = findViewById<RecyclerView>(R.id.mainRecycleView)
            recyclerViewJson.visibility = RecyclerView.GONE
            val txtempty = findViewById<TextView>(R.id.empty)
            txtempty.visibility = TextView.VISIBLE
        }
        fillAdapter(dto_OmdbClass)
    }

    fun fillAdapter(dto_OmdbClass: OmdbClass) {
        val recyclerViewJson = findViewById<RecyclerView>(R.id.mainRecycleView)
        recyclerViewJson.setHasFixedSize(true)
        //recyclerViewJson.LayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerViewJson.setLayoutManager(layoutManager);
        recyclerViewJson.layoutManager = LinearLayoutManager(this@FavoriteList)
        val adapterJson = dto_OmdbClass.search?.let { Adapter_omdb_api(it, this@FavoriteList) }
        adapterJson?.setClickListener(this)
        recyclerViewJson.adapter = adapterJson
        val dividerItemDecoration = DividerItemDecoration(recyclerViewJson.context, LinearLayoutManager(this).orientation)
        recyclerViewJson.addItemDecoration(dividerItemDecoration)
    }

    override fun onItemClick(imdbID: String?) {
        searchDetail(imdbID)
    }

    fun searchDetail(imdbID: String?) {
        val database = OmdbRepository(this, "FilmYab1", null, 1)
        val dto_omdbDetailClass = imdbID?.let { database.GetRowOmdbInformation(it) }
        //        if (dto_omdbDetailClass.getResponse().toUpperCase().equals("false".toUpperCase())){
//            Toast.makeText(FavoriteList.this, "مشکل", Toast.LENGTH_SHORT).show();
//        }
        fillAdapterDetail(dto_omdbDetailClass)
    }

    fun fillAdapterDetail(dto_OmdbClassDetail: OmdbDetailClass?) {
        val intent = Intent(this@FavoriteList, ShowInformationFilm::class.java)
        intent.putExtra("dto_OmdbClassDetail", dto_OmdbClassDetail)
        startActivity(intent)
    }
}