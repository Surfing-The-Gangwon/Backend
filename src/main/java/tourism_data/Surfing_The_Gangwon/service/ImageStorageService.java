package tourism_data.Surfing_The_Gangwon.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tourism_data.Surfing_The_Gangwon.config.properties.AwsProperties;

@Service
@EnableConfigurationProperties(AwsProperties.class)
public class ImageStorageService {

    private final AmazonS3 amazonS3;
    private final AwsProperties awsProperties;

    public ImageStorageService(AmazonS3 amazonS3, AwsProperties awsProperties) {
        this.amazonS3 = amazonS3;
        this.awsProperties = awsProperties;
    }

    public String uploadImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new RuntimeException("not found image"); // ImageUploadException
        }
        return uploadFile(image);
    }

    private String uploadFile(MultipartFile file) {
        try {
            String key = UUID.randomUUID() + "_" + file.getOriginalFilename();
            ObjectMetadata metadata = createObjectMetadata(file);

            amazonS3.putObject(
                new PutObjectRequest(awsProperties.s3().bucket(), key, file.getInputStream(),
                    metadata));

            return String.format("https://%s.s3.%s.amazonaws.com/%s", awsProperties.s3().bucket(),
                awsProperties.region(), key);
        } catch (IOException e) {
            throw new RuntimeException("failed upload image"); // ImageUploadException
        }
    }

    private ObjectMetadata createObjectMetadata(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        String contentType = file.getContentType();

        if (contentType == null || contentType.isBlank()) {
            contentType = "multipart/form-data";
        }
        metadata.setContentType(contentType);
        metadata.setContentLength(file.getSize());
        return metadata;
    }

    public void deleteImage(String imageUrl) {
        Path filePath = Paths.get(imageUrl);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("failed deleting image"); // ImageDeleteException
        }
    }
}
