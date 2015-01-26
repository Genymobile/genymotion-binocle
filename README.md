genymotion-binocle
==================

*An Android app showing Genymotion Java API usage.*  
![Binocle](../master/binocle/src/main/res/drawable-xxxhdpi/ic_launcher.png?raw=true)


This is a simple Android app with Android tests demonstrating how to use Genymotion Java API to check the behavior of your app when sensors values change.

[Genymotion Java Api documentation](https://cloud.genymotion.com/page/api/)    
[Genymotion Java Api Javadoc](https://cloud.genymotion.com/static/external/javadoc/index.html)

### Battery
This activity displays a warning if the device is not plugged to a power source and has got less than 10% power left.  
Here is the Fragment showing it: [BatterySampleFragment.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/main/java/com/genymotion/binocle/BatterySampleFragment.java)  
Here is an androidTest testing the behavior: [TestBattery.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/instrumentTest/java/com/genymotion/binocle/test/TestBattery.java)

### GPS
This activity displays a message if the device is localized near a specific place.  
Here is the Fragment showing it: [GpsSampleFragment.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/main/java/com/genymotion/binocle/GpsSampleFragment.java)  
Here is an androidTest testing the behavior:  [TestGps.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/instrumentTest/java/com/genymotion/binocle/test/TestGps.java)

### Radio
This activity displays a message if the device is a nexus 4 device, as recognized by its IMEI number.  
Here is the Fragment showing it: [RadioSampleFragment.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/main/java/com/genymotion/binocle/RadioSampleFragment.java)  
Here is an androidTest testing the behavior:  [TestRadio.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/instrumentTest/java/com/genymotion/binocle/test/TestRadio.java)

### Id
This activity encrypts data using ANDROID_ID to avoid the backed up data to be moved to another Android device.  
Here is the Fragment showing it: [IdSampleFragment.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/main/java/com/genymotion/binocle/IdSampleFragment.java)  
Here is an androidTest testing the behavior:  [TestId.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/instrumentTest/java/com/genymotion/binocle/test/TestId.java)


# License
```
Copyright 2015 Genymotion

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
