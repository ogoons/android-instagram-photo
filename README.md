# AndroidInstagramPhoto
Android instagram photo api wrapper
<br><br>
안드로이드 인스타그램 사진 가져오기 기능을 구현한 Container class 입니다.<br>
인스타그램 API는 순수 REST API로만 구현되어 있어 안드로이드에서 구현할 때는 까다로운데요.<br>
이 wrapper는 시간을 벌어주는 소스코드가 되겠지요 :)<br>
현재 recent photo API와 관련해서 권한 이슈로 사진을 20개 이상 가져오지 못할 텐데<br>
해당 기능이 구현되는 앱을 심사받고 권한을 획득하시면 됩니다.<br>
<br>
Square 사의 Retrofit2로 구성된 소스 코드입니다.<br>
아래 코드를 build.gradle 에 추가해주세요.<br>
<br>
<b>build.gradle (app)</b><br>
compile 'com.squareup.retrofit2:retrofit:2.1.0' <br>
compile 'com.squareup.retrofit2:converter-gson:2.1.0'<br>
<br>
******************************************************<br>
AndroidInstagramPhoto wrapper<br>
Copyright 2016 ogoons.<br>
이 소스코드는 BSD 라이센스를 따릅니다.<br>
******************************************************<br>
