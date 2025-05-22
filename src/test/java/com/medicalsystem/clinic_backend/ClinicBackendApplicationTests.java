package com.medicalsystem.clinic_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.security.user.name=test",
    "spring.security.user.password=test",
    "spring.security.user.roles=ADMIN"
})
class ClinicBackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
