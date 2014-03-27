genymotion-binocle
==================

*An Android app showing Genymotion API usage.*

This is a simple app with android tests demonstrating how to use Genymotion API to check behavior of your app on sensors changes.

[Genymotion Java Api documentation](https://cloud.genymotion.com/page/api/)
[Genymotion Java Api Javadoc](https://cloud.genymotion.com/static/external/javadoc/index.html)

### Battery
An app must display a warning if the device is not plugged to a power source and has got less than 10% power left.  
Here is the Fragment showing it: [BatterySampleFragment.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/main/java/com/genymotion/binocle/BatterySampleFragment.java)  
Here is an androidTest testing for the behavior: [TestBattery.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/instrumentTest/java/com/genymotion/binocle/test/TestBattery.java)

### GPS
An app must display a message if the device is localized near a specific place.  
Here is the Fragment showing it: [GpsSampleFragment.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/main/java/com/genymotion/binocle/GpsSampleFragment.java)  
Here is an androidTest testing for the behavior:  [TestGps.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/instrumentTest/java/com/genymotion/binocle/test/TestGps.java)

### Radio
An app must display a message if the device is a nexus 4 device, as recognized by its IMEI number.  
Here is the Fragment showing it: [RadioSampleFragment.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/main/java/com/genymotion/binocle/RadioSampleFragment.java)  
Here is an androidTest testing for the behavior:  [TestRadio.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/instrumentTest/java/com/genymotion/binocle/test/TestRadio.java)

### Id
An app must encrypt data using ANDROID_ID to avoid the backup data to be moved on another Android device.  
Here is the Fragment showing it: [IdSampleFragment.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/main/java/com/genymotion/binocle/IdSampleFragment.java)  
Here is an androidTest testing for the behavior:  [TestId.java](https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/instrumentTest/java/com/genymotion/binocle/test/TestId.java)


# Licence
```
Copyright 2014 Genymotion

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
