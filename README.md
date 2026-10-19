# bunq-example

Spring Boot (WebFlux) example that uses the [bunq SDK](https://github.com/flock-community/bunq-sdk) for Kotlin.
`GET /main` returns the public nickname of the bunq sandbox user.

## Configuration

The app reads a bunq **sandbox** API key from the `BUNQ_API_KEY` environment variable and fails to start without it.
Create a sandbox user and run the app:

```bash
export BUNQ_API_KEY=$(curl -s -X POST https://public-api.sandbox.bunq.com/v1/sandbox-user-person \
  -H 'Content-Type: application/json' -H 'User-Agent: bunq-example' -d '{}' | jq -r '.Response[0].ApiKey.api_key')
./gradlew bootRun
curl localhost:8080/main
```

Tests need the same variable (`./gradlew build`). CI creates a new sandbox user on every run.
