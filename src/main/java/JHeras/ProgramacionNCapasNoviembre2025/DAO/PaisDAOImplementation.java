package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.ML.Pais;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaisDAOImplementation implements IPais {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result getAll() {
        Result result = new Result();

        try {
            result.Correct = jdbcTemplate.execute("{CALL getAllPais(?) }", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                result.Objects = new ArrayList<>();

                while (resultSet.next()) {
                    Pais pais = new Pais();
                    pais.setIdPais(resultSet.getInt("idpais"));
                    pais.setNombre(resultSet.getString("nombre"));

                    result.Objects.add(pais);

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
