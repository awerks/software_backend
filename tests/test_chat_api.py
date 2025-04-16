"""
Chat API Test Suite
------------------
This file contains test cases for verifying the functionality of the chat API endpoints.
The tests cover basic CRUD operations and message handling functionality.

Architecture:
- Uses pytest for testing
- Implements REST API testing using requests library
- Follows a modular approach with separate test functions for each endpoint

Key Concepts:
- API Testing: Verifying the functionality, reliability, and performance of API endpoints
- HTTP Methods: Testing GET, POST, PUT, DELETE operations
- Response Validation: Checking status codes, response formats, and data integrity
"""

import pytest
import requests
import json
from datetime import datetime
import os
from dotenv import load_dotenv

# Load environment variables
load_dotenv()

# Base URL for the API - using environment variable if available, otherwise default to local server
BASE_URL = os.getenv('API_BASE_URL', 'http://localhost:8080')
API_URL = f"{BASE_URL}/api"

# Test user credentials
TEST_USER = {
    "username": "test_user",
    "password": "test_password",
    "firstName": "Test",
    "lastName": "User",
    "email": "test@example.com",
    "birthdate": "2000-01-01",
    "role": "user"
}

def register_test_user():
    """
    Register a test user
    """
    response = requests.post(
        f"{API_URL}/auth/register",
        json=TEST_USER,
        headers={"Content-Type": "application/json"}
    )
    if response.status_code != 201:
        print(f"Registration failed with status {response.status_code}")
        print(f"Response: {response.text}")
    assert response.status_code == 201
    return response.json()

def get_auth_token():
    """
    Get authentication token for test user
    """
    # First register the user
    try:
        register_test_user()
    except AssertionError:
        # User might already exist, try logging in directly
        pass
    
    # Then login
    login_data = {
        "usernameOrEmail": TEST_USER["username"],
        "password": TEST_USER["password"]
    }
    response = requests.post(
        f"{API_URL}/auth/login",
        json=login_data,
        headers={"Content-Type": "application/json"}
    )
    if response.status_code != 200:
        print(f"Login failed with status {response.status_code}")
        print(f"Response: {response.text}")
    assert response.status_code == 200
    return response.cookies.get("access_token")

def test_health_check():
    """
    Test the health check endpoint to ensure the API is accessible
    """
    response = requests.get(BASE_URL)
    if response.status_code != 200:
        print(f"Health check failed with status {response.status_code}")
        print(f"Response: {response.text}")
    assert response.status_code == 200
    assert "Hello world" in response.text

def create_teamspace(token):
    """
    Create a teamspace for testing
    """
    teamspace_data = {
        "name": "Test Teamspace",
        "description": "Test teamspace for chat testing"
    }
    response = requests.post(
        f"{API_URL}/teamspaces",
        json=teamspace_data,
        headers={
            "Content-Type": "application/json",
            "Cookie": f"access_token={token}"
        }
    )
    if response.status_code != 201:
        print(f"Create teamspace failed with status {response.status_code}")
        print(f"Response: {response.text}")
    assert response.status_code == 201
    data = response.json()
    assert "teamspaceId" in data
    return data["teamspaceId"]

def test_send_message():
    """
    Test sending a message in a teamspace chat
    """
    token = get_auth_token()
    teamspace_id = create_teamspace(token)
    message_data = {
        "content": "Hello, this is a test message",
        "sender": TEST_USER["username"]
    }
    response = requests.post(
        f"{API_URL}/teamspaces/{teamspace_id}/chat",
        json=message_data,
        headers={
            "Content-Type": "application/json",
            "Cookie": f"access_token={token}"
        }
    )
    if response.status_code != 201:
        print(f"Send message failed with status {response.status_code}")
        print(f"Response: {response.text}")
    assert response.status_code == 201
    data = response.json()
    assert data["content"] == message_data["content"]
    assert data["sender"] == message_data["sender"]
    return teamspace_id

def test_get_messages():
    """
    Test retrieving messages from a teamspace chat
    """
    token = get_auth_token()
    teamspace_id = test_send_message()  # This will create a teamspace and send a message
    response = requests.get(
        f"{API_URL}/teamspaces/{teamspace_id}/chat",
        headers={"Cookie": f"access_token={token}"}
    )
    if response.status_code != 200:
        print(f"Get messages failed with status {response.status_code}")
        print(f"Response: {response.text}")
    assert response.status_code == 200
    data = response.json()
    assert isinstance(data, list)
    assert len(data) > 0
    assert data[0]["content"] == "Hello, this is a test message"

if __name__ == "__main__":
    pytest.main([__file__]) 