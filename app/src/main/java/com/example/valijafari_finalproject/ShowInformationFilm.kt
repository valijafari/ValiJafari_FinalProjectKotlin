package com.example.valijafari_finalproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ShowInformationFilm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_information_film)
        val teamFragment = InformationFilmFragment()
        val args = Bundle()
        args.putSerializable("dto_OmdbClassDetail", intent.getSerializableExtra("dto_OmdbClassDetail"))
        teamFragment.arguments = args
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frameLayout, teamFragment)
        ft.commit()
    }
}