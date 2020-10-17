#!/bin/sh

COUNTRY=AU
LANGUAGE=en
# (also supports FR-fr)

java  -Duser.country="${COUNTRY}" -Duser.language=${LANGUAGE} \
      --enable-preview \
      --add-modules javafx.controls,javafx.fxml,org.controlsfx.controls \
      --module-path ./lib \
      --add-exports javafx.base/com.sun.javafx.event=org.controlsfx.controls \
      -jar javafx-template.jar

