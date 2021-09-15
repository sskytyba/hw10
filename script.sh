#!/bin/bash

while :; do
  v=$(date +%s%N)
  curl -s -X POST -H "Content-Type: application/json" "http://localhost:8080/set" -d '{"key": "key", "value": "'"$v"'"}'
  sleep 0.2
  curl -s -X GET "http://localhost:8080/get?key=key"
  echo ""
  sleep 0.2
done