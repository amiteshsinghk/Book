package org.amitesh.book.di

import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module
import org.koin.dsl.module
import io.ktor.client.engine.darwin.Darwin


actual val platformModule: Module
    get() = module {
        single<HttpClientEngine>{
            Darwin.create()
        }
    }