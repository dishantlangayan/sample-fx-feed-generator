Sample FX Feed Generator README
====================

## Overview

The Sample FX Feed Generator is a sample program that generates FX currency
market data quotes to a Solace Message Router. The currency data is a
snapshot taken from Yahoo Finance service saved in JSON format in the file:
src/main/resources/currency-data.json.

The FX Feed Generator uses json data as the initial starting data. The
quotes for all currency are loaded in an internal cache. A feed randomizer
updates the prices every 3 second to introduce some fluctuations. It also
updates the timestamp values, calculates the price change and change pct.

Everything in the cache is published every second by the feed publisher.

The topic prefix for the messages published is configurable via the config
files. The "name" field from the json currency data file is used as the
last level in the topic name, which is in the format: USD/<Currency>

Example topics:

* demo/MD/D20/FX/USD/CAD
* demo/MD/D20/FX/USD/GBP

There are some other quotes as well for example SILVER FX rates, which are
published on topics like:

* demo/MD/D20/FX/SILVER 1 OZ 999 NY

## Usage

To start the sample fx feed generator:

	1. Extract the zip/tar distribution package
	2. Update the configuration file in config/ folder to point to your
		Solace VMR.
	2. From a command line run the following:
	
	java -jar sample-fx-feed-generator-bin-0.1.0.jar
	
Replace the above command with the correct version of the jar distributed.

## Configuration

The FX Feed Generator uses Spring Boot framework, which allows 
configuration of this application is several ways. The easiest way to 
configure is to modify the configuration file in the config/ directory
of the distribution package. For alternate way, such as configuration
through environment variables refer to the Spring Boot documentation.

## Logging

The FX Feed Generator uses SLF4J logging framework, so any supported
implementation, like LogBack or Log4J can be used to redirect logs to a file.

## Distribution Package

To generate a binary distribution package run:

```
./gradlew clean fatJarZip
```

## Disclaimer

The feed published by this program is for demo and test purposes only.
Do NOT use these feeds for actual trading, re-distribution, or for any 
purpose other testing and demos.

## Support

For any support, bugs, and enhancements, please contact:

Dishant Langayan <dishantlangayan@gmail.com>

## License

Copyright 2017 Dishant Langayan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
