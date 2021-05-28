package com.foo;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.util.UUID;

public class UuidAsStringCodec implements Codec<UUID> {
    static final UuidAsStringCodec INSTNACE = new UuidAsStringCodec();

    @Override
    public UUID decode(BsonReader reader, DecoderContext context) {
        return UUID.fromString(reader.readString());
    }

    @Override
    public void encode(BsonWriter writer, UUID uuid, EncoderContext context) {
        writer.writeString(uuid.toString());
    }

    @Override
    public Class<UUID> getEncoderClass() {
        return UUID.class;
    }
}
