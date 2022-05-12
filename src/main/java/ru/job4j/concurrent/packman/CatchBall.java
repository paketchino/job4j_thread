package ru.job4j.concurrent.packman;

import java.util.function.Predicate;

public class CatchBall {

     static class Title {

        String name;

        public Title(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Number {

         int n;
         public Integer getRecursive() {
             return n * n;
         }

         public Boolean checkNatNumber(Predicate<Integer> predicate, int n) {
             if (predicate.test(n)) {
                 if (n % 2 == 0) {
                     return true;
                 }
             }
             return false;
         }
    }

}
