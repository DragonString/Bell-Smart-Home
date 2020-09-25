package net.softbell.bsh.util

import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 방울의 로그 규칙 라이브러리
 */
object BellLog {
    val logHead: String
        get() = "[" + Thread.currentThread().stackTrace[2].methodName + "] "
}