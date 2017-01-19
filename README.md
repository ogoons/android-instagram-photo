# Android Instagram Photo

Instagram Photo API Android Wrapper

## Summary

안드로이드 인스타그램 사진 가져오기 기능을 구현한 Container class 입니다.

인스타그램 API는 순수 REST API로만 제공되는데 이를 래핑한 클래스 꾸러미입니다.
결국 시간을 벌어주는 소스코드가 되겠습니다. :)

현재 recent photo API와 관련해서 권한 이슈로 사진을 20개 이상 가져오지 못할 텐데 
해당 기능이 구현되는 앱을 심사받고 권한을 획득하시면 됩니다.

## Dependencies

build.gradle (app)
```
compile 'com.squareup.retrofit2:retrofit:2.1.0'
compile 'com.squareup.retrofit2:converter-gson:2.1.0'
```

## License
```
Copyright 2016 Oh, Su-cheol <ogoons@hanmail.net>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
