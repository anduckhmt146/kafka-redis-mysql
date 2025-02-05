docker-up:
	docker-compose up -d

redis:
	docker exec -it redis-container redis-cli

lombok:
	mvn clean install

dev:
	mvn spring-boot:run