package ru.job4j.concurrent.jcip.annotation;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import static org.hamcrest.core.Is.is;

class SingleLockListTest {

    @Test
    public void add() throws InterruptedException {
        List<Integer> listA = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(listA);
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        MatcherAssert.assertThat(rsl, is(Set.of(1, 2)));
    }

}