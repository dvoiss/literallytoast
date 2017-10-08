# Literally Toast

🍞 A toast library for Android.

<img src="https://github.com/dvoiss/literallytoast/blob/master/lit_toast.gif"/>

### Usage:

👍 Use the `LitToast` to get lit and show your users a **proper toast**.

```java
LitToast.create(context, "My special toast...", 1000)
  .setPlayToasterSound(true)
  .show();
```

Add the jitpack repo to your root `build.gradle`:

```
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```

Add the dependency in your app `build.gradle`:

```
compile 'com.github.dvoiss:literallytoast:0.0.1'
```

### Features:

* Show a toast, with the message burned into the toast.
* Play a toaster sound
* Feature request? Create an issue or pull request!

### Road-map:

* Variation with Jesus image on toast (on road-map)
* Different types of toast (French toast, Avocado?)
* There is no toast queue to set up multiple toasts. A toaster class (queue) should be written, the queue should only allow for two toasts because most toasters only handle that (unless you've got 💰)

Resources used:

* [Toast image](https://commons.wikimedia.org/wiki/File:Toast-2.jpg) from wikimedia
* [Bread font](https://www.dafont.com/bread.font) from dafont.com
* [Toaster sound](https://freesound.org/people/Adam_N/sounds/164557/) from freesound.org
