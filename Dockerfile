FROM selenium/standalone-chromium:latest

USER root
WORKDIR /workspace

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        ca-certificates \
        maven \
    && rm -rf /var/lib/apt/lists/*

ENV CHROME_BINARY=/usr/bin/chromium \
    WEBDRIVER_CHROME_DRIVER=/usr/bin/chromedriver \
    MAVEN_OPTS="-Djava.awt.headless=true"

ENTRYPOINT []

COPY pom.xml .
RUN mvn -B -ntp dependency:go-offline

COPY src ./src
COPY README.md .

CMD ["mvn", "-B", "test", "-Pall"]
