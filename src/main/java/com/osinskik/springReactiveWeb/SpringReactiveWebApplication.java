package com.osinskik.springReactiveWeb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//Two comments for running mongo in docker (starting mongo + mongo-express), assuming that mongo and mongo-express containers exists
//docker run --link mongo:mongo -p 8081:8081 mongo-express
//docker run -d --name mongo -p 27017:27017 mongo

@SpringBootApplication
public class SpringReactiveWebApplication implements CommandLineRunner {

  @Value("${app.collectionName}")
  private String collectionName;

  @Value("${app.dbName}")
  private String dbName;

  @Autowired
  private MongoClient mongoClient;

  public static void main(String[] args) {
    SpringApplication.run(SpringReactiveWebApplication.class, args);
  }

  @Override
  public void run(String... args) {
    this.cleanCollectionAndSetAsCapped();
  }

  private void cleanCollectionAndSetAsCapped() {
    final MongoDatabase db = mongoClient.getDatabase(dbName);
    final MongoCollection<Document> collection = db.getCollection(collectionName);
    if (collection != null) {
      collection.drop();
    }
    final CreateCollectionOptions conf = new CreateCollectionOptions().sizeInBytes(20 * 1024 * 1024)
        .capped(true);
    db.createCollection(collectionName, conf);

  }

}
