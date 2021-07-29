package com.Freecharge.snssqspoc.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author Aakash Sinha
 */
@Configuration
public class AWSSNSConfig {

  @Value("${ACCESS_KEY}")
  private String ACCESS_KEY;
  @Value("${SECRET_KEY}")
  private String SECRET_KEY;

  @Primary
  @Bean
  public AmazonSNSClient getSnsClient() {
    return (AmazonSNSClient) AmazonSNSClientBuilder.standard().withRegion(Regions.US_EAST_2)
        .withCredentials(new AWSStaticCredentialsProvider(
            new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)))
        .build();
  }
}
