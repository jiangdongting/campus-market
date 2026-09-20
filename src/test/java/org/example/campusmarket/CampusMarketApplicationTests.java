package org.example.campusmarket;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CampusMarketApplicationTests {

    @Test
    void testBCrypt() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String result = encoder.encode("123456");

        System.out.println(result);
    }
}