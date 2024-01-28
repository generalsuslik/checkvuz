package checkvuz.checkvuz.university.service;

import checkvuz.checkvuz.university.assembler.UniversityTagModelAssembler;
import checkvuz.checkvuz.university.controller.UniversityTagController;
import checkvuz.checkvuz.university.entity.UniversityTag;
import checkvuz.checkvuz.university.exception.UniversityTagNotFoundException;
import checkvuz.checkvuz.university.repository.UniversityTagRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@AllArgsConstructor
public class UniversityTagService implements UniversityTagServiceInterface{

    private final UniversityTagModelAssembler universityTagModelAssembler;
    private final UniversityTagRepository universityTagRepository;

    @Override
    public CollectionModel<EntityModel<UniversityTag>> getUniversityTags() {

        List<EntityModel<UniversityTag>> tags = universityTagRepository.findAll().stream()
                .map(universityTagModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(tags,
                linkTo(methodOn(UniversityTagController.class).getUniversityTags()).withSelfRel());
    }

    @Override
    public ResponseEntity<?> createUniversityTag(UniversityTag universityTagToCreate) {

        EntityModel<UniversityTag> entityModel =
                universityTagModelAssembler.toModel(universityTagRepository.save(universityTagToCreate));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    public EntityModel<UniversityTag> getUniversityTag(Long universityTagId) {

        UniversityTag universityTag = universityTagRepository
                .findById(universityTagId).orElseThrow(() -> new UniversityTagNotFoundException(universityTagId));

        return universityTagModelAssembler.toModel(universityTag);
    }

    @Override
    public ResponseEntity<?> updateUniversityTag(UniversityTag universityTagToUpdate, Long universityTagId) {
        UniversityTag updatedUniversityTag = universityTagRepository.findById(universityTagId)
                .map(universityTag -> {
                    universityTag.setId(universityTagToUpdate.getId());
                    universityTag.setTitle(universityTagToUpdate.getTitle());
                    return universityTagRepository.save(universityTag);
                })
                .orElseGet(() -> {
                    universityTagToUpdate.setId(universityTagId);
                    return universityTagRepository.save(universityTagToUpdate);
                });

        EntityModel<UniversityTag> entityModel = universityTagModelAssembler.toModel(updatedUniversityTag);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    public ResponseEntity<?> deleteUniversityTag(Long universityTagId) {

        universityTagRepository.deleteById(universityTagId);

        return ResponseEntity.noContent().build();
    }
}
