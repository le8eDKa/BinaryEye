package de.markusfisch.android.binaryeye.util

import java.io.OutputStream

object HexToBinByle8eDKa {
    // Список безопасных сигнатур (Magic Numbers)
    private val signatures = mapOf(
        "ffd8ff" to "jpg",
        "89504e47" to "png",
        "47494638" to "gif",
        "25504446" to "pdf",
        "504b0304" to "zip",
        "494433" to "mp3",
        "52494646" to "wav"
    )

    //Проверка формата вывода
    fun isStrictHex(hex: String): Boolean {
        val clean = hex.replace(Regex("\\s"), "")
        return clean.length >= 4 && 
               clean.length % 2 == 0 && 
               clean.all { it in '0'..'9' || it in 'a'..'f' || it in 'A'..'F' } &&
               getExtension(clean) != null
    }

    fun getExtension(hex: String): String? {
        val clean = hex.replace(Regex("\\s"), "").lowercase()
        for ((signature, ext) in signatures) {
            if (clean.startsWith(signature)) return ext
        }
        return null
    }
    
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
//спасибо что смотришь код)
