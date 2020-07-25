package org.zerock.secu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private PasswordEncoder pwEncoder;

	@Test
	public void testEncode(){
		String str ="1234";

		String enStr = pwEncoder.encode(str);

		System.out.println(enStr);
	}

	@Test
	void contextLoads() {
	}

}
