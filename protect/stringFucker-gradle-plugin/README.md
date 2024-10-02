<p>
    <a href="https://github.com/AquaApps/AkuaX/tree/main/protect/stringFucker-gradle-plugin/README.md">English</a>
    | <a href="https://github.com/AquaApps/AkuaX/tree/main/protect/stringFucker-gradle-plugin/README_CN.md">中文</a>
</p>
<p align="center"><img src="https://github.com/AquaApps/AkuaX/blob/main/assets/borderview.png?raw=true" alt="1600" width="25%"/></p>
<p align="center">
    <strong>A Gradle-Plugin for string obfuscator.</strong>
    <br>
    <br>
    <a href="https://github.com/AquaApps/AkuaX/tree/main/protect/stringFucker-gradle-plugin/Doc.md">Documentation</a>
    <br>
</p>
<br>


## Features

- [x] Easy for use.
- [x] Can custom algorithm.

## Use

<p align="center">
    <img src="https://img.shields.io/nexus/akuax/fan.akua.akuax.protect/stringFucker-gradle-plugin?server=http%3A%2F%2Fmaven.akua.fan%3A8081%2F" alt="version"/>
    <img src="https://img.shields.io/badge/license-MIT-blue" alt="license"/>
</p>

Add the remote repository to the root settings.gradle file

```kotlin
dependencyResolutionManagement {
    repositories {
        // ...
        maven(url = "http://maven.akua.fan:8081/repository/akuax/").apply {
            isAllowInsecureProtocol = true
        }
    }
}
```

Add the classpath to the root build.gradle file

```kotlin
buildscript {
    dependencies {
        classpath 'fan.akua.akuax.stringFucker-algorithm:xor:+'
        classpath 'fan.akua.akuax.protect:stringFucker-gradle-plugin:+'
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
}
```

Then, add the following codeblock to the module's build.gradle file:

```groovy
import fan.akua.protect.stringfucker.StorageMode
import fan.akua.stringFucker.algo.XOR
apply plugin: 'stringFucker'

stringFucker {
    debug false
    enable true
    implementation new XOR()
    mode StorageMode.Java
}
dependencies {
    implementation 'fan.akua.akuax.stringFucker-algorithm:xor:+'
}
```

## License

```
MIT License

Copyright (c) 2023 Aqua/A-kua

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
