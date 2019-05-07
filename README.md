# mq-poc
A POC using Spring Boot and ActiveMQ

## Introduction
### Purpose
The purpose of this proof-of-concept is to use Apache AMQ or Rabbit MQ as JMS Provider and develop a solution using Java to receive a message in a given text format, perform validations and invoke another REST-based microservice to convey the result of validations.
### Scope
The scope of this project includes:

1. Software code for the POC solution
2. Test Automation with Jmeter

## Design Overview
### System Architecture
The system is constructed from multiple distinct components. These components are briefly described as below.

1. **Shipment Message Producer API** - This is the message producer layer, consistign of an API to accept/allow incoming messages and send them to the configured JMS queue. It provides a RESTful endpoint to accept text messages before pushing it to the queue.
2. **Shipment Message Consumer** - This is a message cosumer layer of the application which consumes the message from the queue, performs technical and business validations and further dispatches to the subsequent layer.
3. **Shipment Manager API** - This is the message processor/manager layer of the application which exposes RESTful API(s) to manage messages.

Below is the high level system architecture of this POC application.

![High Level Design](https://github.com/shishir-insane/mq-poc/blob/master/images/hld.png?raw=true)
### Description of Problem
### Technologies Used


### System Operation
Below diagram is the typical sequence of events that occur during the consumption of a new message.

![New message consumption sequence diagram](https://github.com/shishir-insane/mq-poc/blob/master/images/processConsumedMessage-seq.png?raw=true)

## System Components
### Shipment Message Producer API
![Producer Class Diagram](https://github.com/shishir-insane/mq-poc/blob/master/images/producer-class-diagram.png?raw=true)

### Shipment Message Consumer
![Consumer Class Diagram](https://github.com/shishir-insane/mq-poc/blob/master/images/consumer-class-diagram.png?raw=true)

### Shipment Manager API
![Manager Class Diagram](https://github.com/shishir-insane/mq-poc/blob/master/images/manager-class-diagram.png?raw=true)