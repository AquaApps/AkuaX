package fan.akua.protect.stringfucker.annotation;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation could keep no obf for string.
 *
 * @author Akua
 * @since 2024/10/01 18:16
 */
@Retention(RetentionPolicy.CLASS)
@Target(value = {TYPE})
public @interface IgnoreStringFucker {

}
