import java.io.File
import java.util.UUID

object EphemeralMongo {
    private var process: Process? = null
    private var uuid = UUID.randomUUID()

    fun startProcess() {
        if (process != null) throw RuntimeException("Process already running")

        File("/tmp/$uuid").mkdir()

        val pb = ProcessBuilder()
        pb.command("/usr/bin/mongod", "--dbpath", "/tmp/$uuid", "--storageEngine", "ephemeralForTest", "--port", "37017")
        try {
            process = pb.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopProcess() {
        process?.destroyForcibly()
        process = null
        File("/tmp/$uuid").deleteRecursively()
    }
}