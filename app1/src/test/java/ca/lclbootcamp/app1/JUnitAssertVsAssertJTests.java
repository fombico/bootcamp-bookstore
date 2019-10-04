package ca.lclbootcamp.app1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
public class JUnitAssertVsAssertJTests {

    @Test
    public void foo() {
        assertEquals(1,1);
        assertThat(1).isEqualTo(1);

        int[] array = new int[] { 1, 2, 3};
        assertArrayEquals(array, array);
        assertThat(array).isEqualTo(array)
                .hasSize(3)
                .contains(2)
                .doesNotContain(4);

        List<String> strings = Arrays.asList("Hello", "World");
        assertEquals(strings, strings);
        assertThat(strings).isEqualTo(strings)
                .hasSize(2)
                .contains("Hello")
                .doesNotContain("Goodbye");

        boolean isOctober = true;
        assertTrue(isOctober);
        assertThat(isOctober).isTrue();

        boolean isSeptember = false;
        assertFalse(isSeptember);
        assertThat(isSeptember).isFalse();

        String someNull = null;
        assertNull(someNull);
        assertThat(someNull).isNull();
        assertThat(someNull).isBlank();
        assertThat(someNull).isNullOrEmpty();

        String someText = "Hello";
        assertNotNull(someText);
        assertThat(someText).isNotNull();
        assertThat(someText).isNotBlank();
        assertThat(someText).isNotEmpty();
        assertThat(someText).hasSize(5);
    }
}
