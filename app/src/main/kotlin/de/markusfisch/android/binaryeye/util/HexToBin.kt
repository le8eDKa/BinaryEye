package com.markusfisch.android.binaryeye.util

import java.io.OutputStream

object HexToBin {
    fun save(hex: String, out: OutputStream) {
        val clean = hex.replace(Regex("[^0-9A-Fa-f]"), "")
        val len = clean.length
        if (len < 2) return

        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(clean[i], 16) shl 4) +
                    Character.digit(clean[i + 1], 16)).toByte()
            i += 2
        }
        
        out.write(data)
        out.close()
    }
}
