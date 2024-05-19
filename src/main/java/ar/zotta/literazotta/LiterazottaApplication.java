package ar.zotta.literazotta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ar.zotta.literazotta.main.Main;
import ar.zotta.literazotta.repository.LibraryRepository;

@SpringBootApplication
public class LiterazottaApplication implements CommandLineRunner {

	@Autowired
	private LibraryRepository libraryRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterazottaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(libraryRepository);
		main.main();
	}

}
