package org.ide.userSetting.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.ide.userSetting.object.UserInfo;

import java.util.UUID;

public class MongoDBRepository {
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public MongoDBRepository() {
        String atlasString = "mongodb://localhost:27017";
        ConnectionString connectionString = new ConnectionString(atlasString);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        this.mongoClient = MongoClients.create(settings);
        this.database = this.mongoClient.getDatabase("UserSetting");
    }

    public UserInfo getPlayerInfo(UUID uuid) {
        MongoCollection<Document> collection = database.getCollection("UserData");
        Document playerDoc = collection.find(new Document("uuid", uuid.toString())).first();

        if (playerDoc != null) {
            String nickname = playerDoc.getString("nickname");
            return new UserInfo(uuid, nickname);
        }

        return null;
    }

    public void insertPlayerInfo(UserInfo userInfo) {
        MongoCollection<Document> collection = database.getCollection("UserData");
        Document newPlayer = new Document("uuid", userInfo.getUuid().toString())
                .append("nickname", userInfo.getName());
        collection.insertOne(newPlayer);
    }

    public void updateNickname(UUID uuid, String newNickname) {
        MongoCollection<Document> collection = database.getCollection("UserData");
        collection.updateOne(
                new Document("uuid", uuid.toString()),
                new Document("$set", new Document("nickname", newNickname)),
                new UpdateOptions().upsert(false)
        );
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}