package com.herehs.mdnotes

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform