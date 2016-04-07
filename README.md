# Clock
[![Build Status](https://travis-ci.org/eXsio/clock.svg?branch=master)](https://travis-ci.org/eXsio/clock)
[![Coverity Status](https://scan.coverity.com/projects/8499/badge.svg?flat=1)](https://scan.coverity.com/projects/exsio-clock)

Clock is a simple Java application that serves as a coutndown clock for presenters. It can be controlled from a web page or desktop gui (Depending on execution mode and preferences) and displayed remotely on many devices through a webpage.

## Running
This application can be executed in 2 modes:
- webapp
- standalone

By default the app is being build by Maven as a standalone application. If you want to build it as a Web application, you must switch the Maven profiles.

### Webapp

When running as a webapp deployed on a Servlet 3.x compliant container, the following pages are available:
- http://SERVER_IP:8080/clock/ - the clock
- http://SERVER_IP:8080/clock/manage.html - the clock control panel

The application uses Atmosphere for Push support. You can force the transport method by providing an additional URL parameter 'transport', which can take one of the following values:
- long-polling
- sse
- websocket
- jsonp

### Standalone

When running as a standalone application, aside of the above Webapp functionalities, a Swing GUI will be displayed on the server's desktop with clock controls.

## TODO
- i18n
