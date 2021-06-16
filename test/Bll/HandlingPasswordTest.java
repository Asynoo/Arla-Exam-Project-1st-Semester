package Bll;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandlingPasswordTest {

    @Test
    void returnCorrectHash() {

        /**
         * Testing if 2 identical input would yield the same hash, which stands in the basis of our project auth.
         * */

        String passToCheck1 = "testpassword";
        String passToCheck2 = "testpassword";

        HandlingPassword hp = new HandlingPassword();
        HandlingPassword hp1 = new HandlingPassword();

        String hash1 = hp.returnHash(passToCheck1);
        String hash2 = hp1.returnHash(passToCheck2);

        Assertions.assertEquals(hash1, hash2 );
    }

    @Test
    void returnWrongHash() {
        /**
         * Testing if 2 identical input would yield the same hash, with the difference that one will have space in front.
         * Resulting in not matching hashes.
         * */

        String passToCheck1 = " testpassword";
        String passToCheck2 = "testpassword";

        HandlingPassword hp = new HandlingPassword();
        HandlingPassword hp1 = new HandlingPassword();

        String hash1 = hp.returnHash(passToCheck1);
        String hash2 = hp1.returnHash(passToCheck2);

        Assertions.assertNotEquals(hash1,hash2);
    }

    @Test
    void returnWrongHash_CapitalLetter() {
        /**
         * Testing if 2 identical input would yield the same hash, with the difference that one will have first letter
         * of password capitalised and the other one not.
         * Resulting in not matching hashes.
         * */

        String passToCheck1 = " Testpassword";
        String passToCheck2 = "testpassword";

        HandlingPassword hp = new HandlingPassword();
        HandlingPassword hp1 = new HandlingPassword();

        String hash1 = hp.returnHash(passToCheck1);
        String hash2 = hp1.returnHash(passToCheck2);

        Assertions.assertNotEquals(hash1,hash2);
    }
}