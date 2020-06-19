package com.epam.drill.logger.api.internal

import com.epam.drill.logger.api.*

internal class NamedLogger(
    val name: String,
    private val logLevel: () -> LogLevel = { LogLevel.ERROR },
    private val appender: LogAppender
) : Logger {
    override fun trace(t: Throwable?, marker: Marker?, msg: () -> Any?) {
        log(LogLevel.TRACE, t = t, marker = marker, msg = msg)
    }

    override fun debug(t: Throwable?, marker: Marker?, msg: () -> Any?) {
        log(LogLevel.DEBUG, t = t, marker = marker, msg = msg)
    }

    override fun info(t: Throwable?, marker: Marker?, msg: () -> Any?) {
        log(LogLevel.INFO, t = t, marker = marker, msg = msg)
    }

    override fun warn(t: Throwable?, marker: Marker?, msg: () -> Any?) {
        log(LogLevel.WARN, t = t, marker = marker, msg = msg)
    }

    override fun error(t: Throwable?, marker: Marker?, msg: () -> Any?) {
        log(LogLevel.ERROR, t = t, marker = marker, msg = msg)
    }

    private fun log(
        level: LogLevel,
        t: Throwable? = null,
        marker: Marker? = null,
        msg: () -> Any?
    ) {
        if (logLevel() <= level) {
            appender(name, level, t, marker, msg)
        }
    }
}
