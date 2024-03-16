#!/bin/bash

# Variables
readonly JAR_FILE="/dev/ApiService.jar"
readonly PORT=9091
readonly PROFILES_ACTIVE="dev"
readonly COMMAND="java -jar $JAR_FILE -Dspring.profiles.active=$PROFILES_ACTIVE --port $PORT"

# Functions
check_running() {
  pgrep -f "$COMMAND" > /dev/null
}

find_pid_using_port() {
  netstat -tulpn | grep ":$PORT" | awk '{print $7}' | cut -d'/' -f1
}

start_service() {
  if check_running; then
    echo "ApiService is already running."
  else
    if netstat -tulpn | grep ":$PORT" > /dev/null; then
      echo "Another service is running on port $PORT"
      echo "Service details:"
      netstat -tulpn | grep ":$PORT"
      read -p "Do you want to kill it and start ApiService? (y/n): " answer
      if [ "$answer" == "y" ]; then
        kill $(find_pid_using_port)
      else
        echo "Start aborted."
        exit 1
      fi
    fi
    nohup "$COMMAND" > /dev/null 2>&1 &
    echo "ApiService started on port $PORT with profiles.active=$PROFILES_ACTIVE"
  fi
}

stop_service() {
  if check_running; then
    pkill -f "$COMMAND"
    echo "ApiService stopped."
  else
    echo "ApiService is not running."
  fi
}

print_status() {
  if check_running; then
    echo "ApiService is running."
  else
    echo "ApiService is not running."
  fi
}

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
    exit 1
    ;;
esac

exit 0
