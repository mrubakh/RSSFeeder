package com.uzias.rssreader.feed.domain.usecase

import com.uzias.rssreader.core.domain.InvalidData
import com.uzias.rssreader.core.domain.UseCase
import com.uzias.rssreader.feed.domain.model.Rss
import io.reactivex.Completable
import io.reactivex.Observable

interface DeleteRss: UseCase<Completable> {
    fun setUrl(url: String = InvalidData.UNINITIALIZED.getString()) : DeleteRss
}