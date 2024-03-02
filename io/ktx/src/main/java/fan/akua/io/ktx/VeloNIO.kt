package fan.akua.io.ktx

import fan.akua.io.velonio.VeloNIO
import java.io.InputStream
import java.io.OutputStream


infix fun InputStream.to(that: OutputStream): InputStream {
    VeloNIO.copyInputToOutput(this, that)
    return this
}
