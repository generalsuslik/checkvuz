package checkvuz.checkvuz.faculty.service;

import checkvuz.checkvuz.faculty.assembler.FacultyTagModelAssembler;
import checkvuz.checkvuz.faculty.controller.FacultyTagController;
import checkvuz.checkvuz.faculty.entity.FacultyTag;
import checkvuz.checkvuz.faculty.exception.FacultyTagNotFoundException;
import checkvuz.checkvuz.faculty.repository.FacultyTagRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
    public ResponseEntity<?> createFaculty() {
        return null;
    }

    @Override
    public EntityModel<FacultyTag> getFacultyTag(Long facultyTagId) {

        FacultyTag facultyTag = facultyTagRepository
                .findById(facultyTagId).orElseThrow(() -> new FacultyTagNotFoundException(facultyTagId));

        return facultyTagModelAssembler.toModel(facultyTag);
    }

    @Override
    public ResponseEntity<?> updateFacultyTag() {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteFacultyTag() {
        return null;
    }
}
