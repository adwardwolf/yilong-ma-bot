# yilong-ma-bot
<img src="https://github.com/adwardwolf/yilong-ma-bot/blob/main/android/app/src/main/ic_launcher-playstore.png" width="16%">

**Android sample chat project with Chatterbot where you can add any conversations as you wish and teach your bot right away.**

**Note:** This project is still on its early stage, there's alot of things to improve.

## Screenshots
<p>
  <img src="https://github.com/adwardwolf/yilong-ma-bot/blob/main/screenshots/chat_screen.jpg" width="20%">
  <img src="https://github.com/adwardwolf/yilong-ma-bot/blob/main/screenshots/category_screen.jpg" width="20%">
  <img src="https://github.com/adwardwolf/yilong-ma-bot/blob/main/screenshots/conversation_screen.jpg" width="20%">
</p>

## Run app :rocket:
To run the app you need to run **Server** and **Websocket** on your local machine.

Make sure to change `BASE_URL` at [Contants.kt](android/app/src/main/java/com/wo1f/yilongma/utils/Constants.kt)

1. Run Websocket
2. Run Server
3. Run Chatterbot(you might need to install [Chatterbot](https://chatterbot.readthedocs.io/en/stable/setup.html) and [Mongodb](https://www.mongodb.com/docs/manual/administration/install-community/))
4. Run Android app

**Note:** Your bot has not yet had any ideas about the conversations you've just added. To make it learn, send `/restart` command to your bot via chat.

### Tech Stack :robot:
**Android:** Jetpack compose, ViewModel, Hilt, Flow, Retrofit, Coroutine, and testing(Mockk). 

**Websocket(Node Js):** Express, Mongoose, and SocketIO.

**Server(Ktor):** Koin, Kmongo, and testing(Mockk). 

### Contribution :handshake:

Contributions to this project are always welcome! :wink:

## License
```
Copyright 2014 Wo1f. All rights reserved.

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
