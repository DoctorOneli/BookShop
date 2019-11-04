package org.freedom.boot;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcTest {

	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode("170729");
	    System.out.println(password);
 

	}

}
