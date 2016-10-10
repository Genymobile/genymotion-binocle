genymotion-binocle
==================

*An Android app showing Genymotion Java API usage.*  
![Binocle](../master/binocle/src/main/res/drawable-xxxhdpi/ic_launcher.png?raw=true)


Binocle is a simple application showcasing Genymotion Java API use.
In this application, you can find activities for which the behavior depends on sensor values.
Below are some Android test examples built with Genymotion Java API to manipulate sensor values and check activity behaviors.

[Genymotion Java Api documentation](https://cloud.genymotion.com/page/api/)    
[Genymotion Java Api Javadoc](https://cloud.genymotion.com/static/external/javadoc/index.html)

### Battery
This part of the application displays a warning if the device is not plugged to a power source and has less than 10% of charge left.  
* Here is the fragment showing it: [BatterySampleFragment.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/main/java/com/genymotion/binocle/BatterySampleFragment.java)  
* Here is an Android test of the behavior: [TestBattery.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/androidTest/java/com/genymotion/binocle/test/TestBattery.java)

### GPS
This part of the application displays a message if the device is localized near a specific place.  
* Here is the fragment showing it: [GpsSampleFragment.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/main/java/com/genymotion/binocle/GpsSampleFragment.java)  
* Here is an Android test of the behavior:  [TestGps.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/androidTest/java/com/genymotion/binocle/test/TestGps.java)

### Radio
This part of the application displays a message if the device is a Nexus 4, as recognized by its IMEI number.  
* Here is the fragment showing it: [RadioSampleFragment.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/main/java/com/genymotion/binocle/RadioSampleFragment.java)  
* Here is an Android test of the behavior:  [TestRadio.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/androidTest/java/com/genymotion/binocle/test/TestRadio.java)

### Id
This part of the application encrypts data using ANDROID_ID to avoid the backed up data to be moved on another Android device.  
* Here is the fragment showing it: [IdSampleFragment.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/main/java/com/genymotion/binocle/IdSampleFragment.java)  
* Here is an Android test of the behavior:  [TestId.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/androidTest/java/com/genymotion/binocle/test/TestId.java)

### Phone
This part of the application displays a green check when the device receives an SMS containing "666".
* Here is the fragment showing it: [PhoneSampleFragment.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/main/java/com/genymotion/binocle/PhoneSampleFragment.java)
* Here is an Android test of the behavior: [TestPhone.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/androidTest/java/com/genymotion/binocle/test/TestPhone.java)

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
