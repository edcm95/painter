#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
SIMPLE_GRAPHICS_DIR="$SCRIPT_DIR/../simple-graphics"
JAR_NAME="painter-1.0-SNAPSHOT-jar-with-dependencies.jar"

if [ ! -d "$SIMPLE_GRAPHICS_DIR" ]; then
    echo "Cloning SimpleGraphics..."
    git clone https://github.com/academia-de-codigo/simple-graphics.git "$SIMPLE_GRAPHICS_DIR"
else
    echo "SimpleGraphics already cloned, pulling latest..."
    git -C "$SIMPLE_GRAPHICS_DIR" pull
fi

echo "Building and installing SimpleGraphics to local Maven repo..."
mvn install -f "$SIMPLE_GRAPHICS_DIR/pom.xml" -DskipTests

echo "Building Painter..."
mvn install -f "$SCRIPT_DIR/pom.xml" -DskipTests

cp "$SCRIPT_DIR/target/$JAR_NAME" "$SCRIPT_DIR/docs/$JAR_NAME"
echo "Copied JAR to docs/ for GitHub Pages."

echo "Done. Running Painter..."
java -jar "$SCRIPT_DIR/target/painter-1.0-SNAPSHOT-jar-with-dependencies.jar"
