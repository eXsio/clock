# Clock
[![Build Status](https://travis-ci.org/eXsio/clock.svg?branch=master)](https://travis-ci.org/eXsio/clock)
[![Coverity Status](https://scan.coverity.com/projects/8499/badge.svg?flat=1)](https://scan.coverity.com/projects/exsio-clock)
[![codecov](https://codecov.io/gh/eXsio/clock/branch/master/graph/badge.svg)](https://codecov.io/gh/eXsio/clock)


Clock is a simple Java application that serves as a coutndown clock for presenters. It can be controlled from a web page or desktop gui (Depending on execution mode and preferences) and displayed remotely on many devices through a webpage.

## Download

You can download the latest builds here:
- [Executable JAR] (https://drive.google.com/open?id=0B2R7xCap0M7uNlJMY0tkTkROWVk)
- [Deployable WAR] (https://drive.google.com/open?id=0B2R7xCap0M7uUFdRdzVqVy1VVGc)


## Running
This application can be executed in 2 modes:
- webapp
- standalone

By default the app is being build by Maven as a standalone application. If you want to build it as a Web application, you must switch the Maven profiles.

### Webapp

When running as a webapp deployed on a Servlet 3.x compliant container, the following pages are available:
- ```http://SERVER_IP:8080/clock/``` - the clock
- ```http://SERVER_IP:8080/clock/manage.html``` - the clock control panel

The application uses Atmosphere for Push support. You can force the transport method by providing an additional URL parameter 'transport', which can take one of the following values:
- ```long-polling```
- ```sse```
- ```websocket```
- ```jsonp```

### Standalone

When running as a standalone application, aside of the above Webapp functionalities, a Swing GUI will be displayed on the server's desktop with clock controls.

## i18n

Internationalization is achieved using one of my other libraries - [JIN](https://github.com/eXsio/jin). The application is translated into the following languages:

- English (default)
- Polish

The application will by default discover the language of the system (in Swing UI) and in Web Browser (Web UI). To force the language, you must:

- for the Swing UI: manually set the ```user.language``` property (eg. ```-Duser.language=pl ```)
- for the Web UI: add a ```lang``` param to the url (eg. ```/clock/?lang=pl ```)
