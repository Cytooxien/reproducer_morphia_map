package com.foo;

import com.antwerkz.bottlerocket.BottleRocket;
import com.antwerkz.bottlerocket.BottleRocketTest;
import com.antwerkz.bottlerocket.clusters.SingleNode;
import com.antwerkz.bottlerocket.configuration.blocks.SystemLog;
import com.antwerkz.bottlerocket.configuration.types.Verbosity;
import com.github.zafarkhaja.semver.Version;
import com.mongodb.MongoClientSettings;
import com.mongodb.assertions.Assertions;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class ReproducerTest extends BottleRocketTest {
    private Datastore datastore;

    public ReproducerTest() {
        SingleNode node = new SingleNode();
        node.clean();
        node.start();

        MongoClient mongo = node.getClient(MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(CodecRegistries.fromRegistries(CodecRegistries.fromCodecs(UuidAsStringCodec.INSTNACE),
                        MongoClientSettings.getDefaultCodecRegistry())));
        MongoDatabase database = getDatabase();
        database.drop();
        datastore = Morphia.createDatastore(mongo, getDatabase().getName());
    }

    @NotNull
    @Override
    public String databaseName() {
        return "morphia_repro";
    }

    @Nullable
    @Override
    public Version version() {
        return BottleRocket.DEFAULT_VERSION;
    }


    @Test
    public void reproduce() {
        MyEntity entity = createEntity();
        UUID id = entity.getId();

        datastore.save(entity);

        MyEntity entity1 = datastore.find(MyEntity.class).field("_id").equal(id).first();

        System.out.println("entity.equals(entity1) = " + Objects.equals(entity, entity1));

        for (UUID uuid : entity1.getUuidList()) {
            System.out.println(uuid.getClass());
        }

        entity1.getUuidMap().forEach((uuid, s) -> {
            System.out.println(uuid.getClass());
        });

        entity1.getMap().get("val").forEach((uuid, integer) -> {
            System.out.println(uuid.getClass());
        });

        Assert.assertEquals(entity, entity1);
    }

    private MyEntity createEntity() {
        UUID id = UUID.randomUUID();

        Map<UUID, Integer> entry = new HashMap<>();
        entry.put(UUID.randomUUID(), 100);

        Map<UUID, String> uuidMap = new HashMap<>();
        uuidMap.put(UUID.randomUUID(), "val2");

        Map<String, Map<UUID, Integer>> map = new HashMap<>();
        map.put("val", entry);

        List<UUID> uuidList = Collections.singletonList(UUID.randomUUID());

       return new MyEntity(id, uuidList, uuidMap, map);
    }
}
