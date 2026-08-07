package com.example.navcue

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class NavAccessibilityService : AccessibilityService() {

    companion object {
        @Volatile
        var isRunning: Boolean = false
    }

    private var lastSignature = ""

    override fun onServiceConnected() {
        super.onServiceConnected()
        isRunning = true
    }

    override fun onDestroy() {
        isRunning = false
        super.onDestroy()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        // Google Maps Android package.
        if (event.packageName?.toString() != "com.google.android.apps.maps") return

        val root = rootInActiveWindow ?: return
        val texts = LinkedHashSet<String>()
        collectVisibleText(root, texts)

        // Keep only useful-looking navigation UI text.
        val useful = texts.filter { looksLikeNavigationText(it) }
        if (useful.isEmpty()) return

        val signature = useful.joinToString(" | ")
        if (signature == lastSignature) return
        lastSignature = signature

        NavCueStore.add(signature)
    }

    private fun collectVisibleText(
        node: AccessibilityNodeInfo,
        out: MutableSet<String>
    ) {
        node.text?.toString()
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?.let(out::add)

        node.contentDescription?.toString()
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?.let(out::add)

        for (i in 0 until node.childCount) {
            node.getChild(i)?.let { child ->
                collectVisibleText(child, out)
                child.recycle()
            }
        }
    }

    private fun looksLikeNavigationText(s: String): Boolean {
        val x = s.lowercase()
        val directionWords = listOf(
            "turn", "left", "right", "straight", "u-turn",
            "roundabout", "exit", "keep", "merge", "ramp",
            "continue", "destination", "arrive", "take"
        )
        val distanceUnits = listOf("m", "km", "ft", "mi", "meter", "metre", "mile")

        return directionWords.any { x.contains(it) } ||
               distanceUnits.any { x.contains(it) } ||
               x.matches(Regex(".*\\d+\\s*(m|km|ft|mi).*"))
    }
}
