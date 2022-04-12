package ru.job4j.concurrent.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) != null;
    }

    public boolean update(Base model) {
        Base storage = memory.get(model.getId());
        if (storage.getVersion() != model.getVersion()) {
            throw new OptimisticException("Version are not equal");
        }
        return memory.
                computeIfPresent(
                        model.getId(), (now, update) -> new Base(now, update.getVersion() + 1)) != null;
    }

    public Integer getSizeMap() {
        return memory.size();
    }

    public Map<Integer, Base> getMemory() {
        return new ConcurrentHashMap<>(memory);
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }
}
