# Clarity Health Platform

An executive intelligence dashboard that unifies healthcare payer data from member management, enrollment, and pharmacy benefit management systems.

## Run locally

```bash
npm install
npm run dev
```

## Validate

```bash
npm run lint
npm run build
```

The current UI uses representative synthetic data. See [ARCHITECTURE.md](./ARCHITECTURE.md) for the production ingestion, identity resolution, semantic layer, and HIPAA safeguard design. A production deployment requires organizational controls, risk analysis, vendor review, BAAs, and approval by the covered entity's privacy and security teams.
