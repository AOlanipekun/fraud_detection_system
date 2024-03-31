# Real-Time Fraud Detection System

## Overview:
- This program simulates a real-time fraud detection system based on the provided requirements.
- The system processes incoming transactions and detects fraudulent patterns based on the outlined criteria.
- Fraudulent patterns include:
  - A user conducting transactions in more than 3 distinct services within a 5-minute window. 
  - Transactions that are 5x above the user's average transaction amount in the last 24 hours. 
  - A sequence of transactions indicating "ping-pong" activity (transactions bouncing back and forth between two services) within 10 minutes.


## Setup Instructions:
1. Clone the repository locally:
```shell
git clone https://github.com/AOlanipekun/fraud_detection_system.git
cd fraud_detection_system
```
2. Install dependencies
```shell
npm install
```
3. Compile the Java files.
4. Run the main program.
3. Provide input data either from a file or simulated real-time input.

## Algorithm Description:
- The program simulates a real-time fraud detection system based on the provided requirements.
- It maintains a data structure to store user transactions and continuously processes incoming transactions.
- Fraudulent patterns are detected based on the provided criteria, and alerts are generated for flagged users.
- Out-of-order events are handled by maintaining a time window for each user's transactions.

## Handling Out-of-Order Events:
- The program considers event timestamps and network latencies to handle out-of-order events efficiently.

## Assumptions:
- The provided criteria for fraud detection are based on the outlined requirements.

## Test Data and Expected Results:
- The input dataset example provided in the task description can be used for testing.
- Expected results include flagged users based on fraudulent patterns identified by the system.

