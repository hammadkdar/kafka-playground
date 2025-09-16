# kafka-playground
a playground to use kafka from different services

API Gateway
-
* Entry point of the system implementing
  * Gateway for the APIs of the system
  * Authentication of the requests by using Auth Server
  * Implements Circuit Breaker for the services
  * Implements Bulkhead for the services
  * Raises events in case of any request and response and handles those events to send messafes to Kafka Topic
  * Use Spring Modulith to store the request/response events and uses Spring Modulith's Kafka support to push events. Spring modulith implements outbox pattern as well to maintain events in the database as well unless the events are sent.

