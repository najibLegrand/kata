# You build it, you run it — Ops Guide

## Quick start (local)
```bash
make build
make up
make smoke
# Swagger:    http://localhost:8080/swagger-ui.html
# Metrics:    http://localhost:8080/actuator/prometheus
# H2 console: http://localhost:8080/h2-console
```

## Observability pack
```bash
# In another terminal (Prometheus + Grafana)
docker compose -f docker-compose.observability.yml up -d
# Grafana: http://localhost:3000 (admin/admin)
```

Add the Prometheus datasource (auto-provisioned). Import a JVM + Spring Boot dashboard (e.g., 4701, 159) or build your own.

## CI/CD
- Pushes & PRs run `mvn verify` on JDK 21.
- On pushes to `main`, container image is pushed to GHCR as `:latest` and `:${GITHUB_SHA}`.
- Set the container registry namespace by editing `REGISTRY` in the Makefile or using env vars in the workflow.

## OpenShift
```bash
# Login, select project, then:
oc apply -f k8s/openshift/deployment.yaml
oc apply -f k8s/openshift/service.yaml
oc apply -f k8s/openshift/route.yaml
oc get route delivery-scheduler -o wide
```

Probes are wired to Actuator readiness/liveness. Ensure `application-prod.properties` is packaged and `springdoc` is disabled in prod.

## SLIs/SLOs (suggestion)
- **Availability**: `http_server_requests_seconds_count{outcome="SUCCESS"}` vs total (SLO 99.9% monthly)
- **Latency**: 95th percentile of `http_server_requests_seconds` < 250ms
- **Errors**: `http_server_requests_seconds_count{outcome!="SUCCESS"}` budget < 0.1%

## Runbook (common ops)
- **Scale up/down**: `oc scale deploy/delivery-scheduler --replicas=N`
- **Rolling restart**: `oc rollout restart deploy/delivery-scheduler`
- **View logs**: `oc logs deploy/delivery-scheduler -f`
- **Port forward**: `oc port-forward deploy/delivery-scheduler 8080:8080`
- **Debug pod**: `oc rsh deploy/delivery-scheduler`

## Security hygiene
- Never commit real `.env` secrets; provide a `.env.example` instead.
- Rotate any leaked keys.
- Optionally add Trivy/Grype scan steps in CI.
