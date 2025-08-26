# TransactionFaultDetector
A real-time transaction fault detection system built with Spring Boot, Kafka, Redis, and Elasticsearch. It detects anomalies (e.g., >5 transactions per user in a 1-minute window) in a simulated transaction stream, caches counts in Redis, stores faults in Elasticsearch, and displays results via a simple HTML UI. The project is containerized with Docker for easy deployment and reproducibility.

## Table of Contents
- [Features](#Features)
- [Technologies](#Technologies)
- [Architecture](#Architecture)
- [Prerequisites](#Prerequisites)
- [Setup Instructions](#Setup_Instructions)
- [Running the application](#Running_the_application)
- [Usage](#Usage)
- [Troubleshooting](#Troubleshooting)

## Project Overview

This project demonstrates a scalable, event-driven system for detecting transaction faults in real-time, inspired by fraud detection systems used in fintech (e.g., banks or payment platforms like PayPal). Transactions are streamed via Kafka, cached in Redis for efficient counting, and stored in Elasticsearch for querying and analysis. A simple HTML/JavaScript UI allows users to submit transactions and view detected faults.

The project is containerized using Docker and Docker Compose, ensuring consistent deployment across environments. It serves as a portfolio piece to showcase skills in Java, Spring Boot, streaming, caching, search, and front-end development.

## Features

- Real-Time Processing: Detects transaction faults (e.g., >5 transactions in 1 minute) using Kafka streams.
- Caching: Uses Redis to track transaction counts with a sliding 1-minute window.
- Data Storage: Stores faults in Elasticsearch for historical analysis and querying.
- REST API: Spring Boot provides endpoints to submit transactions and retrieve faults.
- Front-End: Simple HTML/JavaScript UI for user interaction.
- Containerization: Dockerized with Docker Compose for easy setup and reproducibility.
- Scalability: Designed for high throughput with Kafka and Redis.

## Architecture

The system follows an event-driven microservices architecture:

<img width="898" height="202" alt="image" src="https://github.com/user-attachments/assets/6b2af7b1-4d11-4218-b886-2f79c13f2b06" />

- Front-End: Users submit transactions (user ID, amount) via a web UI, which calls the backend API.
- Spring Boot Backend: Handles API requests, produces transactions to Kafka, consumes streams, detects faults, and queries Elasticsearch.
- Kafka: Streams transactions in real-time.
- Redis: Caches transaction counts per user with a 1-minute TTL for efficient fault detection.
- Elasticsearch: Stores faults for historical analysis (e.g., query all faults for a user).

## Prerequisites

- Docker: Install Docker Desktop from docker.com. Includes Docker Compose.
- Git: For cloning the repository.
- Optional: Maven and Java 17 for local development without Docker.

## Setup Instructions

1. Clone the repository

```bash
https://github.com/KrishnaMouryaParuchuri/TransactionFaultDetector.git
cd TransactionFaultDetector/TransactionFaultDetector
```

2. Verify docker installation

```bash
docker --version
docker-compose --version
```

3. Project Structure

TransactionFaultDetector/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/
│   │   │       ├── TransactionFaultDetectorApplication.java
│   │   │       ├── model/
│   │   │       ├── controller/
│   │   │       ├── producer/
│   │   │       ├── consumer/
│   │   │       └── config/
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html
│   │       │   └── script.js
│   │       └── application.properties
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md  

## Running the Application

1. Build and Start Containers:

```bash
docker-compose up --build
```

- Builds the Spring Boot app image and starts Zookeeper, Kafka, Redis, Elasticsearch, and the app.

2. Access the Application:

- Open http://localhost:8080 in a browser to access the UI.

API endpoints:

- POST /api/transactions: Submit a transaction.

- GET /api/transactions/faults: Retrieve detected faults.

Stop the Application:

```bash
docker-compose down
```

- Stops and removes containers, preserving data in memory only 

## Usage

1. Submit Transactions:

- In the UI, enter a User ID (e.g., user123) and Amount (e.g., 100.50).
- Click "Submit Transaction".
- To trigger a fault, submit 6+ transactions for the same user within 1 minute.

2. View Alerts:

- Alerts (e.g., "User: user123, Transactions: 6, Detected: [timestamp]") appear in the UI every 5 seconds.
- Query faults manually: curl http://localhost:9200/faults/_search?pretty.

3. Monitor Logs:

```bash
docker-compose logs -f app
```

- Look for messages like "Fault detected for user user123: 6 transactions in 1 minute".

## Troubleshooting

- Kafka Connectivity:
  - Check Kafka health: docker inspect kafka | grep Health (should show "healthy").
  - Test Kafka: docker exec -it kafka kafka-topics --list --bootstrap-server kafka:9092.

- Redis/Elasticsearch Connectivity:
  - Test Redis: docker exec -it redis redis-cli ping (should return PONG).
  - Test Elasticsearch: curl http://localhost:9200 (should return cluster info).

- Port Conflicts:
  - Cause: Ports 8080, 9092, 2181, 6379, or 9200 are in use.
  - Fix: Check: netstat -tuln | grep 8080 (Linux/macOS) or netstat -a -n -o (Windows). Free ports or change in docker-compose.yml.

- Service Not Ready:
  - Cause: Kafka, Redis, or Elasticsearch not fully started.
  - Fix: Health checks and a 10-second app startup delay are included. Verify logs: docker-compose logs -f <service>.

- App Errors:
  - Check logs: docker-compose logs -f app | grep ERROR.
  - Restart: docker-compose down && docker-compose up --build.

