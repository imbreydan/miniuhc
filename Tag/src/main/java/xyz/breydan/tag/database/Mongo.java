package xyz.breydan.tag.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Mongo {

    private MongoClient client;
    private MongoDatabase database;

    private static final CodecRegistry CODEC_REGISTRY = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    public static final FindOneAndReplaceOptions REPLACE_OPTIONS = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER).upsert(true);

    public Mongo(String uri, String db) {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(CODEC_REGISTRY)
                .build();
        this.client = MongoClients.create(settings);
        this.database = client.getDatabase(db);
    }

    public MongoClient getClient() {
        return client;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

}
