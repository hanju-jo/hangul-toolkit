package hangul.toolkit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HangulToolkitTest {

    @Test fun testDecompose() {
        val toolkit = HangulToolkit()
        assertEquals(Triple('ㅁ', 'ㅏ', 'ㄴ'), toolkit.decompose('만'))
        assertEquals(Triple('ㄷ', 'ㅜ', null), toolkit.decompose('두'))
        assertEquals(Triple('ㄱ', null, null), toolkit.decompose('ㄱ'))
        assertEquals(Triple(null, 'ㅏ', null), toolkit.decompose('ㅏ'))
        assertEquals(Triple(null, null, 'ㄳ'), toolkit.decompose('ㄳ'))
    }

    @Test fun testCompose() {
        val toolkit = HangulToolkit()
        assertEquals('만', toolkit.compose(Triple('ㅁ', 'ㅏ', 'ㄴ')))
        assertEquals('두', toolkit.compose(Triple('ㄷ', 'ㅜ', null)))
    }

    @Test fun testIsHangul() {
        assertTrue('만'.isHangul())
        assertFalse('N'.isHangul())
        assertFalse('&'.isHangul())
        assertTrue("만두".isHangul())
        assertFalse("not만두".isHangul())
    }

}
