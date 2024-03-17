#!/bin/bash

# Functions
check_running() {
  pgrep -f "$COMMAND" > /dev/null
}

find_pid_using_port() {
  netstat -tulpn | grep ":$PORT" | awk '{print $7}' | cut -d'/' -f1
}

start_service() {
  if check_file_existence "$JAR_FILE"; then
    echo "JAR file exist..."
  else
    echo -e "JAR file does not exist. Cannot proceed.\n"
    exit 0
  fi

  if check_running; then
    echo -e "ApiService is already running.\n"
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

    nohup $COMMAND > /dev/null 2>&1 &
    echo -e "ApiService started on port $PORT with profiles.active=$PROFILES_ACTIVE \n"
  fi
}

stop_service() {
  if check_running; then
    pkill -f "$COMMAND"
    echo -e "ApiService stopped.\n"
  else
    echo -e "ApiService is not running.\n"
  fi
}

print_status() {
  if check_running; then
    echo -e "ApiService is running.\n"
  else
    echo -e "ApiService is not running.\n"
  fi
}

check_file_existence() {
    local file_path="$1"
    if [ -f "$file_path" ]; then
        return 0
    else
        return 1
    fi
}