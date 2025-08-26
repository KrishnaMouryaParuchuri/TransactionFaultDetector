# TransactionFaultDetector
A real-time transaction fault detection system built with Spring Boot, Kafka, Redis, and Elasticsearch. It detects anomalies (e.g., >5 transactions per user in a 1-minute window) in a simulated transaction stream, caches counts in Redis, stores faults in Elasticsearch, and displays results via a simple HTML UI. The project is containerized with Docker for easy deployment and reproducibility.

## Table of Contents
- [Installation](#installation)
- [Features](#features)
- [Technologies](#technologies)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Setup-Instructions](#setup-instructions)
- [Running the Application](#running the application)
- [Usage]
- [Troubleshooting](troubleshooting)
