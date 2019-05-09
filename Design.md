# Messaging Queue POC
A POC using Spring Boot and Apache ActiveMQ

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

### Technologies Used
1. Java 1.8
2. Spring Boot 2.1
3. Apache ActiveMQ 5.15

### System Operation
Below diagram is the typical sequence of events that occur during the consumption of a new message.

![New message consumption sequence diagram](https://github.com/shishir-insane/mq-poc/blob/master/images/processConsumedMessage-seq.png?raw=true)

### Design Considerations
1. For this POC, single instances of system components and queue server/node is considered. However, design allows any of the components to be scaled up as per the load.
2. For reasons in #1, the **Validation Manager API** is separated from the **Message Consumer** component so that scaling decisions are not dependent on each other.
3. No datastore is considered in this POC and all the information/data is visible deligently on persistent log files.
4. Failover/replay of queue messages is not configured in this POC. The commit mode is auto by default which can be changed easily in configurations.
5. High availability of system components is not stressed in this POC.  
6. Non functional requirements such as flexibility, extensibility, performance, reliability, testability and manageability are taken care while designing the solution.

## System Components
### Message Producer API
Below diagram depicts the UML model for the Shipment Message Producer API. Operations depicted in this diagram originate from the RESTful interface. However, the incoming messages can be pushed to the queue via any other means as well. 

![Producer Class Diagram](https://github.com/shishir-insane/mq-poc/blob/master/images/producer-class-diagram.png?raw=true)

### Message Consumer
Below diagram depicts the UML model for the Shipment Message Consumer application. Operations depicted in this diagram originate after the message is consumed from the queue. The action performed in the sequence in this component are shown under **System Operations**.

![Consumer Class Diagram](https://github.com/shishir-insane/mq-poc/blob/master/images/consumer-class-diagram.png?raw=true)

### Validation API
Below diagram depicts the UML model for the Shipment Message Validation Manager API. Operations depicted in this diagram originate after the message is is POSTed to this application using the RESTful interface by the consumer application. The validation of the message is performed pattern matching text parse technique alongwith simple conditions. The action performed in the sequence in this component are shown under **System Operations**.

![Consumer Class Diagram](https://github.com/shishir-insane/mq-poc/blob/master/images/validation-manager-class-diagram.png?raw=true)

### Data Manager API
Below diagram depicts the UML model for the Shipment Message Manager API. Operations depicted in this diagram originate from the RESTful interface provided to POST new messages to this manager application.

![Manager Class Diagram](https://github.com/shishir-insane/mq-poc/blob/master/images/manager-class-diagram.png?raw=true)