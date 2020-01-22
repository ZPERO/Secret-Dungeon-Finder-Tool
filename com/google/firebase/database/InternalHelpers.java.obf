package com.google.firebase.database;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.core.DatabaseConfig;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.Repo;
import com.google.firebase.database.core.RepoInfo;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.Node;

public class InternalHelpers
{
  public InternalHelpers() {}
  
  public static DataSnapshot createDataSnapshot(DatabaseReference paramDatabaseReference, IndexedNode paramIndexedNode)
  {
    return new DataSnapshot(paramDatabaseReference, paramIndexedNode);
  }
  
  public static FirebaseDatabase createDatabaseForTests(FirebaseApp paramFirebaseApp, RepoInfo paramRepoInfo, DatabaseConfig paramDatabaseConfig)
  {
    return FirebaseDatabase.createForTests(paramFirebaseApp, paramRepoInfo, paramDatabaseConfig);
  }
  
  public static MutableData createMutableData(Node paramNode)
  {
    return new MutableData(paramNode);
  }
  
  public static DatabaseReference createReference(Repo paramRepo, Path paramPath)
  {
    return new DatabaseReference(paramRepo, paramPath);
  }
}
