package com.employwise.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

@Configuration
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration {
    @Value("${bucket.name}")
    private String bucketName;

    @Value("${bucket.password}")
    private String password;

    @Value("${database.host}")
    private String hosts;

    @Value("${bucket.user}")
    private String user;

    @Override
    public String getConnectionString() {
        return this.hosts;
    }

    @Override
    public String getUserName() {
        return this.user;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getBucketName() {
        return this.bucketName;
    }

}
