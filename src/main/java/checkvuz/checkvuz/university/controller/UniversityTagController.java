package checkvuz.checkvuz.university.controller;

import checkvuz.checkvuz.university.entity.UniversityTag;
import checkvuz.checkvuz.university.service.UniversityTagService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/university-tags")
@AllArgsConstructor
public class UniversityTagController {

    private final UniversityTagService universityTagService;

    @GetMapping("")
    public CollectionModel<EntityModel<UniversityTag>> getUniversityTags() {
        return universityTagService.getUniversityTags();
    }

    @PostMapping("")
    public ResponseEntity<?> createUniversityTag(@RequestBody UniversityTag universityTag) {
        return universityTagService.createUniversityTag(universityTag);
    }

    @GetMapping("/{universityTagId}")
    public EntityModel<UniversityTag> getUniversityTag(@PathVariable Long universityTagId) {
        return universityTagService.getUniversityTag(universityTagId);
    }

    @PutMapping("/{universityTagId}")
    public ResponseEntity<?> updateUniversityTag(@RequestBody UniversityTag universityTagToUpdate,
                                                 @PathVariable Long universityTagId) {

        return universityTagService.updateUniversityTag(universityTagToUpdate, universityTagId);
    }
}
