# android-mvp-firebase-boilerplate
Boilerplate Android app with Model-View-Controller (MVP) architecture that uses Firebase as the backend.

The app provides a simple UI to login a user and list/create simple data items in Firebase (using Retrofit only).
It provides the necessary base Activities/Fragments that support custom presenters. 
In this architecture, Activities/Fragements are "dumb" views. The presenters contain the business logic.
Activity/Fragment lifecycle events are forwarded to presenters for handling.
The app uses Realm as a local cache where network results are stored. UI is updated using changes to Realm realm.

To run the appm make sure you put your firebase's google-services.json inside the app folder. Also update BASE_SERVICE_URL parameter inside build.gradle to make the app work.

Used libraries:
Firebase Auth (for signing in users)
Retrofit (for using Firebase as the backend)
Realm
Dagger2
RxJava/RxAndroid (mostly for network management)
Butterknife
Retrolambda
