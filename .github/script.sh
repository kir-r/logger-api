#!/bin/bash

if [[ ! -v MAVEN_USERNAME ]]; then
    echo "MAVEN_USERNAME is not set"
elif [[ -z "$MAVEN_USERNAME" ]]; then
    echo "MAVEN_USERNAME is set to the empty string"
else
    echo "MAVEN_USERNAME has the value: $MAVEN_USERNAME"
fi
