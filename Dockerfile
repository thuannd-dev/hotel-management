# -------- STAGE 1: BUILD --------
FROM tomcat:9.0-jdk8 AS builder

WORKDIR /app
COPY . .

# Cài ant
RUN apt-get update && apt-get install -y ant

# Build WAR, chỉ chạy target dist (bỏ javadoc)
RUN ant -noinput -buildfile build.xml dist


# -------- STAGE 2: RUNTIME --------
FROM tomcat:9.0-jdk8

# Xóa ROOT mặc định
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy WAR đã build từ stage 1
COPY --from=builder /app/dist/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]