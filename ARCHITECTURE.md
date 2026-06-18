# Clarity Health payer data platform

The dashboard in `CareerPilot` is a working executive experience backed by representative data. A production deployment should use the following pipeline. HIPAA compliance is an operating model—not a UI claim—and must be validated by the covered entity's privacy, security, and legal teams.

## Data flow

```text
Member management ─┐
Enrollment ─────────┼─> Private ingestion ─> Raw PHI vault ─> Validate/tokenize
PBM claims ─────────┘         │                    │                 │
                              └─ audit events      └─ immutable      v
                                                            Curated payer model
                                                                     │
                                                      Policy + semantic layer
                                                                     │
                                                  Executive API / Clarity AI
```

1. **Private ingestion:** Receive CDC events, encrypted object batches, or private API traffic. Authenticate workloads with short-lived identities. Reject unencrypted and unsigned payloads.
2. **Raw PHI vault:** Write encrypted, immutable source records with source, arrival time, schema version, and content hash. Restrict this zone to ingestion and break-glass roles.
3. **Validation and identity:** Quarantine schema failures; normalize code systems; resolve members with a controlled crosswalk; tokenize direct identifiers before analytical processing.
4. **Curated model:** Publish idempotent member, coverage span, plan, pharmacy claim, prescriber, drug, and geography facts. Preserve source lineage and effective timestamps on every row.
5. **Semantic layer:** Define governed metrics such as covered lives, active enrollment, PMPM drug spend, lapse risk, and data quality. Apply row, column, and minimum-cohort policies before query execution.
6. **Serving:** Expose only approved metrics through a private API. Cache aggregate results, never unrestricted PHI. Natural-language queries compile to allow-listed semantic operations rather than arbitrary SQL.

## Required safeguards

- Encrypt data in transit and at rest with managed keys, rotation, separation of duties, and audited key use.
- Use SSO, MFA, least-privilege RBAC/ABAC, short sessions, periodic access reviews, and a documented break-glass flow.
- Log authentication, query intent, policy decisions, record scope, exports, administrative changes, and key access to immutable storage. Never place PHI values in application logs.
- Mask direct identifiers by default and suppress small cohorts. Require a purpose-of-use claim and elevated role for member-level access.
- Use synthetic or properly de-identified data in development and CI. Keep production PHI out of prompts, telemetry, support tools, and third-party processors without a BAA and approved data flow.
- Define retention and deletion by record class. Backups inherit the same controls, retention, and restore testing.
- Monitor freshness, completeness, uniqueness, validity, referential integrity, and source-to-target reconciliation. Quarantine bad data instead of silently publishing it.
- Complete risk analysis, vendor/BAA review, incident response exercises, disaster recovery tests, workforce training, and documented policies before launch.

## Core metric contracts

| Metric | Governed definition | Freshness target |
|---|---|---|
| Covered lives | Distinct tokenized members with an effective coverage span at query time | 5 minutes |
| Active enrollment | Covered lives divided by eligible lives for the selected plan and period | 5 minutes |
| Pharmacy spend PMPM | Allowed pharmacy amount divided by covered member months | 15 minutes |
| Data quality score | Weighted result of published validation rules across the three source domains | 5 minutes |

Each response should return `as_of`, source lineage, applied policy, metric version, and a correlation ID. Those fields make executive numbers explainable and auditable.

## Production acceptance gates

- Source-to-target counts and financial totals reconcile within approved tolerances.
- Duplicate and late-arriving events produce deterministic results under replay.
- Unauthorized fields, rows, small cohorts, and exports are denied in automated policy tests.
- Audit events exist for every query and cannot be altered by application administrators.
- Recovery point and recovery time objectives are demonstrated in a restore exercise.
- Privacy and security teams approve the risk analysis, data flows, BAAs, policies, and incident procedures.
