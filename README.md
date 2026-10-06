# Naikeri Signaling Gateway

A Java library for building signalling applications over SS7 and Diameter. An application declares its
protocol layers, channels and routing in one XML configuration, and the gateway sets up the stacks and
delivers each message to the application's processing logic.

Layers: SCTP, M3UA, SCCP, TCAP, MAP and CAP on [jSS7](https://github.com/FerUy/naikeri-jss7), and
Diameter on [jDiameter](https://github.com/FerUy/naikeri-jdiameter). Link and association states are
exported in a form Prometheus can collect.

Built on it:

- [Naikeri HRR](https://github.com/FerUy/naikeri-home-rerouting): MAP and CAP Home Re-Routing proxy
- [Naikeri DRA](https://github.com/FerUy/naikeri-dra): Diameter Routing Agent

## Building

Java 11 and Maven 3.9:

~~~
mvn clean install
~~~

Jenkins publishes `naikeri-signaling-gateway-core` to the Naikeri Artifactory as `<major>-<build>`, for
example `2.2.0-112`.

## Configuration

An application starts the gateway with `SignalingGateway.initialize(args)`. The first argument names the
configuration file (`naikeri-signaling-gateway.xml` when none is given), which is looked up in the
directory named by `-DmainConfig.path`, then on the classpath. One installation can therefore keep several
configurations and choose between them at start-up.

The gateway logs through SLF4J.
