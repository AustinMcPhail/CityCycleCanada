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
        assertThat(sbf.validPhoneNumber("306-999-3548"), is(true));
    }

    @Test
    public void validPhoneNumber_isFalse() throws Exception {
        assertThat(sbf.validPhoneNumber(""), is(false));
    }

    @Test
    public void validStolenBikeDescription_isTrue() throws Exception{
        assertThat(sbf.validDescription("A Description"), is(true));
    }
    @Test
    public void validStolenBikeDescription_isFalse() throws Exception{
        assertThat(sbf.validDescription(""), is(false));
    }

    @Test
    public void validStolenBikeSerialNumber_IsTrue() throws Exception{
        assertThat(sbf.validSerialNumber("123-446"), is(true));
    }
    @Test
    public void validStolenBikeSerialNumber_IsFalse() throws Exception{
        assertThat(sbf.validSerialNumber(""), is(false));
    }

}
