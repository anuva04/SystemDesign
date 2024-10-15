# Rate Limiter
Design a rate limiter to prevent a user from making too many requests to a service within a certain time frame.

## Functional Requirements
- A user can be identified using either a cookie, userId or IP address.
- If the throttling threshold is surpassed, the rate limiter will reject the request and send a "429 Too Many Requests" status code back to the client. Otherwise, it will forward the request to intended server.

## Non-functional Requirements
- The service should have low latency.
- The service should be highly available.

## Resource estimation

### Requirements
- 100 million daily active users.
- Data retention is 1 day.
- Each user makes 1,000 requests per day. 
- Each request and response are 1 KB in size. 
- We need to store each user's remaining capacity and the timestamp of their last request. Let's say that takes 50 bytes.

The end-users of the service won't be writing anything, so we are only concerned about read QPS. <br>
`Read QPS = (DAU x number of requests per user)/(24x60x60) = 1.16M/sec`

Given, each request and response is 1 KB in size, `total bandwidth = 1.16Mx1KBx2/sec = 2.32GBps`.

As we are retaining data of 1 day, `total storage per day = DAU x size of each record = 100M x 50B = 5000MB = 5GB/day`. Old data can be simply discarded as it doesn't affect future decisions. Else, it can be archived for future analysis.

## API design
The APIs used by end-users do not change when a rate limiter is introduced to the system. However, existing API responses may need to be modified to reflect rate limiter's response (e.g., `429 Too Many Requests`).

On the other hand, some internal APIs have to be developed for management purposes.
- APIs for setting/modifying rate limit rules.
- APIs for consuming telemetry and monitoring data.
These features are beyond the scope of functional requirements of this problem.

## Data storage
We need to store data which is required to determine whether a request from a user should be forwarded or rejected and this operation should be fast. So, the chosen data store should be able to serve fast reads and writes. Given, low latency and fast read/write are key requirements, an in-memory DB would be a good choice (e.g., Redis). Potential race conditions that may arise when multiple API calls try to read and modify a particular value for a user are easily handled by Redis itself via Lua scripts. The data can be periodically snapshotted to a persistent storage if needed. 

As for schema, for commonly used algorithms like Token Bucket or Sliding Window, a simple key-value store would suffice. <br>
```
Key: UserId / APIKey / IPAddress
Value: Number of remaining tokens / timestamp of last request (for slidiing window log)
```

## High-level design

### Where to place the rate limiter service?
The placement of a rate limiter in a system would vary with the requirements. Some of the common approaches are:
- **At the load balancer**: This helps filter out excessive requests early on in the network path before the request hits the backend service. This helps reduce load on the infrastructure and also helps mitigate DDoS attacks. However, it goes against the concept of separation of concerns, as load balancers primarily focus on distributing traffic to internal servers.
- **At API Gateway**: This approach provides more flexible control over individual components of the system. Independent limits can be set at API level and rate limiting can be based on more granular params like API Keys, user auth tokens etc. E.g., if a service exposes 2 APIs `api1` and `api2` of which the first API is free and can be used by anyone, whereas the second API requires a user to be authorized. So, while calling the second API, user will probably pass an API key or an auth token in header. Using this, the API gateway can determine if the request should be forwarded or dropped.
- **At application layer**: The application itself has full access to the request data. This helps in implementing complex rate limiting logic based on various business requirements. E.g., for LLMs, rate limit might be set on the amount of data being sent in the payload - number of token in chat based systems, size of media etc. <br> However, for this approach, the request has to travel all the way up to the application, so it introduces some latency and also puts extra burden on application servers.

An ideal system might be a combination of the above approaches as per business requirements.

### What algorithm to use?
Some common algorithms are as follows.

#### Token bucket
In this algorithm, each user is assumed to have a bucket of tokens. The number of tokens in the bucket is the number of requests that will be accepted by the service. Any extra requests will be rejected. Tokens are added to the bucket periodically (e.g., 1 token per minute). When a user makes a request, it is rejected if the bucket is empty, else a token is removed from the bucket and the request is forwarded.

This algo is simple to implement. It allows bursts up to the capacity of the bucket. In a key-value store, it can be stored as an integer will is decremented everytime a user makes a request, and incremented by some background process periodically.

#### Leaky Bucket
In this algorithm, requests are added to a queue or bucket and are processed at a fixed rate. If the bucket is full, new requests are discarded, i.e., sudden surge in requests are not handled beyond the capacity of the bucket.

This algo is slightly more complex to implement as we need to store each request in a queue.  Requests are not processed immediately, it is asynchronous. It takes more space to store data of all unprocessed requests. Requests are always processed with a steady rate, so there are no spikes ever.

#### Fixed window counter
In this algorithm, timeline is divided into fixed non-overlapping slots. A fixed number of requests are processed in each slot for each user. A counter is kept for the number of remaining requests in current slot. If this value is 0, new requests are discarded until the next slot starts where this counter is reset.

It is simple to implement, but it cannot handle bursts at the edge of slots. A user can fire max requests at the end of one slot and the beginning of the next. This would create a 2x surge in requests.

#### Sliding window log
This algorithm keeps a log of timestamps of all requests. Window size is pre-determined. When a new request comes, it checks the number of requests that have been processed in the window ending at current time. If it doesn't exceed the limit, the new request is processed.

It is quite accurate and intuitive, and doesn't allow sudden spikes beyond the limit of a window at any point in time. However, it is more complex to implement that Fixed Window counter. Also, it takes more space to store timestamps of all requests in the window.

#### Sliding window counter
In this algorithm, first, timeline is divided into fixed windows, and on top of that there is a sliding window. Two counters keep track of number of requests in the previous fixed window and current fixed window. When a new request arrives, first it is determined how much % of time has elapsed from the current fixed window. Then a weighted sum of requests is calculated for current sliding window using the formula:<br>
`number of requests = (100 - %time) * number of requests in previous fixed window + number of requests in current fixed window`

It is more accurate in fixed window counter and more memory-efficient than sliding window log.

### Bottlenecks and Scaling

#### Sharding
As the read QPS is 1.16M/sec, a single rate limiter instance won't be able to handle all requests efficiently even in a normal scenario, so we need to scale out. The amount of data to be stored per day is 5GB which can be easily handled in RAM. However, syncing the data across all instances would be complicated, so it's better to shard the data and let each instance handle a specific shard only.

Data can be sharded by whatever user identifier is being used for rate limiting. Some geographical info can also be added to the user id so that it can be sharded geographically. Then, rate limiter instances can serve requests in their respective geographical locations only. 

A set of closely located instances can share a database shard if the number of instances are much more than the number of shards. In that case, sync has to be maintained amongst these instances only.

## Follow-up questions

1. Is caching helpful?
> No, because the data will be modified very often i.e., whenever a new request is being processed, the number of remaining tokens for the user has to be updated. Rather, for fast read/write, in-memory DB is used.