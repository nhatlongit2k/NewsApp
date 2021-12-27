package com.example.newsapp

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.request.RequestListener
import com.example.newsapp.models.Article
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.target.Target
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Adapter(var articles: ArrayList<Article>, var context: Context): RecyclerView.Adapter<Adapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = articles[position]
        holder.tvTitle.setText(currentItem.title)
        holder.tvDesc.setText(currentItem.description)
        holder.tvAuthor.setText(currentItem.author)
        holder.tvPublishedAt.setText(convertDate(currentItem.publishedAt))
        //holder.tvSource.setText(currentItem.source.name)
        //holder.tvTime.setText(currentItem.)
        if(context!=null){
//            Glide.with(context).load(currentItem.urlToImage).into(holder.imgView)
            Glide.with(context)
                .load(currentItem.urlToImage)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.progressBar.visibility = View.GONE
                        return false
                    }

                })
                .error(R.drawable.no_image)
                .into(holder.imgView)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun convertDate(date: String): String{
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val output: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm aa")
        val d = sdf.parse(date)
        val formattedTime = output.format(d)
        return formattedTime
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvDesc: TextView = itemView.findViewById(R.id.tv_desc)
        val tvAuthor: TextView = itemView.findViewById(R.id.tv_author)
        val tvPublishedAt: TextView = itemView.findViewById(R.id.tv_published_at)
        val imgView: ImageView = itemView.findViewById(R.id.img)
        val progressBar: ProgressBar = itemView.findViewById(R.id.pgb_load_photo)

        init {
            itemView.setOnClickListener {
//                articles[layoutPosition]
                val intent: Intent = Intent(context, NewsActivity::class.java)
                intent.putExtra("linkUrl", articles[layoutPosition].url)
                context.startActivity(intent)
            }
        }
    }
}