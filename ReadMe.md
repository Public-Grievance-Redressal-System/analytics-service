# Analytics Service

Analytics Service for a Public Grievance Redressal System.

## Table of Contents

- [Getting Started](#getting-started)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Authentication](#authentication)
- [Testing](#testing)

## Getting Started

To run this API locally, you'll need [list prerequisites]. Follow these steps to get started:

1. [Installation instructions]
2. [Configuration steps]
3. [Running the API]

## Usage

Here's how you can use this API to [describe a common use case]:


## Endpoints

- ### `/api/v1/analytics/metrics/tickets` (GET)
This endpoint allows you to retrieve ticket data based on specific parameters.

**Request Parameters:**

- `from` (required): The start date for the data retrieval in the format `DDMMYYYY`.
- `to` (required): The end date for the data retrieval in the format `DDMMYYYY`.
- `rangeFrequency` (required): The frequency of the data range (e.g., "DAILY," "WEEKLY," "MONTHLY").
- `department_id` (optional): The unique identifier of the department for filtering ticket data.
- `region_id` (optional): The unique identifier of the region for filtering ticket data.
- `status` (required): The status of the tickets (e.g., "OPEN," "RESOLVED," "PENDING").

**Example Request:**

```http
GET /api/v1/analytics/metrics/tickets?from=01092021&to=10112023&rangeFrequency=DAILY&department_id=75677a61-2420-41af-be68-d673175a5b9f&region_id=c8bdbb5f-689d-42de-996b-fbd31990fbf9&status=OPEN
```

- ### `/api/v1/analytics/metrics/average-resolution-time` (GET)

This endpoint allows you to retrieve the average resolution time metrics based on specific parameters.

**Request Parameters:**

- `from` (required): The start date for the data retrieval in the format `DDMMYYYY`.
- `to` (required): The end date for the data retrieval in the format `DDMMYYYY`.
- `department_id` (optional): The unique identifier of the department for filtering average resolution time data.
- `region_id` (optional): The unique identifier of the region for filtering average resolution time data.
- `rangeFrequency` (optional): The frequency of the data range (e.g., "DAILY," "WEEKLY," "MONTHLY").

**Example Request:**

```http
GET /api/v1/analytics/metrics/average-resolution-time?from=01092021&to=10112023&department_id=75677a61-2420-41af-be68-d673175a5b9f&region_id=c8bdbb5f-689d-42de-996b-fbd31990fbf9&rangeFrequency=DAILY
```

### `/api/v1/analytics/generate-report` (POST)

This endpoint allows you to generate a report based on specified parameters by sending a JSON object in the request body.

**Request Parameters:**

- `metricType` (required): The type of metric for generating the report (e.g., "TicketBasedOnStatus").
- `from` (required): The start date for the data retrieval in the format `DDMMYYYY`.
- `to` (required): The end date for the data retrieval in the format `DDMMYYYY`.
- `rangeFrequency` (required): The frequency of the data range (e.g., "DAILY," "WEEKLY," "MONTHLY").
- `department_id` (optional): The unique identifier of the department for filtering report data.
- `region_id` (optional): The unique identifier of the region for filtering report data.
- `status` (required): The status of the tickets (e.g., "OPEN," "RESOLVED," "PENDING").

**Example Request:**

```http
POST /api/v1/analytics/generate-report
Content-Type: application/json

{
  "metricType": "TicketBasedOnStatus",
  "from": "01092021",
  "to": "10112023",
  "rangeFrequency": "DAILY",
  "department_id": "some uuid",
  "region_id": "some uuid",
  "status": "OPEN"
}
```
### `/api/v1/analytics/reports` (GET)

This endpoint allows you to fetch a list of reports.

**Request Parameters:**

- No specific request parameters for this GET request.

**Example Request:**

```http
GET /api/v1/analytics/reports
```
**Response:**
```http
Content-Type: application/json
[
{
"generated_by": "some_uuid",
"metricType": "TicketBasedOnStatus",
"report_url": "https://example.com/report/1",
"generated_at": "2023-11-10T12:30:00Z"
},
{
"generated_by": "another_uuid",
"metricType": "AverageResolutionTime",
"report_url": "https://example.com/report/2",
"generated_at": "2023-11-10T13:45:00Z"
},
// Additional reports...
]
```
## Authentication

This API uses [type of authentication], and you can set it up by following these steps:
1. [Authentication setup instructions]
2. [How to include authentication headers in requests]


