package dev.zgrv.binaryUtils.extensions


inline fun <T> Iterable<T>.applyForEach(action: T.() -> Unit) {
    forEach { it.action() }
}