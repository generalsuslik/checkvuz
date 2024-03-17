package checkvuz.checkvuz.university.program.service;

import checkvuz.checkvuz.university.program.assembler.ProgramModelAssembler;
import checkvuz.checkvuz.university.program.entity.Program;
import checkvuz.checkvuz.university.program.entity.ProgramTag;
import checkvuz.checkvuz.university.program.exception.ProgramNotFoundByIdException;
import checkvuz.checkvuz.university.program.exception.ProgramNotFoundByTitleException;
import checkvuz.checkvuz.university.program.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramService implements ProgramServiceInterface {

    private final ProgramModelAssembler programModelAssembler;
    private final ProgramRepository programRepository;

    private final ProgramTagService programTagService;

    @Override
    public List<Program> getPrograms() {

        return programRepository.findAll();
    }

    @Override
    public Program createProgram(Program programToCreate) {

        return programRepository.save(programToCreate);
    }

    @Override
    public Program getProgramById(Long programId) {

        return programRepository.findById(programId)
                .orElseThrow(() -> new ProgramNotFoundByIdException(programId));
    }

    @Override
    public Program getProgramByTitle(String programTitle) {

        return programRepository.getProgramByTitle(programTitle)
                .orElseThrow(() -> new ProgramNotFoundByTitleException(programTitle));
    }

    @Override
    public Program updateProgram(Program programToUpdate, Long programId) {

        return programRepository.findById(programId)
                .map(program -> {
                    program.setCode(programToUpdate.getCode());
                    program.setTitle(programToUpdate.getTitle());
                    program.setDescription(programToUpdate.getDescription());
                    program.setProgramTags(programToUpdate.getProgramTags());
                    return programRepository.save(program);
                })
                .orElseGet(() -> {
                    programToUpdate.setId(programId);
                    return programRepository.save(programToUpdate);
                });
    }

    @Override
    public void deleteProgram(Long programId) {

        programRepository.deleteById(programId);
    }

    @Override
    public EntityModel<Program> convertProgramToModel(Program program) {

        return programModelAssembler.toModel(program);
    }

    @Override
    public List<ProgramTag> getAssignedTags(Long programId) {

        Program program = getProgramById(programId);
        List<ProgramTag> tags = programTagService.getProgramTags();

        List<ProgramTag> programTags = new ArrayList<>();
        for (ProgramTag tag : tags) {
            if (program.getProgramTags().contains(tag)) {
                programTags.add(tag);
            }
        }

        return programTags;
    }

    @Override
    public List<EntityModel<ProgramTag>> getAssignedTagModels(Long programId) {

        return getAssignedTags(programId).stream()
                .map(programTagService::convertProgramTagToModel)
                .collect(Collectors.toList());
    }

    @Override
    public Program assignTag(Long programId, Long programTagId) {

        Program program = getProgramById(programId);
        ProgramTag programTag = programTagService.getProgramTag(programTagId);

        program.getProgramTags().add(programTag);
        return programRepository.save(program);
    }

    @Override
    public Program removeTag(Long programId, Long programTagId) {

        Program program = getProgramById(programId);
        ProgramTag programTag = programTagService.getProgramTag(programTagId);

        program.getProgramTags().remove(programTag);
        return programRepository.save(program);
    }
}
