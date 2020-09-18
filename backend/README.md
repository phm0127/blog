# blog

## Project Compiles
```
mvn clean install
```

### Build Docker Image for Deploy
```
docker build -t {your-docker-image-name} -f Dockerfile ./  # You may need root permission.
```

### Deploy for Production
```
docker run --name {your-container-name} -d -p 8080:8080 {your-docker-image-name}  # You may need root permission.
```
