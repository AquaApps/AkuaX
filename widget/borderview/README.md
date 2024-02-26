<p >
    <a href="https://github.com/AquaApps/AkuaX/tree/main/widget/borderview/">English</a>
    | <a href="https://github.com/AquaApps/AkuaX/tree/main/widget/borderview/README_CN.md">中文</a>
</p>

<p align="center"><img src="https://github.com/AquaApps/AkuaX/blob/main/assets/borderview.png?raw=true" alt="1600" width="25%"/></p>

<p align="center">
    <strong>Provide border for Scrollable view.</strong>
    <br>
    <br>
    <a href="https://github.com/AquaApps/AkuaX/tree/main/widget/borderview/Doc.md">Documentation</a>
    | <a href="https://github.com/AquaApps/AkuaX/blob/main/assets/borderview_demo.apk">Download Demo App</a>
    <br>
</p>

<br>

Borderview was copied from RikkaX and underwent architectural modifications. I would like to express my gratitude to Rikka and hvv for their contributions.


<p align="center"><strong>Thank you once again to RikkaX and hvv for their contributions!</strong></p>

## Features

- [x] Easy for use.
- [x] Java only.
- [x] Can use your Drawable.
- [x] Support for RecyclerView and NestedScrollView.
- [x] Always backward compatible.

## Installation

Add the remote repository to the root build.gradle file

```kotlin
dependencyResolutionManagement {
    repositories {
        // ...
        maven { url 'https://fan.akua' }
    }
}
```

Then, add the framework dependency to the module's build.gradle file:

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
