# VitalSyncAI

An executive intelligence dashboard that unifies healthcare payer data from member management, enrollment, and pharmacy benefit management systems.

## Current build status

Step 1 of the PRD implementation is complete:

- Spring Boot backend scaffold
- Unified member, enrollment, and pharmacy domain model
- Synthetic, de-identified demo records
- Executive dashboard aggregation endpoint
- Member list and Member 360 endpoints
- H2 local database and PostgreSQL deployment profile
- Integration tests for dashboard metrics and cross-source lineage

Step 2.1 authentication is also complete:

- Signed, one-hour JWT access tokens
- Stateless bearer-token validation on protected APIs
- BCrypt-protected local demo accounts
- Issuer and expiration validation
- External `JWT_SECRET` configuration for deployments

Role-specific endpoint authorization, PHI policy enforcement, and persistent audit logging remain required before deployment.

## Run the frontend

```bash
npm install
npm run dev
```

## Run the backend

```powershell
cd backend
$env:JAVA_HOME='C:\Program Files\Java\jdk-25'
.\mvnw.cmd spring-boot:run
```

The local API is available at `http://localhost:8080/api/v1`:

- `GET /dashboard`
- `GET /members`
- `GET /members/{memberId}`

Authenticate through `POST /api/v1/auth/login`. Local synthetic-data demo accounts:

- Executive: `executive@vitalsync.ai` / `DemoExecutive!2026`
- Analyst: `analyst@vitalsync.ai` / `DemoAnalyst!2026`

Send the returned token as `Authorization: Bearer <accessToken>`. Change all demo credentials and set a strong `JWT_SECRET` outside source control before deployment.

## Validate

```bash
npm run lint
npm run build
cd backend
.\mvnw.cmd test
```

The current UI uses representative synthetic data. See [ARCHITECTURE.md](./ARCHITECTURE.md) for the production ingestion, identity resolution, semantic layer, and HIPAA safeguard design. A production deployment requires organizational controls, risk analysis, vendor review, BAAs, and approval by the covered entity's privacy and security teams.
