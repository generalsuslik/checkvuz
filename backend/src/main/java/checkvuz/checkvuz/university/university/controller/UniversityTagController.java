package checkvuz.checkvuz.university.university.controller;

import checkvuz.checkvuz.university.university.entity.UniversityTag;
import checkvuz.checkvuz.university.university.service.UniversityTagService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/university-tags")
@AllArgsConstructor
public class UniversityTagController {

    private final UniversityTagService universityTagService;

    @GetMapping("")
    public CollectionModel<EntityModel<UniversityTag>> getUniversityTags() {

        List<UniversityTag> universityTags = universityTagService.getUniversityTags();

        List<EntityModel<UniversityTag>> universityTagsModels = new ArrayList<>();
        for (UniversityTag tag : universityTags) {
            universityTagsModels.add(universityTagService.convertUniversityTagToModel(tag));
        }

        return CollectionModel.of(universityTagsModels,
                linkTo(methodOn(UniversityTagController.class).getUniversityTags()).withSelfRel());
    }

    @PostMapping("")
    public ResponseEntity<?> createUniversityTag(@RequestBody UniversityTag universityTag) {

        EntityModel<UniversityTag> entityModel = universityTagService.convertUniversityTagToModel(
                universityTagService.createUniversityTag(universityTag)
        );
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @GetMapping("/{universityTagId}")
    public EntityModel<UniversityTag> getUniversityTag(@PathVariable Long universityTagId) {

        return universityTagService.convertUniversityTagToModel(
                universityTagService.getUniversityTag(universityTagId)
        );
    }

    @PutMapping("/{universityTagId}")
    public ResponseEntity<?> updateUniversityTag(@RequestBody UniversityTag universityTagToUpdate,
                                                 @PathVariable Long universityTagId) {

        EntityModel<UniversityTag> entityModel = universityTagService.convertUniversityTagToModel(
                universityTagService.updateUniversityTag(universityTagToUpdate, universityTagId)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/{universityTagId}")
    public ResponseEntity<?> deleteUniversityTag(@PathVariable Long universityTagId) {

        universityTagService.deleteUniversityTag(universityTagId);
        return ResponseEntity.noContent().build();
    }
}
