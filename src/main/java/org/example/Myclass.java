package org.example;

public class Myclass {

    public void myMethod() {
        int counter = 0;
        int number = 3;

        System.out.println("myMethod is called!");

        if (number > 10) {
            System.out.println("Number is greater than 10");
            counter++;
        } else {
            System.out.println("Number is 10 or less");
            counter--;
        }

        for (int i = 0; i < 5; i++) {
            System.out.println("Loop iteration " + i);
            counter += i;
        }

        anotherMethod();

        System.out.println("Final counter value: " + counter);
    }

    public void anotherMethod() {
        System.out.println("This is another method.");
    }
}
