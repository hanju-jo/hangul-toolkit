package hangul.toolkit

import hangul.toolkit.Constants.CHO
import hangul.toolkit.Constants.FIRST_HANGUL_UNICODE
import hangul.toolkit.Constants.JONG
import hangul.toolkit.Constants.JUNG
import hangul.toolkit.Constants.NUM_JONG
import hangul.toolkit.Constants.NUM_JUNG

class HangulToolkit {

    fun compose(choJungJong: Triple<Char, Char, Char?>): Char {
        val jong = choJungJong.third ?: Char.MIN_VALUE

        val choIndex = CHO.indexOf(choJungJong.first)
        val jungIndex = JUNG.indexOf(choJungJong.second)
        val jongIndex = JONG.indexOf(jong)
        if (choIndex < 0 || jungIndex < 0 || jongIndex < 0) {
            throw NotHangulException()
        }

        val hangulIndex = choIndex * NUM_JUNG * NUM_JONG + jungIndex * NUM_JONG + jongIndex
        val code = FIRST_HANGUL_UNICODE + hangulIndex
        return code.toChar()
    }

    fun decompose(syllable: Char): Triple<Char?, Char?, Char?> {
        if (!syllable.isHangul()) {
            throw NotHangulException()
        }
        if (syllable in CHO) {
            return Triple(syllable, null, null)
        }
        if (syllable in JUNG) {
            return Triple(null, syllable, null)
        }
        if (syllable in JONG) {
            return Triple(null, null, syllable)
        }
        val hangulIndex = syllable.toHangulIndex()
        val choJungJongIndex = hangulIndex.decomposeIntoIndex()

        val choIndex = if (choJungJongIndex.first < 0) 0 else choJungJongIndex.first
        val jungIndex = choJungJongIndex.second
        val jongIndex = choJungJongIndex.third
        val jong = if (jongIndex == 0) null else JONG[jongIndex]

        try {
            return Triple(CHO[choIndex], JUNG[jungIndex], jong)
        } catch (e: IndexOutOfBoundsException) {
            print("%d / %d / %d".format(choIndex, jungIndex, jongIndex))
            print("%s / %s".format(JUNG[jungIndex], JONG[jongIndex]))
            throw e
        }
    }

    private fun Char.toHangulIndex(): Int {
        return this.toInt() - FIRST_HANGUL_UNICODE
    }

    private fun Int.decomposeIntoIndex(): Triple<Int, Int, Int> {
        var code = this
        val jong = code % NUM_JONG
        code /= NUM_JONG
        val jung = code % NUM_JUNG
        code /= NUM_JUNG
        val cho = code
        return Triple(cho, jung, jong)
    }

}
