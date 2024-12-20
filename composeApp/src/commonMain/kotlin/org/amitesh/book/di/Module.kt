package org.amitesh.book.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.amitesh.book.books.data.database.DatabaseFactory
import org.amitesh.book.books.data.database.FavouriteBookDb
import org.amitesh.book.books.data.network.KtorRemoteBookDataSource
import org.amitesh.book.books.data.network.RemoteBookDataSource
import org.amitesh.book.books.data.repository.DefaultBookRepository
import org.amitesh.book.books.domain.repository.BookRepository
import org.amitesh.book.books.presentation.book_detail.BookDetailViewModel
import org.amitesh.book.books.presentation.book_list.BookListViewModel
import org.amitesh.book.books.presentation.book_list.SelectedViewModel
import org.amitesh.book.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single {
        HttpClientFactory.create(get())
    }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<FavouriteBookDb>().dao }

    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedViewModel)
    viewModelOf(::BookDetailViewModel)
}