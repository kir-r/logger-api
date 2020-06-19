package com.epam.drill.logger.api

import com.epam.drill.logger.api.internal.*

fun String.namedLogger(
    logLevel: () -> LogLevel = { LogLevel.ERROR },
    appender: LogAppender
): Logger = NamedLogger(
    name = this, logLevel = logLevel, appender = appender
)

object NopLogAppender : LogAppender {
    override fun invoke(name: String, level: LogLevel, t: Throwable?, marker: Marker?, msg: () -> Any?) {}
}
