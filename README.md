# TransactionFaultDetector
A real-time transaction fault detection system built with Spring Boot, Kafka, Redis, and Elasticsearch. It detects anomalies (e.g., >5 transactions per user in a 1-minute window) in a simulated transaction stream, caches counts in Redis, stores faults in Elasticsearch, and displays results via a simple HTML UI. The project is containerized with Docker for easy deployment and reproducibility.

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup instructions)
- [Running the Application](#running the application)
- [Usage]
- [Troubleshooting](troubleshooting)

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

<img width="715" height="192" alt="image" src="https://github.com/user-attachments/assets/9ca2659a-aec7-4b19-abca-1d76a28baa59" />

- Front-End: Users submit transactions (user ID, amount) via a web UI, which calls the backend API.
- Spring Boot Backend: Handles API requests, produces transactions to Kafka, consumes streams, detects faults, and queries Elasticsearch.
- Kafka: Streams transactions in real-time.
- Redis: Caches transaction counts per user with a 1-minute TTL for efficient fault detection.
- Elasticsearch: Stores faults for historical analysis (e.g., query all faults for a user).

## Setup Instructions

## Running the Application
