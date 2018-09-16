object EphemeralMongo {
    private var process: Process? = null

    fun startProcess() {
        if (process != null) throw RuntimeException("Process already running")

        val pb = ProcessBuilder()
        pb.command("/usr/bin/mongod", "--dbpath", "/tmp/dbdata", "--storageEngine", "ephemeralForTest", "--port", "37017")
        try {
            process = pb.start()
            println(process!!.isAlive)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopProcess() {
        process?.destroyForcibly()
    }
}