# android-mvp-firebase-boilerplate
Boilerplate Android app with Model-View-Presenter (MVP) architecture that uses Firebase as the backend.

The app provides a simple UI to login a user and list/create simple data items in Firebase using only Retrofit (not Firebase's Android library).
It provides the necessary base Activities/Fragments that support custom presenters. 

In this architecture, Activities/Fragments are responsible for managing UI and forwarding necesssary lifecycle changes to the presenters. Presenters are the interaction points between Activities/Fragments (view) and data (model). Presenters communicate directly with the database/local cache (Realm) and the api (Firebase). Activities/Fragments are able to update their UI by the data handed over to them by the presenters.


To run the app, first create a firebase android app, second create a user and enable sign-in method as email/password and finally create a test user from firebase console. Make sure you put your firebase's google-services.json inside the app folder. Also, update BASE_SERVICE_URL parameter with your firebase url inside build.gradle to make the app work.

Note that the project is currently implemented using Android Studio 3.0 Canary 6 and Android O Preview.

The app uses a simple user-item data structure. You can use the below Firebase database rule to only allow users reading and writing their own item objects:

```
{
  "rules": {
    "users": {
      "$uid": {
        ".read": "auth.uid == $uid",
        ".write": "auth.uid == $uid",
      
        "items" : {
          "$itemId" : {
            ".read": "auth.uid == $uid",
            ".write": "auth.uid == $uid"
          }
        }
      }
    }
  }
}
```

Used libraries:
- Firebase Auth (for signing in users)
- Retrofit (for using Firebase as the backend)
- Realm (local cache)
- Dagger2
- RxJava2/RxAndroid (mostly for network management)
- Butterknife
