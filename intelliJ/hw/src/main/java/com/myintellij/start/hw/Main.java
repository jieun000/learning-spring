package com.myintellij.start.hw;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static String getSomeString() {
        return null; // 이 메서드는 항상 null을 반환한다.
    }
    private static Optional<String> getSomeString2() {
        return Optional.empty(); // null을 반환하는 것이 아니라 비어있는 Optional을 반환한다.
    }
    private static Optional<String> getSomeString3() {
        return Optional.ofNullable("public static void");
    }
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        int number = 1;
        // 2-1
        if (number == 1) {
            System.out.println("if 블록입니다.");
        } else if (number == 2) {
            System.out.println("else if 블록입니다.");
        } else {
            System.out.println("else 블록입니다.");
        }
        // 2-2
        int[] array2 = {1, 2, 3, 4, 5};

        for (int i = 0; i < array2.length; i++) {
            System.out.printf("i = " + array2[i] + ", ");
        }
        System.out.println();
        // 2-3
        int[] array3 = {1, 2, 3, 4, 5};
        int i = 0;

        while (i < array3.length) {
            System.out.printf("i = "+ array3[i] + ", ");
            i++;
            if(i==3) break;
        }
        System.out.println();
        // 2-5
        List list = new ArrayList<Integer>();
        // <Integer>는 ArrayList에 Integer 타입이 저장될 수 있다는 것을 의미한다.
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list.get(1));

        // 2-6
        List list2 = new ArrayList<String>();
        list2.add("public");       // ["public"]
        list2.add("static");       // ["public", "static"]
        list2.add("void");         // ["public", "static", "void"]
        // for 문으로 List를 순회할 수 있다.
        for (i = 0; i < list2.size(); i++) { // list.size()는 리스트의 크기를 반환한다.
            System.out.println(list2.get(i));    // i번째 요소가 출력된다.
        }
        list2.remove(1);     // 1번째 요소인 "static"이 제거된다. -> ["public", "void"]
        int voidIndex = list2.indexOf("void");   // void의 인덱스인 1이 반환된다.
        System.out.println("void의 index = " + voidIndex);

        // 2-7
        String str1 = new String("is same?");
        String str2 = new String("is same?");

        System.out.println(str1 == str2); // true or false? false ==동일성 비교

        // 2-8
        String str3 = new String("is same?");
        String str4 = new String("is same?");

        System.out.println(str3.equals(str4)); // true or false? true equals()동등성 비교

        // 3-1
        List list3 = new ArrayList<String>();
        list3.add("public");
        list3.add("static");
        list3.add("void");
        // 익명 클래스 코드 (=람다 표현식 코드)
        list3.sort(new Comparator<String>() {
            @Override
            public int compare(String str5, String str6) {
                return str5.compareTo(str6);
            }
        });
        // 람다 표현식 코드 (=익명 클래스 코드)
        list3.sort((Comparator<String>) (str5, str6) -> str5.compareTo(str6));
        list3.sort((Comparator<String>) String::compareTo);

        // 3-2
        List list4 = new ArrayList<String>();
        list4.add("public");
        list4.add("static");
        list4.add("void");
        list4.stream().forEach(str -> System.out.println(str));

        // 3-3
        Integer[] integerArray = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        List<Integer> list5 = Arrays.asList(integerArray);
        List evenList = new ArrayList<Integer>();
        for (i = 0; i < list5.size(); i++) {
            Integer number2 = list5.get(i);
            if (number2 % 2 == 0) { // 2로 나눴을 때의 나머지가 0이면 2의 배수다.
                evenList.add(number2);
            }
        }
        for (i = 0; i < evenList.size(); i++) {
            System.out.println(evenList.get(i));
        }

        // 3-4
        Integer[] integerArray2 = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        List<Integer> list6 = Arrays.asList(integerArray2);

        List evenList2 = list6.stream()
                .filter(value -> value % 2 == 0).collect(Collectors.toList());

        evenList2.stream().forEach(value -> System.out.println(value));

        // 3-5
        Integer[] integerArray3 = new Integer[]{1, 1, 1, 1, 2, 2, 2, 3, 3, 4};
        List<Integer> list7 = Arrays.asList(integerArray3);
        List<Integer> distinctList = list7.stream().distinct().collect(Collectors.toList());
        distinctList.stream().forEach(value -> System.out.println(value));

        // 3-6
        String[] lowercaseArray = new String[]{"public", "static", "void"};
        List<String> lowercaseList = Arrays.asList(lowercaseArray);
        List<String> uppercaseList = lowercaseList.stream()
                .map(value -> value.toUpperCase()).collect(Collectors.toList());
        uppercaseList.stream().forEach(value -> System.out.println(value));

        // 4-1
        String isThisNull = getSomeString();

        if(null != isThisNull) {
            System.out.println(isThisNull.toUpperCase());
        } else {
            System.out.println("Null Point Exception 방지");
        }
        // 4-2
        Optional<String> isThisNull2 = getSomeString2();
        isThisNull2.ifPresent(str -> System.out.println(str.toUpperCase())); // Optional의 기능 ifPresent
        // 4-3
        Optional<String> isThisNull3 = getSomeString3();
        isThisNull3.ifPresent(str -> System.out.println(str.toUpperCase())); // PUBLIC STATIC VOID가 출력된다.
        // 4-4
        Optional<String> str = getSomeString3();
        if(str.isPresent()) {
            System.out.println(str.get().toUpperCase());
        }
        str.ifPresent((string) -> System.out.println(string.toUpperCase()));
    }
}