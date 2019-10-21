# Lightning Docker Apps

## How to run locally

### Without Docker container

```bash
export SLACK_SIGNING_SECRET={abcabcabcabcabcbabc}
export SLACK_BOT_TOKEN={xoxb-1234123412-123412341212-abcabcabc}
./gradlew clean run
# runs at localhost:8080
```

### Docker build

```bash
docker build -t your-repo/hello-lightning .
docker run -p 8080:8080 -it your-repo/hello-lightning \
  -e \
  SLACK_SIGNING_SECRET=$SLACK_SIGNING_SECRET \
  SLACK_BOT_TOKEN=$SLACK_BOT_TOKEN
# runs at localhost:8080
```

## AWS ECS

```bash
$(aws ecr get-login --no-include-email --region ap-northeast-1) # set your region

docker build -t hello-lightning .
docker tag hello-lightning:latest {ecr-domain}.amazonaws.com/hello-lightning:latest
docker push {ecr-domain}.amazonaws.com/hello-lightning:latest
```

Plus, you need to create a valid Task Definition.

* Fargate
* Container Definitions
  * Image - {ecr-domain}.amazonaws.com/hello-lightning:latest
* Environment Variables
  * SLACK_BOT_TOKEN - (your value starting `xoxb-***-***`)
  * SLACK_PORT - 80 (or port mapping with 8080)
  * SLACK_SIGNING_SECRET - (your value)

## Google Cloud Run

```bash
export GCLOUD_PROJECT_ID={something-great-12345}
export SLACK_SIGNING_SECRET={abcabcabcabcabcbabc}
export SLACK_BOT_TOKEN={xoxb-1234123412-123412341212-abcabcabc}

# Build and upload a Docker image
gcloud config set project ${GCLOUD_PROJECT_ID}

gcloud builds submit --tag gcr.io/${GCLOUD_PROJECT_ID}/hello-lightning

# Deploy the image to Cloud Run
gcloud config set run/region asia-northeast1 # set your region here
gcloud beta run deploy hello-lightning \
  --image gcr.io/${GCLOUD_PROJECT_ID}/hello-lightning \
  --platform managed \
  --allow-unauthenticated \
  --update-env-vars \
SLACK_SIGNING_SECRET=${SLACK_SIGNING_SECRET},\
SLACK_BOT_TOKEN=${SLACK_BOT_TOKEN}
```