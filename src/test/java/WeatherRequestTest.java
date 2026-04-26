import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherRequestTest {

    @Test
    void buildRequestForKnownCity(){

        var request = new WeatherRequest("London");
        assertEquals("london", request.getCity());
        assertNotNull(request.buildURL());
        assertTrue(request.buildURL().contains("51.5085"));
    }


    @Test
    void throwsForUnknownCity(){
        assertThrows(WeatherException.class, ()-> new WeatherRequest("FakeCity"));
    }

    @Test
    void caseInsensitiveCity(){
        assertDoesNotThrow(()->new WeatherRequest("LONDON"));
    }
}
