package hangul.toolkit

import hangul.toolkit.Constants.FIRST_HANGUL_UNICODE
import hangul.toolkit.Constants.JAMO
import hangul.toolkit.Constants.LAST_HANGUL_UNICODE

private fun Char.isJamo(): Boolean {
    return this in JAMO
}

fun Char.isHangul(): Boolean {
    if (this.isJamo()) {
        return true
    }
    val code = this.toInt()
    return code in FIRST_HANGUL_UNICODE..LAST_HANGUL_UNICODE
}

fun String.isHangul(): Boolean {
    return this.toCharArray()
            .all { it.isHangul() }
}
