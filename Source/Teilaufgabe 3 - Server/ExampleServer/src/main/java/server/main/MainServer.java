package server.main;

import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan(basePackages = {"server", "game", "rules"})
@Configuration
public class MainServer {

	private static final int DEFAULT_PORT = 18235;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MainServer.class);

		app.setDefaultProperties(Collections.singletonMap("server.port", DEFAULT_PORT));
		app.run(args);
	}
}

/*
Starten Sie zuerst Ihren Server und führen anschließend den Testclient aus mittels:

java -jar Server_Evaluation_Client.jar

oder

java -jar Server_Evaluation_Client.jar --port 12345 (hierbei würde Port 12345 als Serverport angenommen)

oder

java -jar Server_Evaluation_Client.jar --testIDs 13 3 7 (hierbei würde nur Test 13, Test 3 und Test 7 ausgeführt)

oder

java -jar Server_Evaluation_Client.jar --includeMove true (hierbei würden auch die Tests für den Move-Endpoint ausgeführt, nur für Bonuspunkte relevant)

oder

java -jar Server_Evaluation_Client.jar --color true (hierbei würde versucht Ergebnisse farblich hervorzuheben)

Die Startparameter können bei Bedarf auch kombiniert werden, z.B.: java -jar Server_Evaluation_Client.jar --includeMove true --color true
*/