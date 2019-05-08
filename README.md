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

1. **Message Producer API** - This is the message producer layer, consistign of an API to accept/allow incoming messages and send them to the configured JMS queue. It provides a RESTful endpoint to accept text messages before pushing it to the queue.
2. **Message Consumer** - This is a message cosumer layer of the application which consumes the message from the queue, performs technical and business validations and further dispatches to the subsequent layer.
3. **Validation API** - This is the message validation layer of the application which exposes RESTful API to receive and manage messages.
3. **Data Manager API** - This is the message processor/manager layer of the application which exposes RESTful API(s) to manage messages.

Below is the high level system architecture of this POC application.

![High Level Design](https://github.com/shishir-insane/mq-poc/blob/master/images/hld.png?raw=true)
### Description of Problem
### Technologies Used


### System Operation
Below diagram is the typical sequence of events that occur during the consumption of a new message.

![New message consumption sequence diagram](https://github.com/shishir-insane/mq-poc/blob/master/images/processConsumedMessage-seq.png?raw=true)

## System Components
### Message Producer API
Below diagram depicts the UML model for the Shipment Message Producer API. Operations depicted in this diagram originate from the RESTful interface. However, the incoming messages can be pushed to the queue via any other means as well. 

![Producer Class Diagram](https://github.com/shishir-insane/mq-poc/blob/master/images/producer-class-diagram.png?raw=true)

### Message Consumer
Below diagram depicts the UML model for the Shipment Message Consumer application. Operations depicted in this diagram originate after the message is consumed from the queue. The action performed in the sequence in this component are shown under **System Operations**.

![Consumer Class Diagram](https://github.com/shishir-insane/mq-poc/blob/master/images/consumer-class-diagram.png?raw=true)

### Validation Manager API
Below diagram depicts the UML model for the Shipment Message Validation Manager API. Operations depicted in this diagram originate after the message is is POSTed to this application using the RESTful interface by the consumer application. The action performed in the sequence in this component are shown under **System Operations**.

![Consumer Class Diagram](https://github.com/shishir-insane/mq-poc/blob/master/images/validation-manager-class-diagram.png?raw=true)

### Data Manager API
Below diagram depicts the UML model for the Shipment Message Manager API. Operations depicted in this diagram originate from the RESTful interface provided to POST new messages to this manager application.

![Manager Class Diagram](https://github.com/shishir-insane/mq-poc/blob/master/images/manager-class-diagram.png?raw=true)