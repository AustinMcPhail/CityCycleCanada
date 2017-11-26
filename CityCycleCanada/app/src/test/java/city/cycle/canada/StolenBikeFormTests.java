package city.cycle.canada;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by rousseln on 2017-11-26.
 */

public class StolenBikeFormTests {
    StolenBikeForm sbf;
    @Before
    public void setup(){
        sbf = new StolenBikeForm();
    }

    @Test
    public void validPictureMime_isTrue() throws Exception {
        assertThat(sbf.validPictureMime("image/png"), is(true));
    }

    @Test
    public void validPictureMime_isFalse() throws Exception {
        assertThat(sbf.validPictureMime("video/mp4"), is(false));
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
