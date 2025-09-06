#!/usr/bin/env bash
set -euo pipefail

BASE_URL="${BASE_URL:-http://localhost:8080}"

echo "🔎 Health check..."
code=$(curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/actuator/health")
test "$code" = "200" && echo "✅ Health OK" || { echo "❌ Health failed ($code)"; exit 1; }

echo "🔎 Delivery methods..."
code=$(curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/api/delivery-methods")
test "$code" = "200" && echo "✅ API OK" || { echo "❌ API failed ($code)"; exit 1; }

echo "🎉 Smoke tests passed."
