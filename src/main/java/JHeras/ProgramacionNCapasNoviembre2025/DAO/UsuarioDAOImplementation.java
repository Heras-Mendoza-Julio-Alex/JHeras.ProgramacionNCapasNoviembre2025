package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.ML.Direccion;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Result;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Usuario;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Colonia;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Rol;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAOImplementation implements IUsuario {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Obtener los usuarios con sus direcciones.
    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            result.Correct = jdbcTemplate.execute("{CALL USUARIODIRECCIONALL(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                result.Objects = new ArrayList<>();

                while (resultSet.next()) {

                    int idUsuarioIngresar = resultSet.getInt("idusuario");

                    if (!result.Objects.isEmpty() && ((Usuario) result.Objects.get(result.Objects.size() - 1)).getIdUsuario() == idUsuarioIngresar) {
                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("calle"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("idColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));

                        Usuario usuario = ((Usuario) result.Objects.get(result.Objects.size() - 1));
                        usuario.Direcciones.add(direccion);

                    } else {

                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(idUsuarioIngresar);
                        usuario.setNombre(resultSet.getString("NombreUsuario"));

                        usuario.setApellidoPaterno(resultSet.getString("apellidopaterno"));
                        usuario.setApellidoMaterno(resultSet.getString("apellidomaterno"));
                        usuario.setTelefono(resultSet.getString("telefono"));
                        usuario.setFechanacimiento(resultSet.getDate("fechanacimiento"));
                        usuario.setEmail(resultSet.getString("email"));
                        usuario.setSexo(resultSet.getString("sexo"));
                        usuario.setCelular(resultSet.getString("celular"));
                        usuario.setCurp(resultSet.getString("curp"));
                        usuario.setPassword(resultSet.getString("password"));
                        usuario.setUsername(resultSet.getString("username"));
                        int IdDireccion = resultSet.getInt("IdDireccion");

                        if (IdDireccion != 0) {

                            usuario.Direcciones = new ArrayList<>();

                            Direccion direccion = new Direccion();
                            direccion.setCalle(resultSet.getString("calle"));
                            direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                            direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));

                            direccion.Colonia = new Colonia();
                            direccion.Colonia.setIdColonia(resultSet.getInt("idColonia"));
                            direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));

                            usuario.Direcciones.add(direccion);
                        }

                        result.Objects.add(usuario);

                    }
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

    //Añadir Usuario
    @Override
    public Result Add(Usuario usuario) {

        Result result = new Result();
        try {
            result.Correct = jdbcTemplate.execute("{CALL ADDUSUARIOCDIRECCION(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                callableStatement.setString(4, usuario.getTelefono());

                java.sql.Date sqlDate = new java.sql.Date(usuario.getFechanacimiento().getTime());

                callableStatement.setDate(5, sqlDate);

                callableStatement.setString(6, usuario.getUsername());
                callableStatement.setString(7, usuario.getEmail());
                callableStatement.setString(8, usuario.getSexo());
                callableStatement.setString(9, usuario.getCelular());
                callableStatement.setString(10, usuario.getCurp());
                callableStatement.setString(11, usuario.getPassword());
                callableStatement.setInt(12, usuario.Rol.getIdRol());
                callableStatement.setString(13, usuario.Direcciones.get(0).getCalle());
                callableStatement.setString(14, usuario.Direcciones.get(0).getNumeroInterior());
                callableStatement.setString(15, usuario.Direcciones.get(0).getNumeroExterior());
                callableStatement.setInt(16, usuario.Direcciones.get(0).Colonia.getIdColonia());

                callableStatement.executeUpdate();
                return true;

            });
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    //Obtener datos de las direcciones de un usuario
    @Override
    public Result getById(int IdUsuario) {
        Result result = new Result();
        try {
            result.Correct = jdbcTemplate.execute("{CALL UsuarioDireccionbyid(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);

                callableStatement.setInt(2, IdUsuario);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                result.Objects = new ArrayList<>();

                while (resultSet.next()) {

                    int idUsuarioIngresar = resultSet.getInt("idusuario");

                    if (!result.Objects.isEmpty() && ((Usuario) result.Objects.get(result.Objects.size() - 1)).getIdUsuario() == idUsuarioIngresar) {
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("idDireccion"));
                        direccion.setCalle(resultSet.getString("calle"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("idColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));

                        Usuario usuario = ((Usuario) result.Objects.get(result.Objects.size() - 1));
                        usuario.Direcciones.add(direccion);

                    } else {

                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(idUsuarioIngresar);
                        usuario.setNombre(resultSet.getString("NombreUsuario"));

                        usuario.setApellidoPaterno(resultSet.getString("apellidopaterno"));
                        usuario.setApellidoMaterno(resultSet.getString("apellidomaterno"));
                        usuario.setTelefono(resultSet.getString("telefono"));
                        usuario.setFechanacimiento(resultSet.getDate("fechanacimiento"));
                        usuario.setEmail(resultSet.getString("email"));
                        usuario.setSexo(resultSet.getString("sexo"));
                        usuario.setCelular(resultSet.getString("celular"));
                        usuario.setCurp(resultSet.getString("curp"));
                        usuario.setPassword(resultSet.getString("password"));
                        usuario.setUsername(resultSet.getString("username"));
                        int IdDireccion = resultSet.getInt("idDireccion");

                        if (IdDireccion != 0) {

                            usuario.Direcciones = new ArrayList<>();

                            Direccion direccion = new Direccion();
                            direccion.setIdDireccion(resultSet.getInt("idDireccion"));
                            direccion.setCalle(resultSet.getString("calle"));
                            direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                            direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));

                            direccion.Colonia = new Colonia();
                            direccion.Colonia.setIdColonia(resultSet.getInt("idColonia"));
                            direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));

                            usuario.Direcciones.add(direccion);
                        }

                        result.Objects.add(usuario);

                    }
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

    //Obtener datos de un usuario
    @Override
    public Result GetByIDU(int IdUsuario) {
        Result result = new Result();
        try {
            result.Correct = jdbcTemplate.execute("{CALL GETUSUARIOBYID(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, IdUsuario);
                callableStatement.execute();

                ResultSet resultset = (ResultSet) callableStatement.getObject(1);

                while (resultset.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultset.getInt("idusuario"));
                    usuario.setNombre(resultset.getString("nombre"));

                    usuario.setApellidoPaterno(resultset.getString("apellidopaterno"));
                    usuario.setApellidoMaterno(resultset.getString("apellidomaterno"));
                    usuario.setTelefono(resultset.getString("telefono"));
                    usuario.setFechanacimiento(resultset.getDate("fechanacimiento"));
                    usuario.setEmail(resultset.getString("email"));
                    usuario.setSexo(resultset.getString("sexo"));
                    usuario.setCelular(resultset.getString("celular"));
                    usuario.setCurp(resultset.getString("curp"));
                    usuario.setPassword(resultset.getString("password"));
                    usuario.setUsername(resultset.getString("username"));

                    usuario.Rol = new Rol();
                    usuario.Rol.setIdRol(resultset.getInt("idrol"));
                    usuario.Rol.setNombre(resultset.getString("nombrerol"));

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

    //Añadir Usuario Carga Masiva
    @Override
    public Result AddCargaMasiva(List<Usuario> usuarios) {

        Result result = new Result();

        try {
            jdbcTemplate.batchUpdate("{Call CrearUsuario(?,?,?,?,?,?,?,?,?,?,?,?)}", usuarios, usuarios.size(), (CallableStatement, usuario) -> {

                CallableStatement.setString(1, usuario.getNombre());
                CallableStatement.setString(2, usuario.getApellidoPaterno());
                CallableStatement.setString(3, usuario.getApellidoMaterno());
                CallableStatement.setString(4, usuario.getTelefono());

                //java.sql.Date sqlDate = new java.sql.Date(usuario.getFechanacimiento().getTime());
                java.util.Date Date = usuario.getFechanacimiento();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  // Cambiar el formato
                String stringdate = dateFormat.format(Date);
                java.sql.Date sqlDate = java.sql.Date.valueOf(stringdate);
                CallableStatement.setDate(5, sqlDate);

                //CallableStatement.setDate(5, sqlDate);
                CallableStatement.setString(6, usuario.getUsername());
                CallableStatement.setString(7, usuario.getEmail());
                CallableStatement.setString(8, usuario.getSexo());
                CallableStatement.setString(9, usuario.getCelular());
                CallableStatement.setString(10, usuario.getCurp());
                //usuario.Rol = new Rol();

                CallableStatement.setString(11, usuario.getPassword());
                CallableStatement.setInt(12, usuario.Rol.getIdRol());
                
            });
            result.Correct=true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;

            System.out.println("Error " + ex);

        }

        return result;
    }

    @Override
    public Result DeleteById(int IdUsuario) {
        Result result = new Result();

        try {
            result.Correct = jdbcTemplate.execute("{CALL deleteUsuario(?)}", (CallableStatementCallback<Boolean>) callableStatementCallback -> {

                callableStatementCallback.setInt(1, IdUsuario);
                callableStatementCallback.execute();
                return true;
            });

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            System.out.println("Error " + ex);
        }

        return result;
    }

    @Override
    public Result UpdateUsuarioid(int IdUsuario) {
        Result result = new Result();

        try {
            result.Correct = jdbcTemplate.execute("{CALL UPDATEUSUARIOBYID(?,?,?,?,?,?,?,?,?,?,?,?,?}", (CallableStatementCallback<Boolean>) callableStatementCallback -> {
                callableStatementCallback.setInt(1, IdUsuario);
                Usuario usuario = new Usuario();
                callableStatementCallback.setString(2, usuario.getNombre());
                callableStatementCallback.setString(3, usuario.getApellidoPaterno());
                callableStatementCallback.setString(4, usuario.getApellidoMaterno());
                callableStatementCallback.setString(5, usuario.getTelefono());
                java.util.Date Date = usuario.getFechanacimiento();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String stringdate = dateFormat.format(Date);
                java.sql.Date sqlDate = java.sql.Date.valueOf(stringdate);
                callableStatementCallback.setDate(6, sqlDate);
                callableStatementCallback.setString(7, usuario.getUsername());
                callableStatementCallback.setString(8, usuario.getEmail());
                callableStatementCallback.setString(9, usuario.getSexo());
                callableStatementCallback.setString(10, usuario.getCelular());
                callableStatementCallback.setString(11, usuario.getCurp());
                callableStatementCallback.setString(12, usuario.getPassword());
                callableStatementCallback.setInt(13, usuario.Rol.getIdRol());

                return true;
            });

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result BusquedaUsuarioDireccionAll(Usuario usuario) {
        Result result = new Result();
        try {
            result.Correct = jdbcTemplate.execute("{Call BusquedaUsuarioDireccionAll(?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoMaterno());
                callableStatement.setString(3, usuario.getApellidoPaterno());
                
                callableStatement.setInt(4,usuario.Rol.getIdRol());
                
                callableStatement.registerOutParameter(5, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultset = (ResultSet) callableStatement.getObject(5);

                result.Objects=new ArrayList<>();
                
                while (resultset.next()) {

                    int idUsuarioIngresar = resultset.getInt("idusuario");

                    if (!result.Objects.isEmpty() && ((Usuario) result.Objects.get(result.Objects.size() - 1)).getIdUsuario() == idUsuarioIngresar) {
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultset.getInt("idDireccion"));
                        direccion.setCalle(resultset.getString("calle"));
                        direccion.setNumeroExterior(resultset.getString("NumeroExterior"));
                        direccion.setNumeroInterior(resultset.getString("NumeroInterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultset.getInt("idColonia"));
                        direccion.Colonia.setNombre(resultset.getString("NombreColonia"));

                        Usuario usuarioB = ((Usuario) result.Objects.get(result.Objects.size() - 1));
                        usuario.Direcciones.add(direccion);

                    } else {

                        Usuario usuarioB = new Usuario();
                        usuario.setIdUsuario(idUsuarioIngresar);
                        usuario.setNombre(resultset.getString("NombreUsuario"));

                        usuario.setApellidoPaterno(resultset.getString("apellidopaterno"));
                        usuario.setApellidoMaterno(resultset.getString("apellidomaterno"));
                        usuario.setTelefono(resultset.getString("telefono"));
                        usuario.setFechanacimiento(resultset.getDate("fechanacimiento"));
                        usuario.setEmail(resultset.getString("email"));
                        usuario.setSexo(resultset.getString("sexo"));
                        usuario.setCelular(resultset.getString("celular"));
                        usuario.setCurp(resultset.getString("curp"));
                        usuario.setPassword(resultset.getString("password"));
                        usuario.setUsername(resultset.getString("username"));
                        int IdDireccion = resultset.getInt("idDireccion");

                        if (IdDireccion != 0) {

                            usuario.Direcciones = new ArrayList<>();

                            Direccion direccion = new Direccion();
                            direccion.setIdDireccion(resultset.getInt("idDireccion"));
                            direccion.setCalle(resultset.getString("calle"));
                            direccion.setNumeroExterior(resultset.getString("NumeroExterior"));
                            direccion.setNumeroInterior(resultset.getString("NumeroInterior"));

                            direccion.Colonia = new Colonia();
                            direccion.Colonia.setIdColonia(resultset.getInt("idColonia"));
                            direccion.Colonia.setNombre(resultset.getString("NombreColonia"));

                            usuario.Direcciones.add(direccion);
                        }

                        result.Objects.add(usuario);

                    }
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
