#!/bin/bash
tmux send-keys -t testserver.0 "stop" ENTER
sleep 10
tmux send-keys -t testserver.0 "exit" ENTER