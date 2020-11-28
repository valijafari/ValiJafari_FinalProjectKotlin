package com.example.valijafari_finalproject

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.valijafari_finalproject.Adapter_omdb_api.ItemClickListener
import com.google.gson.GsonBuilder
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import java.util.*

class MainActivity : AppCompatActivity(), ItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            val imgProfile = findViewById<ImageView>(R.id.imgProfile)
            imgProfile.setOnClickListener {
                val Dr = findViewById<View>(R.id.activity_main) as DrawerLayout
                Dr.openDrawer(Gravity.RIGHT)
            }
            val btnFavoriteList = findViewById<Button>(R.id.btnFavoriteList)
            btnFavoriteList.setOnClickListener {
                val intentFavoriteList = Intent(this@MainActivity, FavoriteList::class.java)
                startActivity(intentFavoriteList)
            }
            val btnAbout = findViewById<Button>(R.id.btnAbout)
            btnAbout.setOnClickListener {
                val intentAbout = Intent(this@MainActivity, ActivityAbout::class.java)
                startActivity(intentAbout)
            }
            search("s=matrix")
            val btn_Search = findViewById<ImageView>(R.id.btn_Search)
            btn_Search.setOnClickListener(View.OnClickListener {
                val txt_search = findViewById<TextView>(R.id.txt_Search)
                if (txt_search.text.toString().trim { it <= ' ' }.length <= 3) {
                    Toast.makeText(this@MainActivity, "لطفا  عنوان  فیلم را وارد کنید", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                search("S=" + txt_search.text.toString().trim { it <= ' ' })
            })
        } catch (ex: Exception) {
            Toast.makeText(this@MainActivity, ex.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemClick(imdbID: String?) {
        if (imdbID != null) {
            searchDetail(imdbID)
        }
    }

    fun fillAdapterDetail(dto_OmdbClassDetail: OmdbDetailClass?) {
        val intent = Intent(this@MainActivity, ShowInformationFilm::class.java)
        intent.putExtra("dto_OmdbClassDetail", dto_OmdbClassDetail)
        startActivity(intent)
    }

    fun searchDetail(imdbID: String) {
        //http://www.omdbapi.com/?i=tt0382026&apikey=fcf181e5
        //"http://www.omdbapi.com/?S=matrix&apikey=fcf181e5"
        //"http://www.omdbapi.com/?" + str_urlParam + "&apikey=fcf181e5"
        val client = AsyncHttpClient()
        client["http://www.omdbapi.com/?i=$imdbID&apikey=fcf181e5", null, object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseString: String, throwable: Throwable) {
                Toast.makeText(this@MainActivity, "مشکل", Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseString: String) {
                val builder = GsonBuilder()
                builder.setPrettyPrinting()
                val gson = builder.create()
                val dto_OmdbClassDetail = gson.fromJson(responseString, OmdbDetailClass::class.java)
                if (dto_OmdbClassDetail.response!!.toUpperCase() == "false".toUpperCase()) {
                    Toast.makeText(this@MainActivity, "مشکل", Toast.LENGTH_SHORT).show()
                }
                fillAdapterDetail(dto_OmdbClassDetail)
            }
        }]
    }

    ///-------
    fun search(str_urlParam: String) {
        //http://www.omdbapi.com/?i=tt0382026&apikey=fcf181e5
        //"http://www.omdbapi.com/?s=matrix&apikey=fcf181e5"
        //"http://www.omdbapi.com/?" + str_urlParam + "&apikey=fcf181e5"
        val client = AsyncHttpClient()
        client["http://www.omdbapi.com/?$str_urlParam&apikey=4311c16f", null, object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseString: String, throwable: Throwable) {
                val dto_OmdbClass = OmdbClass()
                fillAdapter(dto_OmdbClass)
            }

            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseString: String) {
                val builder = GsonBuilder()
                builder.setPrettyPrinting()
                val gson = builder.create()
                //Type listType = new TypeToken<ArrayList<OmdbClass>>(){}.getType();
                //ArrayList lst_OmdbClass = gson.fromJson(responseString, listType);
                val dto_OmdbClass = gson.fromJson(responseString, OmdbClass::class.java)
               val responserr= dto_OmdbClass.response?.toUpperCase()
                if (responserr == "false".toUpperCase()) {
                    Toast.makeText(this@MainActivity, "با عنوان وارد شده ،فیلمی پیدا نشد.", Toast.LENGTH_SHORT).show()
                    dto_OmdbClass.search = ArrayList()
                }
                fillAdapter(dto_OmdbClass)
            }
        }]
    }

    fun fillAdapter(dto_OmdbClass: OmdbClass) {

        //recyclerViewJson.LayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerViewJson.setLayoutManager(layoutManager);
        try {
            val recyclerViewJson = findViewById<RecyclerView>(R.id.mainRecycleView)
            recyclerViewJson.setHasFixedSize(true)
            //            dto_OmdbClass.getSearch().get(0).getImdbID();
            recyclerViewJson.layoutManager = LinearLayoutManager(this@MainActivity)
            val adapterJson = dto_OmdbClass.search?.let { Adapter_omdb_api(it, applicationContext) }
            if (adapterJson != null) {
                adapterJson.setClickListener(this)
            }
            recyclerViewJson.adapter = adapterJson
            val dividerItemDecoration = DividerItemDecoration(recyclerViewJson.context, LinearLayoutManager(this).orientation)
            recyclerViewJson.addItemDecoration(dividerItemDecoration)
        } catch (ex: Exception) {
            Toast.makeText(this, "در حال حاضر به اینترنت دسترسی ندارید،لطفا ارتباط اینترنت خود را چک کنید", Toast.LENGTH_SHORT).show()
        }
    } //    @Override
    //    public boolean onCreateOptionsMenu(Menu menu) {
    //        //super.onCreateOptionsMenu(menu);
    //        getMenuInflater().inflate(R.menu.favoritemenu, menu);
    //        return true;
    //    }
    //
    //    @Override
    //    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    //        switch (item.getItemId()) {
    //            case R.id.mnu_Favorite: {
    //                Intent intent = new Intent(MainActivity.this, FavoriteList.class);
    //                startActivity(intent);
    //                break;
    //            }
    //        }
    //        return true;
    //    }
    // allows clicks events to be caught
}