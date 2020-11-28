package com.example.valijafari_finalproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapter_omdb_api(ListSearchClass: List<Search>, context: Context) : RecyclerView.Adapter<Adapter_omdb_api.ViewHolder>() {
    private val mInflater: LayoutInflater
    private val Data: List<Search>
    private val mContext: Context
    private var mClickListener: ItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.myviewholder_omdb_api, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dto_search = Data[position]
        try {
            holder.txt_Title.text = dto_search.title.toString()
            holder.txt_year.text = dto_search.year.toString()
            holder.txt_imdbID.text = dto_search.imdbID.toString()

//            holder.txt_runtime.setText(String.valueOf(dto_search.g()));
//            holder.txt_Genre.setText(String.valueOf(dto_search.getYear()));
//            holder.txt_Writer.setText(String.valueOf(dto_search.getImdbID()));
//            holder.txt_Actors.setText(String.valueOf(dto_search.getTitle()));
//            holder.txt_Language.setText(String.valueOf(dto_search.getYear()));
//            holder.txt_imdbRating.setText(String.valueOf(dto_search.getImdbID()));
            //holder.txt_rated.setText(String.valueOf(dto_search.getRated()));
            try {
                if (dto_search.poster != "N/A") {
                    Glide.with(mContext).load(dto_search.poster).into(holder.img_poster)
                } else holder.img_poster.setImageResource(R.drawable.noimageavaible)
            } catch (ex: Exception) {
                holder.img_poster.background = ContextCompat.getDrawable(holder.img_poster.context, R.drawable.noimageavaible)
            }
        } catch (ex: Exception) {
            val xxx = ex.message
        }
    }

    override fun getItemCount(): Int {
        return Data.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        //        Button myButton;
        var txt_Title: TextView
        var txt_year: TextView
        var txt_imdbID: TextView

        //        TextView txt_runtime;
        //        TextView txt_Genre;
        //        TextView txt_Writer;
        //        TextView txt_Actors;
        //        TextView txt_Language;
        //        TextView txt_imdbRating;
        var btnMore: TextView
        var img_poster: ImageView
        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(txt_imdbID.text.toString())
        }

        init {
            txt_Title = itemView.findViewById(R.id.title)
            txt_year = itemView.findViewById(R.id.year)
            txt_imdbID = itemView.findViewById(R.id.imdbID)
            img_poster = itemView.findViewById(R.id.imgShort)
            btnMore = itemView.findViewById(R.id.btnMore)
            btnMore.setOnClickListener(this)
            img_poster.setOnClickListener(this)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(imdbID: String?)
    }

    init {
        mInflater = LayoutInflater.from(context)
        Data = ListSearchClass
        mContext = context
    }
}