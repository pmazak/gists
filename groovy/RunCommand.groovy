def run(cmd) {
    println cmd
    def runOS = { osCmd ->
        def outStream = new ByteArrayOutputStream(4096)
        def proc = osCmd.execute()
        proc.consumeProcessOutput(outStream, outStream)
        proc.waitFor()
        return outStream.toString()
    }
    try {
        return runOS(cmd)
    }
    catch (onWindowsMachine) {
        return runOS("cmd /X /C $cmd")
    }
}