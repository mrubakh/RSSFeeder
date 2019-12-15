package com.uzias.rssreader.feed.presentation.adapter

import com.uzias.rssreader.R
import android.content.Context
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.uzias.rssreader.core.presentation.BaseAdapter
import com.uzias.rssreader.feed.data.repository.FeedRepositoryImpl
import com.uzias.rssreader.feed.presentation.RssListener
import com.uzias.rssreader.feed.presentation.model.PresentationRss
import com.uzias.rssreader.feed.presentation.view.FeedActivity
import com.uzias.rssreader.feed.presentation.view.RssViewHolder
import io.reactivex.Completable
import java.net.URL

class RssAdapter(
        context: Context,
        data: MutableList<PresentationRss>,
        layout: Int, val rssListener: RssListener
) : BaseAdapter<PresentationRss>(context, data, layout) {

    override fun getViewHolder(view: View) = RssViewHolder(view)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RssViewHolder) {
            val rss = getItem(position)

            rss?.let {
                with(holder){
                    if (URLUtil.isValidUrl(it.url)) {
                        urlView.text = URL(it.url).host
                    } else {
                        holder.itemView.visibility = View.GONE
                    }
                    Picasso.with(context).load("https://besticon-demo.herokuapp.com/icon?url="+it.url+"&size=80..120..200").into(imageView)
                    holder.itemView.setOnClickListener {
                        rssListener.clicked(rss)
                    }

                    holder.itemView.setOnLongClickListener{
                        showPopup(holder.itemView, rss)
                        return@setOnLongClickListener true
                    }
                }
            }
        }
    }

    private fun showPopup(view: View, presentationRss: PresentationRss) {
        var popup: PopupMenu? = null
        popup = PopupMenu(context, view)
        popup.inflate(R.menu.pop_up)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.delete -> {
                    (context as FeedActivity).removeRss(presentationRss)
                    notifyDataSetChanged()
                    Toast.makeText(context, "RSS deleted", Toast.LENGTH_SHORT).show()
                }
            }
            true
        })

        popup.show()
    }

}
