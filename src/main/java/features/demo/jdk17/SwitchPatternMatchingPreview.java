package features.demo.jdk17;

public class SwitchPatternMatchingPreview {

    // support from Java 12
    static String switchSupportExpression(String s) {
        return switch (s) {
            case "1" -> "S1";
            case "2" -> "S2";
            case "3" -> {
                System.out.println("test");
                yield "S3";
            }
            default -> throw new IllegalStateException("Unexpected value: " + s);
        };
    }

    enum E {
        E1, E2, E3
    }

    static String switchWithEnum(E e) {
        return switch (e) {
            case E1 -> String.format("E %s", E.E1);
            case E2 -> String.format("E %s", E.E2);
            case E3 -> String.format("E %s", E.E3);
        };
    }

    sealed interface S permits A, B {
    }

    static final class A implements S {
    }

    static final class B implements S {
    }

    static String patternSwitchWithSealedClass(S s) {
        return switch (s) {
            case A a -> String.format("int %s", a);
            case B b -> String.format("long %s", b);
        };
    }

    static String patternSwitchToSimplifyInstanceOf(Object o) {
        return switch (o) {
            case Integer i -> String.format("int %d", i);
            case Long l -> String.format("long %d", l);
            case Double d -> String.format("double %f", d);
            case String s -> String.format("String %s", s);
            default -> o.toString();
        };
    }

    static String patternSwitchToSupportNull(String s) {
        return switch (s) {
            case null -> "null value";
            case "T" -> "TT";
            case "S" -> "SS";
            default -> s;
        };
    }
}
