package com.foo;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Entity(value = "test")
public class MyEntity {

    @Id
    private UUID id;
    private Map<String, Map<UUID, Integer>> map = new HashMap<>();

    public MyEntity(UUID id, Map<String, Map<UUID, Integer>> map) {
        this.id = id;
        this.map = map;
    }

    public MyEntity() {
    }

    public UUID getId() {
        return id;
    }

    public Map<String, Map<UUID, Integer>> getMap() {
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyEntity entity = (MyEntity) o;
        return Objects.equals(id, entity.id) && Objects.equals(map, entity.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, map);
    }

    @Override
    public String toString() {
        return "MyEntity{" +
                "id=" + id +
                ", map=" + map +
                '}';
    }
}
