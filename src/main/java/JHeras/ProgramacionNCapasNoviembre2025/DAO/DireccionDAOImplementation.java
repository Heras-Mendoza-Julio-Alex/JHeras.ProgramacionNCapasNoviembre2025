package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.ML.Colonia;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Direccion;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Result;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Usuario;
import java.sql.Array;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOImplementation implements IDireccion {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    //Obtener datos de la direccion por id 
    @Override
    public Result getbyIDD(int idDireccion) {
        Result result = new Result();
        try {
            result.Correct = jdbcTemplate.execute("{CALL GETDIRECCIONBYID(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);

                callableStatement.setInt(2, idDireccion);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                while (resultSet.next()) {
                    Direccion direccion = new Direccion();

                    direccion.setIdDireccion(resultSet.getInt("iddireccion"));
                    direccion.setCalle(resultSet.getString("calle"));
                    direccion.setNumeroInterior(resultSet.getString("numerointerior"));
                    direccion.setNumeroExterior(resultSet.getString("numeroexterior"));
                    /*direccion.Usuario=new Usuario();
                    
                    direccion.Usuario.setIdUsuario(resultSet.getInt("idusuario_fk"));
                     */
                    result.object = direccion;
                }

                return true;
            });

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    //Obtener datos de la direccion por id según el id del usuario y de la direccion
    @Override
    public Result GetByIDUD(int idDireccion, int idUsario) {
        Result result = new Result();
        try {
            result.Correct = jdbcTemplate.execute("{CALL getDireccionByIDUsuarioDi(?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);

                callableStatement.setInt(2, idDireccion);

                callableStatement.setInt(3, idUsario);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                if (resultSet.next()) {
                    Usuario  usuario = new Usuario();
                    usuario.setIdUsuario(resultSet.getInt("idusuario_fk"));
                    usuario.Direcciones = new ArrayList<>();
                    usuario.Direcciones.add(new Direccion());
                    usuario.Direcciones.get(0).setIdDireccion(resultSet.getInt("iddireccion"));
                    usuario.Direcciones.get(0).setCalle(resultSet.getString("calle"));
                    usuario.Direcciones.get(0).setNumeroInterior(resultSet.getString("numerointerior"));
                    usuario.Direcciones.get(0).setNumeroExterior(resultSet.getString("numeroexterior"));
                    
                    
                    
//                    Direccion direccion = new Direccion();
//
//                    direccion.setIdDireccion(resultSet.getInt("iddireccion"));
//                    direccion.setCalle(resultSet.getString("calle"));
//                    direccion.setNumeroInterior(resultSet.getString("numerointerior"));
//                    direccion.setNumeroExterior(resultSet.getString("numeroexterior"));
//                    direccion.Usuario=new Usuario();
//                    
//                    direccion.Usuario.setIdUsuario(resultSet.getInt("idusuario_fk"));
//                     
                    result.object = usuario;
                }
                return true;

            });
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }
    
    
    

}
