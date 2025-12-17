package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Colonia;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Result;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Direccion;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Transactional
    @Override
    public Result edit(JHeras.ProgramacionNCapasNoviembre2025.ML.Direccion direccionML, int IdUsuario) {
        Result result = new Result();

        try {

            Direccion direccionBD = entityManager.find(Direccion.class, direccionML.getIdDireccion());

            if (direccionBD != null) {
                ModelMapper modelMapper = new ModelMapper();              

                Direccion direccionJPA = modelMapper.map(direccionML, Direccion.class);

                JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario usuario = new JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario();
                usuario.setIdUsuario(IdUsuario);
                direccionJPA.setUsuario(usuario);
                
                System.out.println("");
                
                //actualizas direccionjpa
                entityManager.merge(direccionJPA);
            }

            result.Correct = true;

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }

    @Override
    public Result add(JHeras.ProgramacionNCapasNoviembre2025.ML.Direccion direccionML, int IdUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
