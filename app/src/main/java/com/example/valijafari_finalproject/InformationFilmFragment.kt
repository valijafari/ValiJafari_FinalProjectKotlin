package com.example.valijafari_finalproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class InformationFilmFragment : Fragment() {
    var dto_OmdbClassDetail: OmdbDetailClass? = OmdbDetailClass()
    var ExsitsFilm = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_information_film, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dto_OmdbClassDetail = this.arguments!!.getSerializable("dto_OmdbClassDetail") as OmdbDetailClass?
        val txtyearDetail = view.findViewById<TextView>(R.id.yeardetail)
        val txtTitleDetail = view.findViewById<TextView>(R.id.titledetail)
        val txtruntimeDetail = view.findViewById<TextView>(R.id.runtimedetail)
        val txtGenreDetail = view.findViewById<TextView>(R.id.Genredetail)
        val txtWriterDetail = view.findViewById<TextView>(R.id.Writerdetail)
        val txtActorsDetail = view.findViewById<TextView>(R.id.Actorsdetail)
        val txtLanguageDetail = view.findViewById<TextView>(R.id.Languagedetail)
        val txtimdbRatingDetail = view.findViewById<TextView>(R.id.imdbRatingdetail)
        val poster = view.findViewById<ImageView>(R.id.poster)
        val FavoriteimageView = view.findViewById<ImageView>(R.id.AddToFavorite)
        txtyearDetail.text = dto_OmdbClassDetail!!.year.toString()
        txtTitleDetail.text = dto_OmdbClassDetail!!.title.toString()
        txtruntimeDetail.text = dto_OmdbClassDetail!!.runtime.toString()
        txtGenreDetail.text = dto_OmdbClassDetail!!.genre.toString()
        txtWriterDetail.text = dto_OmdbClassDetail!!.writer.toString()
        txtActorsDetail.text = dto_OmdbClassDetail!!.actors.toString()
        txtLanguageDetail.text = dto_OmdbClassDetail!!.language.toString()
        txtimdbRatingDetail.text = dto_OmdbClassDetail!!.imdbRating.toString()
        if (dto_OmdbClassDetail!!.poster != "N/A") {
            Glide.with(view.context).load(dto_OmdbClassDetail!!.poster).into(poster)
        } else poster.setImageResource(R.drawable.noimageavaible)
        val database = OmdbRepository(view.context, "FilmYab1", null, 1)
        if (database.ExsitsFilm(dto_OmdbClassDetail!!.imdbID.toString()) == true) {
            ExsitsFilm = true
            FavoriteimageView.setImageResource(R.mipmap.uninstall)
        } else {
            ExsitsFilm = false
            FavoriteimageView.setImageResource(R.mipmap.tagicon)
        }
        FavoriteimageView.setOnClickListener { v ->
            val database = OmdbRepository(v.context, "FilmYab1", null, 1)
            ExsitsFilm = if (ExsitsFilm) {
                database.DeleteOmdbInformation(dto_OmdbClassDetail!!.imdbID.toString())
                val FavoriteimageView = v.findViewById<ImageView>(R.id.AddToFavorite)
                FavoriteimageView.setImageResource(R.mipmap.tagicon)
                Toast.makeText(v.context, "این فیلم از لیست علاقه مندی های شما حذف شد.", Toast.LENGTH_LONG).show()
                false
            } else {
                database.insertOmdbInformation(dto_OmdbClassDetail!!.title.toString(), dto_OmdbClassDetail!!.imdbID.toString(),
                        dto_OmdbClassDetail!!.year.toString(), dto_OmdbClassDetail!!.poster.toString(), dto_OmdbClassDetail!!.runtime.toString(), dto_OmdbClassDetail!!.genre.toString(), dto_OmdbClassDetail!!.writer.toString(), dto_OmdbClassDetail!!.actors.toString(),
                        dto_OmdbClassDetail!!.language.toString(), dto_OmdbClassDetail!!.imdbRating.toString())
                val FavoriteimageView = v.findViewById<ImageView>(R.id.AddToFavorite)
                FavoriteimageView.setImageResource(R.mipmap.uninstall)
                Toast.makeText(v.context, "این فیلم به لیست علاقه مندی های شما اضافه شد.", Toast.LENGTH_LONG).show()
                true
            }
        }
    }
}