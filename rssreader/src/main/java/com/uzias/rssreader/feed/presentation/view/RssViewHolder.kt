package com.uzias.rssreader.feed.presentation.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.list_item_rss.view.*

class RssViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val urlView: TextView = view.textview_url
    val imageView: ImageView = view.rss_favicon
}