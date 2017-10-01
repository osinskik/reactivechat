# reactivechatt - Simple chat app, for training Reactive Streams
It's a reactive application with Mongo and Spring Reactive Web with web client in vanilla JavaScript.
It runs, assuming there is running Mongo docker container.
# Mappings:
- /main.html GET - web client
- /room/[room id] GET - reactive streams (SSE) for target room id.
- /room/[room id] POST - add message for target room id.

