package org.amitesh.book

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform