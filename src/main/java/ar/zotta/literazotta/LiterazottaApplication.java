package ar.zotta.literazotta;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ar.zotta.literazotta.main.Main;

@SpringBootApplication
public class LiterazottaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiterazottaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main();
		main.main();
	}

}
