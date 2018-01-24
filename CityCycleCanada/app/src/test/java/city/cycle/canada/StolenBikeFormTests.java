package city.cycle.canada;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by rousseln on 2017-11-26.
 */

public class StolenBikeFormTests {

    @Mock()
    StolenBikeForm sbf;
    @Before
    public void setup(){
        sbf = new StolenBikeForm();
    }

    @Test
    public void validPhoneNumber_isTrue() throws Exception {
        String validPhoneNumber = "306-999-3548";
        assertThat(sbf.validPhoneNumber(validPhoneNumber), is(true));
        System.out.println("Corectness Test #3");
        System.out.println("Input:" + validPhoneNumber);
    }

    @Test
    public void validPhoneNumber_isFalse() throws Exception {
        String invalidPhoneNumber = "";
        assertThat(sbf.validPhoneNumber(invalidPhoneNumber), is(false));
        System.out.println("Robustness Test #3");
        System.out.println("Input:" + invalidPhoneNumber);
    }

    @Test
    public void validStolenBikeDescription_isTrue() throws Exception{
        String validDescription = "A Description";
        assertThat(sbf.validDescription(validDescription), is(true));
        System.out.println("Corectness Test #4");
        System.out.println("Input:" + validDescription);
    }
    @Test
    public void validStolenBikeDescription_isFalse() throws Exception{
        String invalidDescription = "";
        assertThat(sbf.validDescription(invalidDescription), is(false));
        System.out.println("Robustness Test #4");
        System.out.println("Input:" + invalidDescription);
    }

    @Test
    public void validStolenBikeSerialNumber_IsTrue() throws Exception{
        String validSerialNumber = "123-446";
        assertThat(sbf.validSerialNumber("123-446"), is(true));
        System.out.println("Corectness Test #5");
        System.out.println("Input:" + validSerialNumber);
    }
    @Test
    public void validStolenBikeSerialNumber_IsFalse() throws Exception{
        String invalidSerialNuber = "";
        assertThat(sbf.validSerialNumber(invalidSerialNuber), is(false));
        System.out.println("Corectness Test #5");
        System.out.println("Input:" + invalidSerialNuber);
    }

}
