package the_thundercats.spyglassserverapi.domain.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserModelTest {
    User mockUser;

    @BeforeEach
    public void setUp(){
        mockUser = new User("Sabrina", "Krueger", "bwina@gmail.com");
    }

    @Test
    @DisplayName("constructor test - pass")
    public void userConstructorTest01(){
        String expected = "Sabrina Krueger bwina@gmail.com";
        String actual = mockUser.toString();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    @DisplayName("constructor test - fail")
    public void userConstructorTest02(){

    }

    @Test
    @DisplayName("getter test firstname - pass")
    public void userGetterTest01(){

    }

    @Test
    @DisplayName("getter test firstname - fail")
    public void userGetterTest02(){

    }

    @Test
    @DisplayName("getter test lastname - pass")
    public void userGetterTest03(){

    }

    @Test
    @DisplayName("getter test lastname - fail")
    public void userGetterTest04(){

    }

    @Test
    @DisplayName("getter test email - pass")
    public void userGetterTest05(){

    }

    @Test
    @DisplayName("getter test email - fail")
    public void userGetterTest06(){

    }

    @Test
    @DisplayName("setter test firstname - pass")
    public void userSetterTest01(){

    }

    @Test
    @DisplayName("setter test firstname - fail")
    public void userSetterTest02(){

    }

    @Test
    @DisplayName("setter test lastname - pass")
    public void userSetterTest03(){

    }

    @Test
    @DisplayName("setter test lastname - fail")
    public void userSetterTest04(){

    }

    @Test
    @DisplayName("setter test email - pass")
    public void userSetterTest05(){

    }

    @Test
    @DisplayName("setter test email - fail")
    public void userSetterTest06(){

    }
}
