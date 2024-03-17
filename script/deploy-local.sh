#!/bin/bash

# Variables
readonly PROFILES_ACTIVE="local"
readonly PORT="9090"
readonly JAR_FILE="apiProject.jar"
readonly COMMAND="java -jar -Dspring.profiles.active=$PROFILES_ACTIVE $JAR_FILE"
source apiProject.sh

# Service Management
case "$1" in
  start)
    start_service
    ;;
  stop)
    stop_service
    ;;
  status)
    print_status
    ;;
  *)
    echo "Usage: $0 {start|stop|status}"
    exit 2
    ;;
esac

exit 0


