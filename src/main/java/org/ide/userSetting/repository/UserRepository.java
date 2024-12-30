package org.ide.userSetting.repository;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.ide.userSetting.object.UserInfo;

import java.util.UUID;

public class UserRepository {
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public UserRepository() {
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
            String nickname = playerDoc.getString("name");
            boolean hud = playerDoc.getBoolean("hud", true);
            return new UserInfo(uuid, nickname, hud);
        }

        return null;
    }

    public void saveUserInfo(UserInfo userInfo) {
        MongoCollection<Document> collection = database.getCollection("UserData");
        Document document = new Document("uuid", userInfo.getUuid().toString())
                .append("name", userInfo.getName())
                .append("hud", userInfo.isHud());

        collection.insertOne(document);
    }

    public void updateUserInfo(UserInfo userInfo) {
        MongoCollection<Document> collection = database.getCollection("UserData");
        collection.updateOne(
                Filters.eq("uuid", userInfo.getUuid().toString()),
                Updates.combine(
                        Updates.set("name", userInfo.getName()),
                        Updates.set("hud", userInfo.isHud())
                ),
                new UpdateOptions().upsert(true)
        );
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}