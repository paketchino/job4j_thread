package ru.job4j.concurrent.packman;

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


    }

}
