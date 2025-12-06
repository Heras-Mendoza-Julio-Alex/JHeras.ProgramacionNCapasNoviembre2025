package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.ML.Municipio;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOImplementation implements IMunicipio {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Override
    public Result getMunByID(int idEstado) {
        Result result = new Result();
        
        try {
        result.Correct = jdbcTemplate.execute("{CALL getMunicipioSP(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                
                callableStatement.setInt(2,idEstado);
                
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                result.Objects = new ArrayList<>();

                while (resultSet.next()) {
                    Municipio municipio=new Municipio();
                    municipio.setIdMunicipio(resultSet.getInt("idmunicipio"));
                    municipio.setNombre(resultSet.getString("nombre"));
                    
                    
                    result.Objects.add(municipio);    
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