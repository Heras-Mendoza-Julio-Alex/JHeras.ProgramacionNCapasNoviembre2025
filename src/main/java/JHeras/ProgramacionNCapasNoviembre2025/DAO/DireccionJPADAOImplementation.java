package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.ML.Result;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Direccion;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionJPADAOImplementation implements IDireccionJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result getById(int IdDireccion) {
        Result result = new Result();

        Direccion direccionBD = entityManager.find(Direccion.class, IdDireccion);

        ModelMapper modelMaperr = new ModelMapper();

        JHeras.ProgramacionNCapasNoviembre2025.ML.Direccion direccionML = modelMaperr.map(direccionBD, JHeras.ProgramacionNCapasNoviembre2025.ML.Direccion.class);

        result.object = direccionML;

        return result;
    }

}
