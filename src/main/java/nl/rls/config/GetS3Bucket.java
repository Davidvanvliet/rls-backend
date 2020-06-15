package nl.rls.config;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetS3Bucket {

    public static String getConnectionStringFromS3Bucket() {
        String bucketName = System.getenv("S3_BUCKET_NAME");
        String key = "S3_BUCKET_KEY";
        S3Object fullObject;
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider())
                    .withRegion(Regions.EU_WEST_1)
                    .build();
            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fullObject.getObjectContent()));
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
