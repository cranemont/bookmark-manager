package com.kldaji.bookmark_manager.data.source.remote

import com.kldaji.bookmark_manager.data.entity.BookmarkBody
import com.kldaji.bookmark_manager.data.entity.BookmarkResponse

interface BookmarkRemoteDataSource {

	suspend fun getBookmarkResponse(bookmarkBody: BookmarkBody): com.kldaji.bookmark_manager.util.Result<BookmarkResponse>
}