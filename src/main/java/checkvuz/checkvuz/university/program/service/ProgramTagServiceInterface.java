package checkvuz.checkvuz.university.program.service;

import checkvuz.checkvuz.university.program.entity.ProgramTag;
import org.springframework.hateoas.EntityModel;

import java.util.List;

public interface ProgramTagServiceInterface {

    List<ProgramTag> getProgramTags();

    ProgramTag createProgramTag(ProgramTag programTagToCreate);

    ProgramTag getProgramTag(Long programTagId);

    ProgramTag updateProgramTag(ProgramTag programTagToUpdate, Long programTagId);

    void deleteProgramTag(Long programTagId);

    EntityModel<ProgramTag> convertProgramTagToModel(ProgramTag programTag);
}
