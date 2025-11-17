package is.hi.hbv501g.sundbok.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import is.hi.hbv501g.sundbok.model.Facility;
import is.hi.hbv501g.sundbok.repository.FacilityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.*;

@Service
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final WebClient web = WebClient.create("https://rsconnect.reykjavik.is");

    @Transactional
    public void refreshFjoldi() throws JsonProcessingException {
        // Fetch raw JSON array
        String json = web.get().uri("/gestafjoldi_api/nuna")
                .retrieve().bodyToMono(String.class).block();

        var mapper = new ObjectMapper();
        ArrayNode arr = (ArrayNode) mapper.readTree(json);

        Map<String, Facility> byName = new HashMap<>();
        facilityRepository.findAll().forEach(f -> byName.put(norm(f.getName()), f));

        Instant now = Instant.now();

        for (JsonNode node : arr) {
            String apiName = norm(node.get("sundlaug").asText());
            int fjoldi = node.get("fjoldi").asInt();
            Facility f = byName.get(apiName);
            if (f == null) continue;           // name didn’t match; skip
            f.setFjoldi(fjoldi);
            f.setFjoldiUpdatedAt(now);         // optional
            facilityRepository.save(f);
        }
    }
    private static String norm(String s) {
        if (s == null) return "";
        return java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .trim();
    }

    public List<Facility> findNearby(double lat, double lng, double radius) {
        return facilityRepository.findWithinRadius(lat, lng, radius);
    }

    public List<Facility> searchByName(String q) {
        return facilityRepository.findByNameContainingIgnoreCase(q);
    }

    public List<Facility> getAll() {
        List<Facility> out = new java.util.ArrayList<>();
        facilityRepository.findAll().forEach(out::add);
        return out;
    }
    public FacilityService(FacilityRepository facilityRepository) {

        this.facilityRepository = facilityRepository;
    }

    // CREATE - Add new facility
    public Facility createFacility(Facility facility) {

        return facilityRepository.save(facility);
    }

    // READ - Get all facilities
    public Iterable<Facility> getAllFacilities() {

        return facilityRepository.findAll();
    }

    // READ - Get facility by ID
    public Optional<Facility> getFacilityById(Long id) {

        return facilityRepository.findById(id);
    }

    // UPDATE - Update facility
    public Facility updateFacility(Long id, Facility updatedFacility) {
        return facilityRepository.findById(id)
            .map(facility -> {
                facility.setName(updatedFacility.getName());
                facility.setAddress(updatedFacility.getAddress());
                return facilityRepository.save(facility);
            })
            .orElseThrow(() -> new RuntimeException("Facility not found with id: " + id));
    }

    // DELETE - Delete facility
    public void deleteFacility(Long id) {

        facilityRepository.deleteById(id);
    }

    // EXISTS
    public boolean facilityExists(Long id) {
        return facilityRepository.existsById(id);
    }

    // COUNT
    public long getFacilityCount() {
        return facilityRepository.count();
    }
    @Transactional
    public List<Facility.ScheduleRow> replaceSchedule(Long facilityId, List<Facility.ScheduleRow> rows){
        Facility fac = facilityRepository.findById(facilityId).orElseThrow();
        fac.setSchedule(rows);
        return fac.getSchedule();
    }

    public List<Facility.ScheduleRow> getSchedule(Long facilityId){
        return facilityRepository.findById(facilityId).orElseThrow().getSchedule();
    }
    @Transactional
    public void addFacilityImage(Long facilityId, byte[] data) {
        Facility f = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("Facility not found with id: " + facilityId));

        for (int i = 1; i <= 10; i++) {
            if (f.getImage(i) == null) {
                f.setImage(i, data);
                return;
            }
        }
        throw new IllegalStateException("Facility already has 10 images");
    }

    @Transactional
    public void setFacilityImage(Long facilityId, int index, byte[] data) {
        if (index < 1 || index > 10) {
            throw new IllegalArgumentException("Image index must be 1–10");
        }
        Facility f = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("Facility not found with id: " + facilityId));
        f.setImage(index, data);
    }

    @Transactional
    public void deleteFacilityImage(Long facilityId, int index) {
        if (index < 1 || index > 10) {
            throw new IllegalArgumentException("Image index must be 1–10");
        }
        Facility f = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("Facility not found with id: " + facilityId));
        f.setImage(index, null);
    }

    public byte[] getFacilityImage(Long facilityId, int index) {
        if (index < 1 || index > 10) {
            throw new IllegalArgumentException("Image index must be 1–10");
        }
        Facility f = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("Facility not found with id: " + facilityId));
        return f.getImage(index);
    }

    public java.util.List<Integer> listFacilityImages(Long facilityId) {
        Facility f = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("Facility not found with id: " + facilityId));
        java.util.List<Integer> out = new java.util.ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            if (f.getImage(i) != null) out.add(i);
        }
        return out;
    }


}
