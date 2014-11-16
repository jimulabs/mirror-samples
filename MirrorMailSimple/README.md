This sample project includes layouts of a fictional email app. It's suitable for quick demos of Mirror features at a workshop.

## Getting started

0. Install Android Studio 0.9.3 (or the latest version) from [Canary Channel](http://tools.android.com/download/studio/canary)
1. Follow [this guide](http://jimulabs.com/mirror-docs/mirror-android-studio-plugin-installation-guide/) to install jimu Mirror from JetBrains plugin repository
2. Enable ADB USB debugging on your device and connect it to your computer (See the section "Setting up your device for debugging" in [this guide](http://jimulabs.com/mirror-docs/mirror-js-designer-guide/))
3. Alternatively, you can use an emulator such as Genymotion. Note for the best result, use Android 4.3 images since Genymotion (as of v2.3.0) has some issues in its 4.4+ images that make Mirror's updates slower than real devices.
4. Import this project (MirrorMailSimple) in Android Studio (Note: when importing, you need to pick the directory `MirrorMailSimple`, not its parent directory `mirror-samples`)
5. Start Mirror by clicking the button on the toolbar within Android Studio
6. Choose `activity_list` on the device
7. When making a change to a layout, remember to press `ctrl-S` or `cmd-S` to save the file. Afterwards, you should be able to see the update on the device. 

## Features demonstrated in this sample project

- How to populate `ListView` with sample data. Related files include:
  - layout file: `MirrorMail/src/res/layout/activity_list.xml`
  - mirror file (sample data): `MirrorMail/mirror/activity_list.xml`
- Responsive layouts, i.e. two panes will show up on a landscape mode tablet (e.g. Nexus 7), whereas normally only one pane will be displayed on phones. Related files include:
  - layout file for tablets: `MirrorMail/src/res/layout-sw600dp-land/activity_list.xml`
  - mirror file: `MirrorMail/mirror/activity_list.xml`
- How to populate the action bar. Related files include:
  - mirror file: `MirrorMail/mirror/activity_list.xml`. There is a line `<actionbar title="INBOX" icon="inbox" menu="list_menu"/>` in the file.
  - menu file: `MirrorMail/src/main/res/menu/list_menu.xml`

## Things to try
Once you get Mirror up and running, you can try the following:

- **Make the avatar bigger:**
  1. open `MirrorMail/src/main/res/layout/mail_list_item.xml`
  2. change the `layout_height` and `layout_width` of `CircularImageView` to `168dp` or other values
- **Change the window color of the theme:**
  1. open `res/values/styles.xml`
  2. find this line in the first style `AppTheme.Light`:
  ```
  <item name="android:windowBackground">@android:color/white</item>`
  ```
  3. change it to `@android:color/black`
- **Responsive layouts:**
  1. delete `MirrorMail/src/main/res/layout-sw600dp-land/activity_list.xml` (save a copy somewhere)
  2. launch a Nexus 7 emulator, start Mirror
  3. choose `activity_list` on the emulator, you should see a one-pane layout with lots of whitespace on the right
  4. copy the deleted `activity_list.xml` file back to `res/layout-sw600dp-land`
  5. you should be able to see a two-pane layout: a list of emails along with the detail of a single email
 
This project is a simplified version of [`MirrorMailProject`](https://github.com/jimulabs/mirror-samples/tree/master/MirrorMailProject) which includes more comprehensive examples of jimu Mirror, such as support for `ViewPager` and a transparent overlay which makes it easy to match a layout with an image.

