# Chat API Test Suite Documentation

## Overview

This test suite verifies the functionality of the chat API endpoints. It includes tests for:

- Health check endpoint
- Chat creation
- Chat retrieval
- Message sending
- Message retrieval

## Prerequisites

- Python 3.7 or higher
- pip (Python package manager)

## Installation

1. Install the required packages:

```bash
pip install pytest requests python-dotenv
```

## Environment Setup

1. Copy the `.env.example` file to `.env` in the root directory:

```bash
cp ../.env.example ../.env
```

2. Update the `.env` file with your API configuration:

```env
API_BASE_URL=https://se-backend.up.railway.app/api
```

## Running the Tests

From the project root directory (`software_backend`), run:

```bash
python -m pytest tests/test_chat_api.py -v
```

To run a specific test:

```bash
python -m pytest tests/test_chat_api.py::test_name -v
```

## Test Structure

The test suite follows a modular approach with separate test functions for each endpoint:

- `test_health_check()`: Verifies API accessibility
- `test_create_chat()`: Tests chat session creation
- `test_get_chat()`: Tests chat retrieval
- `test_send_message()`: Tests message sending
- `test_get_messages()`: Tests message retrieval

## Expected Results

- All tests should pass with a 200 or 201 status code
- Response data should match the expected format
- Error cases should be handled appropriately

## Troubleshooting

If tests fail:

1. Verify the API server is running
2. Check the BASE_URL in the .env file
3. Ensure all required endpoints are implemented
4. Check network connectivity
5. Verify all environment variables are set correctly
