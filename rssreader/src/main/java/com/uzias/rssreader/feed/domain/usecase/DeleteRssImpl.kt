package com.uzias.rssreader.feed.domain.usecase

import com.uzias.rssreader.feed.domain.model.Rss
import com.uzias.rssreader.feed.domain.repository.FeedRepository
import io.reactivex.Completable
import io.reactivex.Observable

class DeleteRssImpl(val repository: FeedRepository) : DeleteRss {

    private lateinit var url: String

    override fun setUrl(url: String): DeleteRss {
        this.url = url
        return this
    }

    override fun run(): Completable = repository.deleteRss(url)

}