all: run

run:
	clear
	mvn clean
	mvn spring-boot:run

clean:
	clear
	mvn clean
	clear

test:
	clear
	mvn test