package checkvuz.checkvuz.university.program.service;

import checkvuz.checkvuz.university.program.entity.Program;
import checkvuz.checkvuz.university.program.entity.ProgramTag;
import org.springframework.hateoas.EntityModel;

import java.util.List;

public interface ProgramServiceInterface {

    List<Program> getPrograms();

    Program createProgram(Program programToCreate);

    Program getProgramById(Long programId);

    Program getProgramByTitle(String programTitle);

    Program updateProgram(Program programToUpdate, Long programId);

    void deleteProgram(Long programId);

    EntityModel<Program> convertProgramToModel(Program program);

    List<ProgramTag> getAssignedTags(Long programId);

    List<EntityModel<ProgramTag>> getAssignedTagModels(Long programId);

    Program assignTag(Long programId, Long programTagId);

    Program removeTag(Long programId, Long programTagId);
}
