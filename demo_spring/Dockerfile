# Sử dụng image chính thức của OpenJDK làm base image
FROM openjdk:17-jdk-slim

# Đặt thư mục làm việc trong container
WORKDIR /app

# Sao chép file JAR đã build vào trong container
COPY target/my-app.jar /app/my-app.jar

# Chạy ứng dụng Spring Boot khi container bắt đầu
CMD ["java", "-jar", "my-app.jar"]
