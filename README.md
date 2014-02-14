genymotion-binocle
==================

*An Android app showing Genymotion API usage.*

Sometimes in your instrumented tests, you will want to test what happens in your app when sensors send back specific values.
As Android cannot fake sensors values, you will have to modify your code to mock them and create proxy objects for them.
This will add unwanted noise inside your project source code, making it less readable, and harder to maintain.

Genymotion API is there to help you on this!
As all our sensors are already mocked inside Genymotion, we created a Java API allowing you to manipulate sensors values directly from your instrumentedTests.

Here is some practical exemples:

An app must display a warning if the device is not plugged to a power source and has got less than 10% power left.
Here is the Fragment showing it: https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/main/java/com/genymotion/binocle/BatterySampleFragment.java
Here is an instrumentedTest testing for the behavior: https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/instrumentTest/java/com/genymotion/binocle/test/TestBattery.java

An app must display a message if the device is localized near a specific place.
Here is the Fragment showing it: https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/main/java/com/genymotion/binocle/GpsSampleFragment.java
Here is an instrumentedTest testing for the behavior:  https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/instrumentTest/java/com/genymotion/binocle/test/TestGps.java

An app must display a message if the device is a nexus 4 device, as recognized by its IMEI number.
Here is the Fragment showing it: https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/main/java/com/genymotion/binocle/RadioSampleFragment.java
Here is an instrumentedTest testing for the behavior:  https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/instrumentTest/java/com/genymotion/binocle/test/TestRadio.java

An app must encrypt data using ANDROID_ID to avoid the backup data to be moved on another Android device.
Here is the Fragment showing it: https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/main/java/com/genymotion/binocle/IdSampleFragment.java
Here is an instrumentedTest testing for the behavior:  https://github.com/Genymobile/genymotion-binocle/blob/master/binocle/src/instrumentTest/java/com/genymotion/binocle/test/TestId.java


How to use the Genymotion Java Api in your instrumentedTest project:
If using Maven:
```
<dependency>
    <groupId>com.genymotion.api</groupId>
    <artifactId>genymotion-api</artifactId>
    <version>1.0.0</version>
</dependency>
```
If using Gradle:
```
instrumentTestCompile 'com.genymotion.api:genymotion-api:1.0.0'
```
Without Maven nor Gradle:
Simply add the jar file to your "libs" folder.
http://www.genymotion.com/download/com.genymotion.api/genymotion-api/1.0.0/genymotion-api-1.0.0.jar

Then inside your instrumented test, get a reference to Genymotion object using:
```
Genymotion.getGenymotionManager(getInstrumentation().getContext())
```
You will then be able to access all sensors from the GenymotionManager as described in our Javadoc: https://cloud.genymotion.com/static/external/javadoc/index.html
Most of the time your app listen to sensor changes using listener, and it takes some times for the system to see that sensors values changed, broadcast the values to every app listening for them. This is how you can wait a period of time for all of this to happen:
```
try {
    Thread.sleep(5000); //Android need time to poll sensors and broadcast event.
} catch (InterruptedException ie) {
}
getInstrumentation().waitForIdleSync();
```
If you want to ensure that your instrumented test is only executed inside Genymotion and not on a real device you can exit the test this way:
```
if (!Genymotion.isGenymotionDevice()) {
    return; //don’t execute this test
}
```

