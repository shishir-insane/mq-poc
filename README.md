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
As depicted in the image below, the system will be constructed from multiple distinct components:

1. **Shipment Message Producer API** - This is an API to accept/allow incoming messages and send them to the configured JMS queue. It provides a RESTful endpoint to accept text messages before pushing it to the queue.
2. **Shipment Message Consumer** - Thi
3. Shipment Manager API

![High Level Design](https://github.com/shishir-insane/mq-poc/blob/master/images/hld.png?raw=true)
### Description of Problem
### Technologies Used


### System Operation
## System Components
### 