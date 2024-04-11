#include <jni.h>
#include <string>

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }
    auto zygote_class = env->FindClass("fan/akua/fucker/Zygote");
    auto fucked_static_field = env->GetStaticFieldID(zygote_class, "fucked", "I");
    env->SetStaticIntField(zygote_class,fucked_static_field,jint(114514));

    return JNI_VERSION_1_6;
}