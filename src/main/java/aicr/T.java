package com.peiowang.tools.aicr;

public class T {

   public static void test(Integer input) {
        switch (input) {
            case 1:
            case 2:
                prepareOneOrTwo();
            case 3:
                handleOneTwoOrThree();
                break;
            default:
                handleLargeNumber();
        }

    }

    private static void handleLargeNumber() {
        String largeNumber2 = "This is a large number2.";
        System.out.println("handleLargeNumber2: " + largeNumber2);
        String largeNumber = "This is a large number.";
        System.out.println("handleLargeNumber: " + largeNumber);
    }

    private static void handleOneTwoOrThree() {
        String largeNumber2 = "This is a large number2.";

        System.out.println("handleLargeNumber2 : " + largeNumber2);
        String largeNumber = "This is a large  number3.";
        System.out.println("handleOneTwoOrThree: " + largeNumber);

    }

    private static void prepareOneOrTwo() {
        
        String largeNumber2 = "This is a large number2.";
        System.out.println("handleLargeNumber2: " + largeNumber2);
        String largeNumber = "This is a large number.";
        System.out.println("prepareOneOrTwo: " + largeNumber);

    }
}
