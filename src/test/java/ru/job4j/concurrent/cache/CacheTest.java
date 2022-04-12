package ru.job4j.concurrent.cache;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CacheTest {

    @Test
    public void whenAddThen1Base() {
        Base base = new Base(1, 1.1, "Butcher");
        Cache cache = new Cache();
        cache.add(base);
        assertThat(cache.getSizeMap(), is(1));
    }

    @Test
    public void whenUpdate1BaseVersionThen1BaseVersionIncrement() {
        Base base = new Base(1, 1.1);
        Cache cache = new Cache();
        cache.add(base);
        cache.update(base);
        assertThat(cache.getMemory().get(1).getVersion(), is(2.1));
    }

    @Test(expected = OptimisticException.class)
    public void whenCompare2VersionInMemoryAndInNewBaseThenExecutionOptimisticException() {
        Base baseMemory = new Base(1, 1.1);
        Base newBase = new Base(1, 3.1);
        Cache cache = new Cache();
        cache.add(baseMemory);
        cache.update(newBase);

    }

    @Test
    public void whenUpdate2BaseVersionThenIncrement() {
        Base baseMemory = new Base(1, 1.1);
        Base newBase = new Base(2, 2.5);
        Cache cache = new Cache();
        cache.add(baseMemory);
        cache.add(newBase);
        cache.update(baseMemory);
        cache.update(newBase);
        assertThat(cache.getMemory().get(1).getVersion(), is(2.1));
        assertThat(cache.getMemory().get(2).getVersion(), is(3.5));
    }

    @org.junit.Test
    public void whenAdd1BaseThenDelete1Base() {
        Base base = new Base(1, 1.1, "Butcher");
        Cache cache = new Cache();
        cache.add(base);
        cache.delete(base);
        assertThat(cache.getSizeMap(), is(0));
    }
}