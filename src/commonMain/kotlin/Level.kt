package com.epam.drill.logger.api

enum class LogLevel(val code: Int) {
    TRACE(0),
    DEBUG(10),
    INFO(20),
    WARN(30),
    ERROR(40);
}

fun Int.toLogLevel(): LogLevel {
    for (level in LogLevel.values()) {
        if (level.code == this) {
            return level
        }
    }
    return LogLevel.ERROR
}
