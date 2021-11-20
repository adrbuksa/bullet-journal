package com.abuksa.server;

import com.abuksa.server.enumeration.EntryStatus;
import com.abuksa.server.enumeration.EntryType;
import com.abuksa.server.model.Entry;
import com.abuksa.server.repo.EntryRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(EntryRepo entryRepo) {
//		return args -> {
//			entryRepo.save(new Entry(null, "Note 1", EntryType.NOTE,
//					LocalDate.now(), EntryStatus.NOT_COMPLETED, Boolean.TRUE));
//			entryRepo.save(new Entry(null, "Note 2", EntryType.TASK,
//					LocalDate.of(2021, 11, 19), EntryStatus.NOT_COMPLETED, Boolean.FALSE));
//			entryRepo.save(new Entry(null, "Note 3", EntryType.EVEMT,
//					LocalDate.of(2021, 11, 18), EntryStatus.COMPLETED, Boolean.FALSE));
//		};
//	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
}
