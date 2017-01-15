# LunchTime

LunchTime application was created with the purpose to help people to decide where they will have lunch during the week amongst teammate. It shows up a list of restaurants close to where the user are and how many votes each one had on the current day. Close to the lunch time, an alert pops up on the screen with the chosen restaurant.
- --
LunchTime application is being developed under the Android platform using Android Studio 2.2.3 and it has some dependencies as listed below:

> All the dependencies are already listed on the `build.gradle` file of the application.

-- This library adds compatibility with the standard design of the Action Bar of the user interface. This library contains UI implementations of material design.
```sh
compile 'com.android.support:appcompat-v7:24.2.1'
```
-- MultiDex patches the application context class loader in order to load classes from more than one dex file and to configure applications with more than 64K methods.
```sh
compile 'com.android.support:multidex:1.0.1'
```
-- The Design Support Library adds support for various components and standards of design material, such as navigation drawers, floating action buttons ( FAB ), bars and guides.
```sh
compile 'com.android.support:design:24.2.1'
```
-- The Google Play Service library allows the app to interact with Google's APIs, as for example the Google Map and Google Places API.
```sh
compile 'com.google.android.gms:play-services:10.0.1'
```
-- An asynchronous callback-based Http client for Android built on top of Apache’s HttpClient libraries.
```sh
compile 'com.loopj.android:android-async-http:1.4.9'
```
--Field and method binding for Android views which uses annotation processing to generate boilerplate code.
```sh
compile 'com.jakewharton:butterknife:6.1.0'
```
-- JUnit is the most popular and widely-used unit testing framework for Java.
```sh### LunchTime Configuration
testCompile 'junit:junit:4.12'
```
-- The Mockito mocking framework for Java offers compatibility with Android unit testing. With Mockito, you can configure mock objects to return some specific value when invoked.
```sh
compile 'org.mockito:mockito-android:2.6.2'
```
-- Robolectric is a unit test framework that de-fangs the Android SDK jar so you can test-drive the development of your Android app.
```sh
testCompile 'org.robolectric:robolectric:3.2.2'
```
#### LunchTime Configuration
```sh
applicationId "com.codechallenge.dbserver.lunchtime"
minSdkVersion 16
targetSdkVersion 24
```
#### Classes Description
- Adapters
    - RestaurantListAdapter – This class is used to organize and display the list of restaurants in a ListView android component.
- Controller
    - RestaurantController – This class implements all the necessary functionality to request the Web Service, in order to store and receive data related to the restaurant list.
    - UserController – This class implements all the necessary functionality to request the Web Service, in order to store and receive data related to the User.
    - VotingController – This class implements all the necessary functionality to request the Web Service, in order to store and receive data related to the voting on the application.
- Models
    - Restaurant – This class represents a Restaurant object inside the application.
    - User – This class represents a User object inside the application.
    - Vote – This class represents a Vote object made by the user inside the application.
- Service
    - LunchTimeService – This class represents a service to keep running in the background a process to alert the user and send notifications while the app is running or not.
    - NotificationServiceStarterReceiver – This class represents a BroadCastReceiver which is used by the LunchTimeService.
- Utils
    - GoogleApiUtility – This class is used to check permissions of the Google APIs and Google Play Services.
    - GooglePlacesDataParser – This class is used to process the JSON string resulted by the Google Places API.
    - GooglePlacesDownloadUrl – This class is used to download the data of all places requested by the application.
    - MainApplicationConstants – This class stores all the constants of the application to facilitates its localization.
- Views
    - LaunchActivity - This activity was created with the purpose to check and adjust some pre-configuration parameters before go to the MainActivity.
    - LoginActivity – This activity shows the login screen and the functionalities to perform the login via email and password.
    - MainActivity – This is the main activity of the application with the purpose of display the menu bar and the fragment view of the list of restaurant and the map.
    - RestaurantListViewer – This fragment is used to show a list of restaurants to the user.
    - RestaurantMapViewer – This fragment is used to show all the closest restaurant on the map.
    - SignUpActivity – This activity is used to let the user sign up to the application.
    - VoteDialogActivity – This activity is used as a dialog object, in order to display the data of the restaurant.

WebService LunchTimeWS: https://github.com/thulioaraujo/LunchTimeWS.git