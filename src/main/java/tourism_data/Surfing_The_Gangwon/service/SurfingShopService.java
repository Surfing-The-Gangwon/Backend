package tourism_data.Surfing_The_Gangwon.service;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tourism_data.Surfing_The_Gangwon.dto.LessonDto;
import tourism_data.Surfing_The_Gangwon.dto.RentalDto;
import tourism_data.Surfing_The_Gangwon.dto.response.shop.SurfingShopInfoResponse;
import tourism_data.Surfing_The_Gangwon.dto.response.shop.SurfingShopInfoResponse.ShopImage;
import tourism_data.Surfing_The_Gangwon.entity.Marker;
import tourism_data.Surfing_The_Gangwon.entity.SurfingShop;
import tourism_data.Surfing_The_Gangwon.entity.SurfingShopPicture;
import tourism_data.Surfing_The_Gangwon.repository.LessonRepository;
import tourism_data.Surfing_The_Gangwon.repository.MarkerRepository;
import tourism_data.Surfing_The_Gangwon.repository.RentalRepository;
import tourism_data.Surfing_The_Gangwon.repository.SurfingShopRepository;
import tourism_data.Surfing_The_Gangwon.repository.SurfingShopPictureRepository;

@Service
public class SurfingShopService {
    private final SurfingShopRepository surfingShopRepository;
    private final SurfingShopPictureRepository surfingShopPictureRepository;
    private final LessonRepository lessonRepository;
    private final RentalRepository rentalRepository;
    private final ImageStorageService imageStorageService;
    private final MarkerRepository markerRepository;

    public SurfingShopService(SurfingShopRepository surfingShopRepository, SurfingShopPictureRepository surfingShopPictureRepository,
        LessonRepository lessonRepository, RentalRepository rentalRepository,
        ImageStorageService imageStorageService, MarkerRepository markerRepository) {
        this.surfingShopRepository = surfingShopRepository;
        this.surfingShopPictureRepository = surfingShopPictureRepository;
        this.lessonRepository = lessonRepository;
        this.rentalRepository = rentalRepository;
        this.imageStorageService = imageStorageService;
        this.markerRepository = markerRepository;
    }

    public SurfingShopInfoResponse getSurfingMarkerInfo(Long markerId) {
        Marker marker = markerRepository.findById(markerId)
            .orElseThrow(() -> new RuntimeException("MARKER IS NOT FOUND"));

        SurfingShop surfingShop = getSurfingShop(marker);

        List<ShopImage> imgUrlList = surfingShopPictureRepository.findByShopShopId(surfingShop.getShopId())
            .stream()
            .map(ShopImage::create)
            .toList();

        return SurfingShopInfoResponse.builder()
            .name(surfingShop.getName())
            .address(surfingShop.getAddress())
            .phone(surfingShop.getPhone())
            .introduce(surfingShop.getIntroduce())
            .shopImg(imgUrlList)
            .build();
    }

    /**
     * 서핑샵 사진 등록용
     */
    public void registerSurfingShopImg(Long shopId, List<MultipartFile> images) {
        SurfingShop surfingShop = getSurfingShop(shopId);

        if (images == null || images.isEmpty()) {
            images = Collections.emptyList();
        }

        for (MultipartFile img : images) {
            String imgUrl = imageStorageService.uploadImage(img);
            SurfingShopPicture picture = new SurfingShopPicture(imgUrl, surfingShop);
            surfingShopPictureRepository.save(picture);
        }
    }

    private SurfingShop getSurfingShop(Long shopId) {
        return surfingShopRepository.findByShopId(shopId)
            .orElseThrow(() -> new RuntimeException("SURFING SHOP IS NOT FOUND"));
    }

    private SurfingShop getSurfingShop(Marker marker) {
        return surfingShopRepository.findByMarker(marker)
            .orElseThrow(() -> new RuntimeException("SURFING SHOP IS NOT FOUND"));
    }

    public List<LessonDto> getLessonInfo(Long markerId) {
        Marker marker = markerRepository.findById(markerId)
            .orElseThrow(() -> new RuntimeException("MARKER IS NOT FOUND"));

        SurfingShop surfingShop = getSurfingShop(marker);

        return lessonRepository.findByShopShopId(surfingShop.getShopId())
            .stream()
            .map(LessonDto::create)
            .toList();
    }

    public List<RentalDto> getRentalInfo(Long markerId) {
        Marker marker = markerRepository.findById(markerId)
            .orElseThrow(() -> new RuntimeException("MARKER IS NOT FOUND"));
        SurfingShop surfingShop = getSurfingShop(marker);
        return rentalRepository.findByShopShopId(surfingShop.getShopId())
            .stream()
            .map(RentalDto::create)
            .toList();
    }
}
