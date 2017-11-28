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
        String validPostTitle = "A valid Post Title";
        assertThat(pf.validPostTitle(validPostTitle), is(true));
        System.out.println("Corectness Test #1");
        System.out.println("Input:" + validPostTitle);
    }

    @Test
    public void validPostTitle_isFalse() throws Exception{
        String invalidPostTitle = "";
        assertThat(pf.validPostTitle(invalidPostTitle), is(false));
        System.out.println("Robustness Test #1");
        System.out.println("Input:" + invalidPostTitle);
    }

    @Test
    public void validPostContent_isTrue() throws Exception{
        String validPostContent = "A valid Post Content";
        assertThat(pf.validPostContent(validPostContent), is(true));
        System.out.println("Corectness Test #2");
        System.out.println("Input:" + validPostContent);
    }

    @Test
    public void validPostContent_isFalse() throws Exception{
        String invalidPostContent = "";
        assertThat(pf.validPostTitle(invalidPostContent), is(false));
        System.out.println("Robustness Test #2");
        System.out.println("Input:" + invalidPostContent);
    }



}
