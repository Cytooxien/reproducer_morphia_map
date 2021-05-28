package com.foo;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.util.*;

@Entity()
public class MyEntity {

    @Id
    private UUID id;
    private List<UUID> uuidList;
    private Map<UUID, String> uuidMap;
    private Map<String, Map<UUID, Integer>> map;

    public MyEntity(UUID id, List<UUID> uuidList, Map<UUID, String> uuidMap, Map<String, Map<UUID, Integer>> map) {
        this.id = id;
        this.uuidList = uuidList;
        this.uuidMap = uuidMap;
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

    public Map<UUID, String> getUuidMap() {
        return uuidMap;
    }

    public List<UUID> getUuidList() {
        return uuidList;
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
