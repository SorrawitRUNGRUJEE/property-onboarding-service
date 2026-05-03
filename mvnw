#!/bin/sh
# Maven Wrapper script
# Auto-downloads Maven if not present

set -e

MAVEN_WRAPPER_PROPERTIES=".mvn/wrapper/maven-wrapper.properties"

if [ -f "$MAVEN_WRAPPER_PROPERTIES" ]; then
    MAVEN_DIST_URL=$(grep "distributionUrl" "$MAVEN_WRAPPER_PROPERTIES" | cut -d'=' -f2-)
fi

MAVEN_HOME="${HOME}/.m2/wrapper/dists/apache-maven-3.9.6"
MAVEN_CMD="${MAVEN_HOME}/bin/mvn"

if [ ! -f "$MAVEN_CMD" ]; then
    echo "Maven not found. Downloading..."
    mkdir -p "$MAVEN_HOME"
    TMPFILE=$(mktemp)
    if command -v curl > /dev/null 2>&1; then
        curl -fsSL -o "$TMPFILE" "$MAVEN_DIST_URL"
    elif command -v wget > /dev/null 2>&1; then
        wget -q -O "$TMPFILE" "$MAVEN_DIST_URL"
    else
        echo "Error: curl or wget is required to download Maven."
        exit 1
    fi
    unzip -q -o "$TMPFILE" -d "${HOME}/.m2/wrapper/dists/"
    rm -f "$TMPFILE"
    # Handle nested directory
    if [ ! -f "$MAVEN_CMD" ]; then
        EXTRACTED_DIR=$(find "${HOME}/.m2/wrapper/dists/" -maxdepth 1 -name "apache-maven-*" -type d | head -1)
        if [ -n "$EXTRACTED_DIR" ] && [ "$EXTRACTED_DIR" != "$MAVEN_HOME" ]; then
            rm -rf "$MAVEN_HOME"
            mv "$EXTRACTED_DIR" "$MAVEN_HOME"
        fi
    fi
    chmod +x "$MAVEN_CMD"
    echo "Maven downloaded successfully."
fi

exec "$MAVEN_CMD" "$@"
