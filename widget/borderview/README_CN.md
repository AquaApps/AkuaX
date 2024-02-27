<p >
    <a href="https://github.com/AquaApps/AkuaX/tree/main/widget/borderview/">English</a>
    | <a href="https://github.com/AquaApps/AkuaX/tree/main/widget/borderview/README_CN.md">中文</a>
</p>

<p align="center"><img src="https://github.com/AquaApps/AkuaX/blob/main/assets/borderview.png?raw=true" alt="1600" width="25%"/></p>

<p align="center">
    <strong>为可滚动的控件提供上下边框。</strong>
    <br>
    <br>
    <a href="https://github.com/AquaApps/AkuaX/tree/main/widget/borderview/Doc.md">文档</a>
    | <a href="https://github.com/AquaApps/AkuaX/blob/main/assets/borderview_demo.apk">下载体验</a>
    <br>
</p>

<br>

Borderview 是从RikkaX中拷贝，修改架构后的产物。我在此表达对Rikka和hvv的感谢。

<p align="center"><strong>再次感谢Rikka和hvv</strong></p>

## 特性

- [x] 极易使用。
- [x] 纯Java。
- [x] 支持自定义Drawable。
- [x] 支持RecyclerView以及NestedScrollView。
- [x] 永远向下兼容

## 安装

Project 的 settings.gradle 添加仓库

```kotlin
dependencyResolutionManagement {
    repositories {
        // ...
        maven { url 'https://fan.akua' }
    }
}
```

Module 的 build.gradle 添加依赖框架

```groovy
dependencies {
    implementation 'fan.akua.akuax.widget:borderview:+'
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
