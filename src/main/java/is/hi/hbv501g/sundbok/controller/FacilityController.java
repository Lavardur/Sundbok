package is.hi.hbv501g.sundbok.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import is.hi.hbv501g.sundbok.model.Facility;
import is.hi.hbv501g.sundbok.repository.FacilityRepository;
import is.hi.hbv501g.sundbok.service.FacilityService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/facilities")
public class FacilityController {

    private final FacilityService facilityService;
    private final FacilityRepository facilityRepository;

    public FacilityController(FacilityService facilityService, FacilityRepository facilityRepository) {
        this.facilityService = facilityService;
        this.facilityRepository = facilityRepository;
    }

    // GET /api/facilities/{id} - Get facility by ID
    @GetMapping("/{id}")
    public ResponseEntity<Facility> getFacilityById(@PathVariable Long id) {
        return facilityService.getFacilityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/facilities - Create new facility
    @PostMapping
    public ResponseEntity<Facility> createFacility(@RequestBody Facility facility) {
        Facility saved = facilityService.createFacility(facility);
        return ResponseEntity.status(201).body(saved);
    }

    // PUT /api/facilities/{id} - Update facility
    @PutMapping("/{id}")
    public ResponseEntity<Facility> updateFacility(@PathVariable Long id, @RequestBody Facility facility) {
        try {
            Facility updated = facilityService.updateFacility(id, facility);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/facilities/{id} - Delete facility
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacility(@PathVariable Long id) {
        facilityService.deleteFacility(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/facilities/count - Get facility count
    @GetMapping("/count")
    public ResponseEntity<Long> getFacilityCount() {
        return ResponseEntity.ok(facilityService.getFacilityCount());
    }

    // GET /api/facilities/exists/{id} - Check if facility exists
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> facilityExists(@PathVariable Long id) {
        return ResponseEntity.ok(facilityService.facilityExists(id));
    }

    @GetMapping
    public ResponseEntity<List<Facility>> list(
            @RequestParam Optional<Double> latitude,
            @RequestParam Optional<Double> longitude,
            @RequestParam(name = "radius") Optional<Double> radius,
            @RequestParam Optional<String> search
    ) {
        System.out.println("params -> lat=" + latitude + ", lon=" + longitude + ", radius=" + radius + ", search=" + search);

        if (search.isPresent() && !search.get().isBlank()) {
            return ResponseEntity.ok(facilityService.searchByName(search.get()));
        }
        if (latitude.isPresent() && longitude.isPresent() && radius.isPresent()) {
            return ResponseEntity.ok(facilityService.findNearby(latitude.get(), longitude.get(), radius.get()));
        }
        return ResponseEntity.ok((List<Facility>) facilityService.getAll());
    }


    // GET /pools/{id}/availability   -> returns just fjoldi
    @GetMapping("/{id}/availability")
    public ResponseEntity<?> getAvailability(@PathVariable Long id) {
        return facilityRepository.findById(id)
                .map(f -> ResponseEntity.ok(Map.of(
                        "poolId", f.getId(),
                        "name", f.getName(),
                        "fjoldi", f.getFjoldi(),
                        "updatedAt", f.getFjoldiUpdatedAt()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    // (Optional) POST /admin/refresh-fjoldi  -> manual refresh trigger
    @PostMapping("/admin/refresh-fjoldi")
    public ResponseEntity<?> refreshFjoldi() throws JsonProcessingException {
        facilityService.refreshFjoldi();
        return ResponseEntity.ok(Map.of("ok", true));
    }
    @GetMapping("/{facilityId}/schedule")
    public ResponseEntity<List<Facility.ScheduleRow>> getSchedule(@PathVariable Long facilityId){
        return ResponseEntity.ok(facilityService.getSchedule(facilityId));
    }

    @PutMapping("/{facilityId}/schedule")
    public ResponseEntity<List<Facility.ScheduleRow>> putSchedule(
            @PathVariable Long facilityId,
            @RequestBody List<Facility.ScheduleRow> rows
    ) {
        return ResponseEntity.ok(facilityService.replaceSchedule(facilityId, rows));
    }
    private byte[] toCompressedJpeg(MultipartFile file, int maxSize, float quality) throws IOException {
        BufferedImage img = ImageIO.read(file.getInputStream());
        if (img == null) {
            throw new IllegalArgumentException("Uploaded file is not an image");
        }

        int w = img.getWidth();
        int h = img.getHeight();
        int max = Math.max(w, h);

        // scale down if needed
        if (max > maxSize) {
            double scale = (double) maxSize / max;
            int newW = (int) (w * scale);
            int newH = (int) (h * scale);

            Image scaled = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
            BufferedImage resized = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = resized.createGraphics();
            g2.drawImage(scaled, 0, 0, null);
            g2.dispose();
            img = resized;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) {
            throw new IllegalStateException("No JPEG writer available");
        }
        ImageWriter writer = writers.next();
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(baos)) {
            writer.setOutput(ios);
            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(quality); // 0.0–1.0 (lower = smaller)
            }
            writer.write(null, new IIOImage(img, null, null), param);
        } finally {
            writer.dispose();
        }
        return baos.toByteArray();
    }
    // GET /api/facilities/{id}/images -> list available image slots
    @GetMapping("/{id}/images")
    public ResponseEntity<java.util.List<Integer>> listImages(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(facilityService.listFacilityImages(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/facilities/{facilityId}/images/{index} -> actual JPEG bytes
    @GetMapping("/{facilityId}/images/{index}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long facilityId,
                                           @PathVariable int index) {
        try {
            byte[] data = facilityService.getFacilityImage(facilityId, index);
            if (data == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                    .body(data);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/facilities/{facilityId}/images -> add new image, first free slot (admins only)
    @PostMapping(path = "/{facilityId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addImage(@PathVariable Long facilityId,
                                      @RequestParam("file") MultipartFile file) {
        try {
            byte[] compressed = toCompressedJpeg(file, 800, 0.4f);
            facilityService.addFacilityImage(facilityId, compressed);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (IOException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /api/facilities/{facilityId}/images/{index} -> replace specific image (admins only)
    @PutMapping(path = "/{facilityId}/images/{index}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> setImage(@PathVariable Long facilityId,
                                         @PathVariable int index,
                                         @RequestParam("file") MultipartFile file) {
        try {
            byte[] compressed = toCompressedJpeg(file, 800, 0.4f);
            facilityService.setFacilityImage(facilityId, index, compressed);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETE /api/facilities/{facilityId}/images/{index} (admins only)
    @DeleteMapping("/{facilityId}/images/{index}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long facilityId,
                                            @PathVariable int index) {
        try {
            facilityService.deleteFacilityImage(facilityId, index);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }





}
