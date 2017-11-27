package city.cycle.canada;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by nicolas on 27/11/17.
 */

public class PostFormTests {

    PostForm pf;

    @Before
    public void setup(){
        pf= new PostForm();
    }

    @Test
    public void validPostTitle_isTrue() throws Exception{
        assertThat(pf.validPostTitle("A valid Post Title"), is(true));
    }

    @Test
    public void validPostTitle_isFalse() throws Exception{
        assertThat(pf.validPostTitle(""), is(false));
    }

    @Test
    public void validPostContent_isTrue() throws Exception{
        assertThat(pf.validPostContent("A valid Post Content"), is(true));
    }

    @Test
    public void validPostContent_isFalse() throws Exception{
        assertThat(pf.validPostTitle(""), is(false));
    }



}
