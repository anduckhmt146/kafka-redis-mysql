docker-up:
	docker-compose up -d

redis:
	docker exec -it redis-container redis-cli

clean:
	mvn clean install

dev:
	mvn spring-boot:run