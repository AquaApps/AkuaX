package fan.akua.protect.stringfucker.core.asm;

import fan.akua.protect.stringfucker.IStringFucker;
import fan.akua.protect.stringfucker.core.mode.IModeWriter;

public record VisitorParams(
        boolean deleteIgnoreAnnotation,
        IModeWriter modeWriter,
        IStringFucker impl
) {
}