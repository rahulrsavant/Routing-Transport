#!/bin/bash

# VPS IP address
VPS_IP="94.136.189.179"

# SSH user
USER="rahul"

# Local port for VNC forwarding
LOCAL_PORT=5901
REMOTE_PORT=5901

# Connect via SSH with port forwarding
ssh -L ${LOCAL_PORT}:localhost:${REMOTE_PORT} ${USER}@${VPS_IP}

