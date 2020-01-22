package com.google.firebase;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.android.common.internal.Objects;
import com.google.android.android.common.internal.Objects.ToStringHelper;
import com.google.android.android.common.internal.Preconditions;
import com.google.android.android.common.internal.StringResourceValueReader;
import com.google.android.android.common.util.Strings;

public final class FirebaseOptions
{
  private static final String API_KEY_RESOURCE_NAME = "google_api_key";
  private static final String APP_ID_RESOURCE_NAME = "google_app_id";
  private static final String DATABASE_URL_RESOURCE_NAME = "firebase_database_url";
  private static final String GA_TRACKING_ID_RESOURCE_NAME = "ga_trackingId";
  private static final String GCM_SENDER_ID_RESOURCE_NAME = "gcm_defaultSenderId";
  private static final String PROJECT_ID_RESOURCE_NAME = "project_id";
  private static final String STORAGE_BUCKET_RESOURCE_NAME = "google_storage_bucket";
  private final String apiKey;
  private final String applicationId;
  private final String databaseUrl;
  private final String gaTrackingId;
  private final String gcmSenderId;
  private final String projectId;
  private final String storageBucket;
  
  private FirebaseOptions(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    Preconditions.checkState(Strings.isEmptyOrWhitespace(paramString1) ^ true, "ApplicationId must be set.");
    applicationId = paramString1;
    apiKey = paramString2;
    databaseUrl = paramString3;
    gaTrackingId = paramString4;
    gcmSenderId = paramString5;
    storageBucket = paramString6;
    projectId = paramString7;
  }
  
  public static FirebaseOptions fromResource(Context paramContext)
  {
    paramContext = new StringResourceValueReader(paramContext);
    String str = paramContext.getString("google_app_id");
    if (TextUtils.isEmpty(str)) {
      return null;
    }
    return new FirebaseOptions(str, paramContext.getString("google_api_key"), paramContext.getString("firebase_database_url"), paramContext.getString("ga_trackingId"), paramContext.getString("gcm_defaultSenderId"), paramContext.getString("google_storage_bucket"), paramContext.getString("project_id"));
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof FirebaseOptions)) {
      return false;
    }
    paramObject = (FirebaseOptions)paramObject;
    return (Objects.equal(applicationId, applicationId)) && (Objects.equal(apiKey, apiKey)) && (Objects.equal(databaseUrl, databaseUrl)) && (Objects.equal(gaTrackingId, gaTrackingId)) && (Objects.equal(gcmSenderId, gcmSenderId)) && (Objects.equal(storageBucket, storageBucket)) && (Objects.equal(projectId, projectId));
  }
  
  public String getApiKey()
  {
    return apiKey;
  }
  
  public String getApplicationId()
  {
    return applicationId;
  }
  
  public String getDatabaseUrl()
  {
    return databaseUrl;
  }
  
  public String getGaTrackingId()
  {
    return gaTrackingId;
  }
  
  public String getGcmSenderId()
  {
    return gcmSenderId;
  }
  
  public String getProjectId()
  {
    return projectId;
  }
  
  public String getStorageBucket()
  {
    return storageBucket;
  }
  
  public int hashCode()
  {
    return Objects.hashCode(new Object[] { applicationId, apiKey, databaseUrl, gaTrackingId, gcmSenderId, storageBucket, projectId });
  }
  
  public String toString()
  {
    return Objects.toStringHelper(this).add("applicationId", applicationId).add("apiKey", apiKey).add("databaseUrl", databaseUrl).add("gcmSenderId", gcmSenderId).add("storageBucket", storageBucket).add("projectId", projectId).toString();
  }
  
  public static final class Builder
  {
    private String apiKey;
    private String applicationId;
    private String databaseUrl;
    private String gaTrackingId;
    private String gcmSenderId;
    private String projectId;
    private String storageBucket;
    
    public Builder() {}
    
    public Builder(FirebaseOptions paramFirebaseOptions)
    {
      applicationId = applicationId;
      apiKey = apiKey;
      databaseUrl = databaseUrl;
      gaTrackingId = gaTrackingId;
      gcmSenderId = gcmSenderId;
      storageBucket = storageBucket;
      projectId = projectId;
    }
    
    public FirebaseOptions build()
    {
      return new FirebaseOptions(applicationId, apiKey, databaseUrl, gaTrackingId, gcmSenderId, storageBucket, projectId, null);
    }
    
    public Builder setApiKey(String paramString)
    {
      apiKey = Preconditions.checkNotEmpty(paramString, "ApiKey must be set.");
      return this;
    }
    
    public Builder setApplicationId(String paramString)
    {
      applicationId = Preconditions.checkNotEmpty(paramString, "ApplicationId must be set.");
      return this;
    }
    
    public Builder setDatabaseUrl(String paramString)
    {
      databaseUrl = paramString;
      return this;
    }
    
    public Builder setGaTrackingId(String paramString)
    {
      gaTrackingId = paramString;
      return this;
    }
    
    public Builder setGcmSenderId(String paramString)
    {
      gcmSenderId = paramString;
      return this;
    }
    
    public Builder setProjectId(String paramString)
    {
      projectId = paramString;
      return this;
    }
    
    public Builder setStorageBucket(String paramString)
    {
      storageBucket = paramString;
      return this;
    }
  }
}
