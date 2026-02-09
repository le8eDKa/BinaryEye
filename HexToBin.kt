import java.io.File

fun hexToBin(hex: String, customExtension: String? = null): ByteArray {
    // Convert hex string to byte array
    val byteArray = hex.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
    return byteArray
}

fun detectFormat(byteArray: ByteArray): String {
    val header: ByteArray = byteArray.copyOfRange(0, 4) // Read the first 4 bytes
    return when {
        header.contentEquals(byteArrayOf(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte())) -> ".jpeg" // JPEG magic number
        header.contentEquals(byteArrayOf(0x1A.toByte(), 0x45.toByte(), 0xDF.toByte(), 0xA3.toByte())) -> ".webm" // WEBM magic number
        header.contentEquals(byteArrayOf(0x52.toByte(), 0x49.toByte(), 0x46.toByte(), 0x46.toByte())) -> ".webp" // WEBP magic number
        else -> ".bin" // Default to .bin if unknown format
    }
}

fun main() {
    // Example usage
    val hexString = "Insert your hex string here"
    val byteArray = hexToBin(hexString)
    val format = detectFormat(byteArray)
    val extension = customExtension ?: format
    val outputFileName = "outputFile$extension"
    File(outputFileName).writeBytes(byteArray)
}