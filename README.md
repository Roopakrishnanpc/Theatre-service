Theatre Service:
	•	CQRS
	•	Separate DB
	•	Scheduling
	•	CAP theorem
	•	Caching
	•	Structured logging
	•	Security
	•	Deployment
	•	Future roadmap

Theatre Service

Overview

The Theatre Service manages theatres and movie show listings within the Movie Ticket Platform.

It follows a CQRS (Command Query Responsibility Segregation) architecture with separate read and write models and databases.

This service enables:
	•	Theatre creation and management
	•	Show scheduling and deactivation
	•	Movie availability search by city and date
	•	Role-based command operations
	•	Optimized read-side browsing



Architecture

Design Approach
	•	Microservice architecture
	•	CQRS pattern (separate read and write flows)
	•	Separate read and write databases
	•	Role-based security
	•	Stateless service
	•	Soft delete strategy
	•	Structured logging
	•	Scheduled background tasks



Responsibilities

Query (Read Side)
	•	Retrieve theatres by city
	•	Browse shows by:
	•	City
	•	Movie
	•	Date
	•	Retrieve show details by ID

Command (Write Side)
	•	Create theatre (PARTNER / ADMIN)
	•	Delete theatre (soft delete)
	•	Create show (PARTNER / ADMIN)
	•	Delete show (soft deactivate)



Technology Stack
	•	Spring Boot
	•	Spring Security
	•	JPA / Hibernate
	•	MySQL
	•	CQRS pattern
	•	Scheduled tasks
	•	In-memory cache
	•	Structured logging
	•	REST API



API Endpoints

Query Endpoints

Base Path:

/api/v1/tickets/query

Get Theatres by City

GET /theatres?city=Chennai

Browse Shows

GET /shows?city=Chennai&movie=Jailer&date=2026-02-23

Get Show By ID

GET /show/{id}




Command Endpoints

Base Path:

/api/v1/tickets/command

Create Theatre (PARTNER / ADMIN)

POST /theatres

Delete Theatre

DELETE /theatres/{id}

Create Show (PARTNER / ADMIN)

POST /shows

Delete Show

DELETE /shows/{id}




Security Model
	•	JWT-based authentication
	•	Role-based access control using @PreAuthorize
	•	Only PARTNER and ADMIN can modify theatres and shows
	•	Customers can access query endpoints
	•	Stateless session management



CQRS Implementation

Command Side
	•	Uses write database
	•	Ensures strong consistency
	•	Handles validation and business rules
	•	Implements soft delete (active flag)

Query Side
	•	Uses read database
	•	Optimized for search and browsing
	•	Uses DTO projections for efficient queries
	•	Improves read scalability



Database Strategy

Separate Databases
	•	Write DB → Transactional integrity
	•	Read DB → Query optimization

Benefits:
	•	Scalability
	•	Performance optimization
	•	Fault isolation
	•	Read-heavy workload optimization



Scheduling

Background scheduled tasks handle:
	•	Automatic deactivation of expired shows
	•	Data consistency checks
	•	Cleanup operations

Scheduling ensures:
	•	Time-based business rules enforcement
	•	Reduced manual intervention
	•	Data accuracy



Caching Strategy
	•	In-memory cache for frequently accessed data
	•	Improves browsing performance
	•	Reduces database load

Redis was intentionally not used in the current environment due to container configuration constraints.

Future plan:
	•	Replace local cache with Redis cluster
	•	Distributed caching for multi-instance deployments



CAP Theorem Consideration

In distributed deployment:
	•	Prioritized Consistency and Partition Tolerance
	•	Eventual consistency acceptable for read operations
	•	Strong consistency enforced during write operations

Tradeoff decisions:
	•	Write operations prioritize data correctness
	•	Read operations tolerate minimal propagation delay


Soft Delete Strategy

Instead of physical deletion:
	•	Records are marked active = false
	•	Historical data preserved
	•	Prevents data inconsistency
	•	Enables auditability


Structured Logging

The service uses structured logging for observability and production readiness.

Features:
	•	Consistent log format
	•	Log levels (INFO, DEBUG, ERROR)
	•	Context-aware logging
	•	Traceable HTTP request logs
	•	Business event logging

Example log structure:

HTTP Request -> Browse: city=Chennai, movie=Jailer, date=2026-02-23

Benefits:
	•	Easier debugging
	•	Production monitoring
	•	Log aggregation compatibility (ELK, CloudWatch, Azure Monitor)
	•	Improved traceability

Future enhancements:
	•	Correlation IDs
	•	Distributed tracing integration
	•	Centralized logging pipeline

Deployment

Docker

docker build -t theatre-service .
docker run -p 8082:8080 theatre-service


Kubernetes
	•	Deployed as Deployment
	•	Exposed via ClusterIP
	•	Horizontal Pod Autoscaler supported
	•	Internal service discovery via DNS

Scalability Plan
	•	Horizontal scaling via replicas
	•	CPU-based HPA
	•	Read-side scaling independently
	•	Future Redis integration for distributed caching

Future Enhancements
	•	Redis for distributed caching
	•	Kafka for event-driven synchronization
	•	Read replica database
	•	Circuit breaker implementation
	•	Prometheus + Grafana monitoring
	•	Centralized logging (ELK stack)
	•	Distributed tracing

Architectural Role

The Theatre Service manages show metadata and theatre configuration.

It is optimized for high read traffic, supports scalable deployment, and enforces strict write-side validation through CQRS separation.

It serves as the foundational catalog service for the booking system.
