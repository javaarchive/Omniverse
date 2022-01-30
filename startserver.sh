#!/bin/bash
tmux new -d -s testserver
tmux send-keys -t testserver.0 "cd /media/raymond/2656B9085E2F2A27/Coding/Omniverse-Test && /usr/lib/jvm/java-1.17.0-openjdk-amd64/bin/java -Xmx8G -jar purpur-1.17.1.jar" ENTER