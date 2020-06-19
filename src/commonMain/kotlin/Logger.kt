package com.epam.drill.logger.api

interface LoggerFactory {
    fun logger(name: String): Logger
}

interface Marker {
    val name: String
}

typealias LogAppender = (name: String, level: LogLevel, t: Throwable?, marker: Marker?, msg: () -> Any?) -> Unit

interface Logger {
    fun trace(t: Throwable? = null, marker: Marker? = null, msg: () -> Any?)
    fun debug(t: Throwable? = null, marker: Marker? = null, msg: () -> Any?)
    fun info(t: Throwable? = null, marker: Marker? = null, msg: () -> Any?)
    fun warn(t: Throwable? = null, marker: Marker? = null, msg: () -> Any?)
    fun error(t: Throwable? = null, marker: Marker? = null, msg: () -> Any?)
}
