package checkvuz.checkvuz.university.faculty.service;

import checkvuz.checkvuz.university.faculty.assembler.FacultyTagModelAssembler;
import checkvuz.checkvuz.university.faculty.controller.FacultyTagController;
import checkvuz.checkvuz.university.faculty.entity.FacultyTag;
import checkvuz.checkvuz.university.faculty.exception.FacultyTagNotFoundException;
import checkvuz.checkvuz.university.faculty.repository.FacultyTagRepository;
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
public class FacultyTagService implements FacultyTagServiceInterface {

    private final FacultyTagModelAssembler facultyTagModelAssembler;
    private final FacultyTagRepository facultyTagRepository;

    @Override
    public CollectionModel<EntityModel<FacultyTag>> getFacultyTags() {

        List<EntityModel<FacultyTag>> facultyTags = facultyTagRepository.findAll().stream()
                .map(facultyTagModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(facultyTags,
                linkTo(methodOn(FacultyTagController.class).getFacultyTags()).withSelfRel());
    }

    @Override
    public ResponseEntity<?> createFacultyTag(FacultyTag facultyTagToCreate) {

        EntityModel<FacultyTag> entityModel =
                facultyTagModelAssembler.toModel(facultyTagRepository.save(facultyTagToCreate));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    public EntityModel<FacultyTag> getFacultyTag(Long facultyTagId) {

        FacultyTag facultyTag = facultyTagRepository
                .findById(facultyTagId).orElseThrow(() -> new FacultyTagNotFoundException(facultyTagId));

        return facultyTagModelAssembler.toModel(facultyTag);
    }

    @Override
    public ResponseEntity<?> updateFacultyTag(FacultyTag facultyTagToUpdate, Long facultyTagId) {

        FacultyTag updatedFacultyTag = facultyTagRepository.findById(facultyTagId)
                .map(facultyTag -> {
                    facultyTag.setId(facultyTagToUpdate.getId());
                    facultyTag.setTitle(facultyTagToUpdate.getTitle());
                    return facultyTagRepository.save(facultyTag);
                })
                .orElseGet(() -> {
                    facultyTagToUpdate.setId(facultyTagId);
                    return facultyTagRepository.save(facultyTagToUpdate);
                });

        EntityModel<FacultyTag> entityModel = facultyTagModelAssembler.toModel(updatedFacultyTag);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    public ResponseEntity<?> deleteFacultyTag(Long facultyTagId) {

        facultyTagRepository.deleteById(facultyTagId);

        return ResponseEntity.noContent().build();
    }
}
