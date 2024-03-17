package checkvuz.checkvuz.university.program.service;

import checkvuz.checkvuz.university.program.assembler.ProgramTagModelAssembler;
import checkvuz.checkvuz.university.program.entity.ProgramTag;
import checkvuz.checkvuz.university.program.exception.ProgramTagNotFoundByIdException;
import checkvuz.checkvuz.university.program.repository.ProgramTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramTagService implements ProgramTagServiceInterface {

    private final ProgramTagModelAssembler programTagModelAssembler;
    private final ProgramTagRepository programTagRepository;

    @Override
    public List<ProgramTag> getProgramTags() {

        return programTagRepository.findAll();
    }

    @Override
    public ProgramTag createProgramTag(ProgramTag programTagToCreate) {

        return programTagRepository.save(programTagToCreate);
    }

    @Override
    public ProgramTag getProgramTag(Long programTagId) {

        return programTagRepository.findById(programTagId)
                .orElseThrow(() -> new ProgramTagNotFoundByIdException(programTagId));
    }

    @Override
    public ProgramTag updateProgramTag(ProgramTag programTagToUpdate, Long programTagId) {

        return programTagRepository.findById(programTagId)
                .map(programTag -> {
                    programTag.setId(programTagToUpdate.getId());
                    programTag.setTitle(programTagToUpdate.getTitle());
                    return programTagRepository.save(programTag);
                })
                .orElseGet(() -> {
                    programTagToUpdate.setId(programTagId);
                    return programTagRepository.save(programTagToUpdate);
                });
    }

    @Override
    public void deleteProgramTag(Long programTagId) {

        programTagRepository.deleteById(programTagId);
    }

    @Override
    public EntityModel<ProgramTag> convertProgramTagToModel(ProgramTag programTag) {

        return programTagModelAssembler.toModel(programTag);
    }
}
