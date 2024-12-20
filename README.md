This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

**Android**

![android-home](https://github.com/user-attachments/assets/b3dab58a-6c67-4c25-ac0d-ea8dac725ff9)
![android-favourite](https://github.com/user-attachments/assets/867bcd7d-c2a8-437e-a99a-4526a64dba72)
![android-detail](https://github.com/user-attachments/assets/847141a9-3504-48d5-b964-d2719be97119)

https://github.com/user-attachments/assets/efe35674-f1a2-4588-a921-4b319bf59f51



**Window**

![window-home](https://github.com/user-attachments/assets/d4a5769b-ddbf-471f-aa06-f1b304b16b13)
![window-favourite](https://github.com/user-attachments/assets/a847023b-4a5c-4398-81d3-0d2715f62960)
![window-detail](https://github.com/user-attachments/assets/160e8eeb-6e3e-478c-8d38-925442986338)

https://github.com/user-attachments/assets/e56d85e7-dc3d-4421-8fed-5b8f35f03c40

