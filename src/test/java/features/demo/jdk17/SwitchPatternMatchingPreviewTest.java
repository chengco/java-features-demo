package features.demo.jdk17;

import org.junit.jupiter.api.Test;

import static features.demo.jdk17.SwitchPatternMatchingPreview.patternSwitchToSimplifyInstanceOf;
import static features.demo.jdk17.SwitchPatternMatchingPreview.patternSwitchToSupportNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


class SwitchPatternMatchingPreviewTest {

    @Test
    void test_patternSwitchToSimplifyInstanceOf() {
        assertEquals("int 10", patternSwitchToSimplifyInstanceOf(10));
        assertEquals("long 10", patternSwitchToSimplifyInstanceOf(10L));
    }


    @Test
    void test_patternSwitchToSupportNull() {
        assertEquals("null value", patternSwitchToSupportNull(null));
        assertEquals("TT", patternSwitchToSupportNull("T"));
        assertEquals("SS", patternSwitchToSupportNull("S"));
        assertEquals("D", patternSwitchToSupportNull("D"));
    }
}
