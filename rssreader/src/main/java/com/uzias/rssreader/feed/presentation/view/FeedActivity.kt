package com.uzias.rssreader.feed.presentation.view

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.uzias.rssreader.R
import com.uzias.rssreader.core.presentation.BaseActivity
import com.uzias.rssreader.core.presentation.BasePresenter
import com.uzias.rssreader.core.presentation.RecyclerViewWithFeedback
import com.uzias.rssreader.feed.di.DaggerFeedComponent
import com.uzias.rssreader.feed.di.FeedModule
import com.uzias.rssreader.feed.presentation.RssListener
import com.uzias.rssreader.feed.presentation.adapter.ItemAdapter
import com.uzias.rssreader.feed.presentation.adapter.RssAdapter
import com.uzias.rssreader.feed.presentation.model.PresentationRss
import com.uzias.rssreader.feed.presentation.presenter.FeedPresenter
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.custom_dialog_input_url.view.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject
import com.uzias.rssreader.feed.presentation.ItemListener
import com.uzias.rssreader.feed.presentation.model.PresentationItem
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.Patterns
import android.webkit.URLUtil
import android.widget.Toast
import com.roger.catloadinglibrary.CatLoadingView
import kotlinx.android.synthetic.main.custom_dialog_input_url.*


class FeedActivity : BaseActivity(), FeedView, RssListener, ItemListener {

    private lateinit var progress: CatLoadingView

    private val drawerToggle: ActionBarDrawerToggle by lazy {
        ActionBarDrawerToggle(this, drawerlayout,
                R.string.app_name, R.string.app_name)
    }

    private val rssAdapter: RssAdapter by lazy {
        RssAdapter(this, mutableListOf(), R.layout.list_item_rss, this)
    }

    private val itemAdapter: ItemAdapter by lazy {
        ItemAdapter(this, mutableListOf(), R.layout.list_item_feed_item, this)
    }

    @Inject
    lateinit var feedPresenter: FeedPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerToggle.isDrawerIndicatorEnabled = true
        button_add.setOnClickListener { feedPresenter.clickedButtonAdd() }

        recyclerview_rss.getRecyclerView().adapter = rssAdapter
        Log.d("ITEMS_COUNT", rssAdapter.getItemCount().toString())
        recyclerview_rss.getRecyclerView().layoutManager = LinearLayoutManager(this)
        recyclerview_rss.setState(RecyclerViewWithFeedback.State.EMPTY)

        recyclerview_items.getRecyclerView().adapter = itemAdapter
        recyclerview_items.getRecyclerView().layoutManager = LinearLayoutManager(this)
        recyclerview_items.setState(RecyclerViewWithFeedback.State.EMPTY)
        swiperefreshlayout.setOnRefreshListener { feedPresenter.refreshFeedActioned() }

        rssAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                recyclerview_rss.setState(RecyclerViewWithFeedback.State.FILLED)
                recyclerview_items.setState(RecyclerViewWithFeedback.State.FILLED)
            }
        })

        progress = CatLoadingView()
        progress.setText(getString(R.string.commons_loading))

//        Log.d("ITEMS_COUNT", rssAdapter.getItemCount().toString())
//        if (rssAdapter.lastItem != null){
//            clicked(rssAdapter.lastItem as PresentationRss)
//        }

    }

    override fun injectDependencies() {
        DaggerFeedComponent.builder()
                .appComponent(getAppComponent())
                .feedModule(FeedModule())
                .build()
                .inject(this)
    }

    override fun getPresenter(): BasePresenter = feedPresenter

    override fun showLoading() {
        progress.show(supportFragmentManager, "")
    }

    override fun dismissLoading() {
        progress.dismiss()
    }

    override fun addRss(presentationRss: PresentationRss) {
        rssAdapter.addItem(presentationRss)
        clicked(presentationRss)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
//        Log.d("ITEMS_COUNT_POST_CREATE", rssAdapter.getItemCount().toString())
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (drawerToggle.onOptionsItemSelected(item)) true
        else super.onOptionsItemSelected(item)

    override fun openDialogInputUrl() {
        val builder = AlertDialog.Builder(this)
        val viewInflated = layoutInflater.inflate(R.layout.custom_dialog_input_url, null)
        builder.setView(viewInflated)

        /*isEditMode = ((arguments != null) && arguments!!.containsKey(FeedActivity.KEY_RSS))
        if (isEditMode) {
            builder.setTitle("Edit url")
            var rss: PresentationRss = (arguments?.getSerializable(ScrollingActivity.KEY_RSS) as PresentationRss)

            etUrl.setText(rss.url)
        } */

        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            var url = viewInflated.etUrl.text.toString()
            if(Patterns.WEB_URL.matcher(url).matches() && URLUtil.isValidUrl(url)) {
                feedPresenter.clickedButtonOkInputUrl(url)
                dialog.dismiss()
            }
            else {
                Toast.makeText(this, "$url is not a valid url!", Toast.LENGTH_LONG).show()
                viewInflated.etUrl.error = "This Url is not valid!"
            }
        }
        builder.setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    override fun clicked(presentationRss: PresentationRss) {
        feedPresenter.setPresentationSelected(presentationRss)
        itemAdapter.clear()
        drawerlayout.closeDrawers()
        presentationRss.items.forEach {
            itemAdapter.addItem(it)
        }
    }

    override fun clicked(presentationItem: PresentationItem) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(presentationItem.url))
    }

    override fun dismissSwipeLoading() {
        if (swiperefreshlayout.isRefreshing){
            swiperefreshlayout.isRefreshing = false
        }
    }

    override fun removeRss(presentationRssSelected: PresentationRss) {
        feedPresenter.clickedButtonDeleteUrl(presentationRssSelected)
        rssAdapter.removeItem(item = presentationRssSelected)
    }

    override fun setSelectedRss(it: PresentationRss) {
        clicked(it)
    }

}
