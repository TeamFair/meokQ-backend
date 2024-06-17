#!/bin/bash
TARGET_FILE_PATH=$1
TO_PATH=$2
CONFIG_FILE_PATH=$3

source apiProject.sh

# Check command-line argument
if [ $# -ne 3 ]; then
  echo "Usage: $0 <TARGET_FILE_PATH> <TO_PATH> <CONFIG_FILE_PATH>"
  echo -e "Ex: $0 apiProject.jar bin ec2-config.txt\n"
  exit 1
fi

# Load configuration based on argument
if check_file_existence "$CONFIG_FILE_PATH" && check_file_existence "$CONFIG_FILE_PATH"; then
    echo "config file exist..."
    source "$CONFIG_FILE_PATH"
else
    echo -e "config file does not exist. Cannot proceed.\n"
    exit 0
fi

# check file
if check_file_existence "$KEY_FILE_PATH" && check_file_existence "$TARGET_FILE_PATH"; then
    echo "Both key file and JAR file exist. Moving files to EC2 instance..."
else
    echo -e "Either key file or JAR file does not exist. Cannot proceed.\n"
    exit 0
fi

# copy and move file
scp -i "$KEY_FILE_PATH" "$TARGET_FILE_PATH" "$EC2_ADDRESS"."$REGION".compute.amazonaws.com:"$TO_PATH"
echo -e "File moved successfully.\n"
exit 1
