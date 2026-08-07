package com.example.navcue

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object NavCueStore {
    private val lock = Any()
    private val entries = ArrayDeque<String>()
    private const val MAX = 100

    fun add(text: String) {
        val stamp = SimpleDateFormat("HH:mm:ss", Locale.US).format(Date())
        synchronized(lock) {
            entries.addLast("[$stamp] $text")
            while (entries.size > MAX) entries.removeFirst()
        }
    }

    fun clear() {
        synchronized(lock) { entries.clear() }
    }

    fun snapshot(): String = synchronized(lock) {
        entries.joinToString("\n")
    }
}
