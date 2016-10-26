# Android Instagram Photo
### Android instagram photo api wrapper

안드로이드 인스타그램 사진 가져오기 기능을 구현한 Container class 입니다.
인스타그램 API는 순수 REST API로만 구현되어 있어 안드로이드에서 구현할 때는 까다로운데요.
이 wrapper는 시간을 벌어주는 소스코드가 되겠지요 :)
현재 recent photo API와 관련해서 권한 이슈로 사진을 20개 이상 가져오지 못할 텐데
해당 기능이 구현되는 앱을 심사받고 권한을 획득하시면 됩니다.

Square 사의 Retrofit2로 구성된 소스 코드입니다.
아래 코드를 build.gradle 에 추가해주세요.

build.gradle (app)

    compile 'com.squareup.retrofit2:retrofit:2.1.0'
    compile 'com.squareup.retrofit2:converter-gson:2.1.0'

# License

    The MIT License (MIT)

    Copyright (c) 2016 Oh, Su-cheol <ogoons@hanmail.net>

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
