## JDK 17：switch可以翻身了吗？

JDK 17提供了switch模式匹配的预览版[Pattern Matching for switch (Preview)](https://openjdk.java.net/jeps/406)，JDK近来的版本也对switch有不少更新和新feature的引入, 网络上也有不少关于只使用if而从来不用switch的讨论，这些改变足以让switch翻身吗？Github: https://github.com/chengco/java-features-demo

#### switch开始支持表达式（从JDK 12）
Java 12引入了switch表达式, 使用switch表达式可以用于变量声明或return，使用->和yield来代替break实现退出分支
```java
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
```

#### 使用switch可以摆脱instanceOf cast模式了
对于一个Object需要向下转型的需求，通常的做法是 instanceof + cast：

```java
if (o instanceof String) {
    String s = (String)o;
    ... use s ...
}
```

switch的Pattern Matching允许case来类型匹配，不再需要cast

```java
static String patternSwitchToSimplifyInstanceOf(Object o) {
        return switch (o) {
            case Integer i -> String.format("int %d", i);
            case Long l    -> String.format("long %d", l);
            case Double d  -> String.format("double %f", d);
            case String s  -> String.format("String %s", s);
            default        -> o.toString();
        };
    }
```
#### switch支持null
之前的JDK版本，switch表达式不支持null，如果遇到null会抛出NullPointerException，所以通常需要先做null判断：
```java
static void testFooBar(String s) {
    if (s == null) {
        System.out.println("oops!");
        return;
    }
    switch (s) {
        case "Foo", "Bar" -> System.out.println("Great");
        default           -> System.out.println("Ok");
    }
}

```
switch的Pattern Matching允许case来判断null

```java
static String patternSwitchToSupportNull(String s) {
        return switch (s) {
            case null -> "null value";
            case "T"  -> "TT";
            case "S"  -> "SS";
            default   -> s;
        };
    }
```

#### switch支持对enum和sealed Classes完整性校验
通常，switch case类型匹配时，如果是引用类型比如Object或String，需要增加default分支来处理case不能覆盖的情况
```java
return switch (s) {
            case null -> "null value";
            case "T" -> "TT";
            case "S" -> "SS";
            default -> s;
        };
```
而对于enum或sealed Classes已约定范围，编译器会进行完整性校验，如果已覆盖所有可能取值，则不再需要default分支

```java
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
```

sealed Classes亦是如此

```java
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
```

不得不说，JDK近来的版本也对switch有不少更新和新feature的引入，网络上也有不少关于只使用if而从来不用switch的讨论，这些改变足以让switch翻身吗？

#### 参考
- https://openjdk.java.net/jeps/406
- https://docs.oracle.com/en/java/javase/13/language/switch-expressions.html