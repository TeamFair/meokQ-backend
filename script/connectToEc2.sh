#!/bin/bash
CONFIG_FILE_PATH=$1

source apiProject.sh

# Check command-line argument
if [ $# -ne 1 ]; then
  echo "Usage: $0 <CONFIG_FILE_PATH>"
  echo -e "Ex: $0 ec2-config.txt\n"
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
if check_file_existence "$KEY_FILE_PATH"; then
    echo "key file exist. Connect to EC2 instance..."
else
    echo -e "key file does not exist. Cannot proceed.\n"
    exit 0
fi

# connect ssh
ssh -i "$KEY_FILE_PATH" "$EC2_ADDRESS"."$REGION".compute.amazonaws.com


