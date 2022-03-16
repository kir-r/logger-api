/**
 * Copyright 2020 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.epam.drill.logger.api

import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

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

@OptIn(ExperimentalTime::class)
inline fun <T> Logger.trackTime(
    tag: String = "",
    debug: Boolean = false,
    block: () -> T
) = measureTimedValue { block() }.apply {
    val message = "[$tag] took: $duration"
    when {
        duration.inSeconds > 1 -> {
            warn { message }
        }
        duration.inSeconds > 30 -> {
            error { message }
        }
        else -> if (debug) debug { message } else trace { message }
    }
}.value
