package JHeras.ProgramacionNCapasNoviembre2025.Controller;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.ColoniaDAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.DAO.DireccionDAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.DAO.EstadoDAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.DAO.MunicipioDAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.DAO.PaisDAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.DAO.RolDAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.DAO.UsuarioDAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.DAO.UsuarioJPADAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Direccion;
import JHeras.ProgramacionNCapasNoviembre2025.ML.ErrorCarga;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Estado;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Result;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Rol;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Usuario;
import JHeras.ProgramacionNCapasNoviembre2025.Service.ValidationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;

    @Autowired
    private RolDAOImplementation RolDAOImplementation;

    @Autowired
    private PaisDAOImplementation paisDAOImplementation;

    @Autowired
    private EstadoDAOImplementation EstadoDAOImplementation;

    @Autowired
    private MunicipioDAOImplementation MunicipioDAOImplementation;

    @Autowired
    private ColoniaDAOImplementation ColoniaDAOImplementation;

    @Autowired
    private DireccionDAOImplementation DireccionDAOImplementation;

    @Autowired
    private ValidationService validatorService;

    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;

    @GetMapping
    public String GetAll(Model model) {

        Result result = usuarioJPADAOImplementation.getall();

        // Result result = usuarioDAOImplementation.GetAll();
        model.addAttribute("Usuarios", result.Objects);
        model.addAttribute("usuarioBusqueda", new Usuario());
        model.addAttribute("Roles", RolDAOImplementation.getAll().Objects);
        return "Usuario";
    }

    @GetMapping("form")
    public String Form(Model model) {

        Result result = RolDAOImplementation.getAll();
        model.addAttribute("Roles", result.Objects);

        Result Presult = paisDAOImplementation.getAll();
        model.addAttribute("Paises", Presult.Objects);

        Usuario usuario = new Usuario();
        usuario.Direcciones = new ArrayList<>();
        usuario.Direcciones.add(new Direccion());

        model.addAttribute("Usuario", usuario);
        return "FormUsu";
    }

    @PostMapping("add")
    public String saveOrUpdate(@Valid @ModelAttribute("Usuario") Usuario usuario,
            BindingResult bindingResult, Model model) {

//        if (bindingResult.hasErrors()) {
//            model.addAttribute("Usuario", usuario);
//            return "FormUsu"; // Vuelve al formulario si hay errores
//        }

        ModelMapper modelMapper = new ModelMapper();
        JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario usuarioJPA
                = modelMapper.map(usuario, JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario.class);

        Result result;
        if (usuarioJPA.getIdUsuario()== 0) {
            // Si el id es 0, agregamos un nuevo usuario
            result = usuarioJPADAOImplementation.add(usuarioJPA);
        } else {
            // Si el id ya existe, hacemos update
            result = usuarioJPADAOImplementation.edit(usuario);
        }

        // Aquí puedes manejar el resultado si quieres mostrar mensajes
        model.addAttribute("result", result);

        return "redirect:/usuario"; 
    }

    @GetMapping("detail/{IdUsuario}")
    public String Detail(@PathVariable("IdUsuario") int IdUsuario, Model model) {

        Result result = usuarioDAOImplementation.getById(IdUsuario);
        //model.addAttribute("usuario",result.Objects);
        Usuario usuario = (Usuario) result.Objects.get(0);
        model.addAttribute("usuario", usuario);
        return "UsuarioDetail";
    }

    @GetMapping("delete/{IdUsuario}")
    public String Delete(@PathVariable("IdUsuario") int IdUsuario, RedirectAttributes redirectAttributes) {
        //Result resultDelete = usuarioDAOImplementation.DeleteById(IdUsuario);
        Result resultDelete = new Result();
        resultDelete.Correct = true;

        if (resultDelete.Correct) {
            resultDelete.object = "El usuario " + IdUsuario + " se elimino de forma correcta";
        } else {
            resultDelete.object = "No fue posible eliminar";
        }

        redirectAttributes.addFlashAttribute("resultDelete", resultDelete);
        return "redirect:/usuario";
    }

//    @GetMapping("deleteDireccion/{IdDireccion}")
//    public String DeleteDireccion(@PathVariable("IdDireccion") int IdDireccion, @PathVariable("IdUsuario") int IdUsuario, RedirectAttributes redirectAttributes) {
//        //Result resultDelete = usuarioDAOImplementation.DeleteById(IdUsuario);
//        Result resultDelete = new Result();
//        resultDelete.Correct = true;
//
//        if (resultDelete.Correct) {
//            resultDelete.object = "El usuario " + IdDireccion + " se elimino de forma correcta";
//        } else {
//            resultDelete.object = "No fue posible eliminar";
//        }
//
//        redirectAttributes.addFlashAttribute("resultDelete", resultDelete);
//        return "redirect:/usuario/detail/"+IdUsuario;
//    }
    @GetMapping("getEstadosByPais/{idPais}")
    @ResponseBody
    public Result EstadosByPais(@PathVariable("idPais") int idPais) {
        Result resultEstados = EstadoDAOImplementation.getById(idPais);
        return resultEstados;

    }

    @GetMapping("getMunicipioByEstado/{idEstado}")
    @ResponseBody
    public Result MunicipioByPais(@PathVariable("idEstado") int idEstado) {
        Result resultMunicipios = MunicipioDAOImplementation.getMunByID(idEstado);
        return resultMunicipios;
    }

    @GetMapping("getColoniaByMunicipio/{idMunicipio}")
    @ResponseBody
    public Result ColoniaByMunicipio(@PathVariable("idMunicipio") int idMunicipio) {
        Result resultMunicipios = ColoniaDAOImplementation.getById(idMunicipio);
        return resultMunicipios;
    }

    @GetMapping("/formEditable")
    public String Form(@RequestParam("idUsuario") int idUsuario, @RequestParam(required = false) Integer IdDireccion, Model model) {
        if (IdDireccion == null) { // editar usuario
            Result result = usuarioDAOImplementation.GetByIDU(idUsuario);

            Result resultRoles = RolDAOImplementation.getAll();
            model.addAttribute("Roles", resultRoles.Objects);

            Usuario usuario = (Usuario) result.object;
            usuario.Direcciones = new ArrayList<>();
            usuario.Direcciones.add(new Direccion());
            usuario.Direcciones.get(0).setIdDireccion(-1);

            model.addAttribute("Usuario", result.object);

            return "FormUsu";
        } else if (IdDireccion == 0) { //Aregar direccion
            //Formulario de direccion sin datos
            Result result = usuarioDAOImplementation.GetByIDU(idUsuario);

            model.addAttribute("Paises", paisDAOImplementation.getAll().Objects);

            Usuario usuario = (Usuario) result.object;
            if (usuario.Direcciones == null) {
                usuario.Direcciones = new ArrayList<>();
                Direccion direccion = new Direccion();
                direccion.setIdDireccion(0);
                usuario.Direcciones.add(direccion);
            }

            model.addAttribute("Usuario", result.object);

            return "FormUsu";
        } else {// Editar Direccion
            //Retornar formulario direccion con datos
            //Result result = DireccionDAOImplementation.GetByIDUD(IdDireccion, idUsuario);
//            Result result = DireccionDAOImplementation.getbyIDD(IdDireccion);
//            
//            Direccion direccion = (Direccion) result.object;
//
//            Usuario usuario = new Usuario();
//            usuario.setIdUsuario(idUsuario);
//
//            usuario.Direcciones = new ArrayList<>();
//            usuario.Direcciones.add(direccion);
//            
//            model.addAttribute("Paises", paisDAOImplementation.getAll().Objects);
//            model.addAttribute("Usuario", result.object);

            Result result = DireccionDAOImplementation.GetByIDUD(IdDireccion, idUsuario);
            model.addAttribute("Paises", paisDAOImplementation.getAll().Objects);
            model.addAttribute("Usuario", result.object);

            return "FormUsu";
        }
    }

    @PostMapping("/formEditable")
    public String Form(@ModelAttribute Usuario usuario, Model model) {

        if (usuario.getIdUsuario() == 0) {
            ModelMapper modelMapper = new ModelMapper();

            JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario usuarioJPA = modelMapper.map(usuario, JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario.class);

            Result resultadd = usuarioJPADAOImplementation.add(usuarioJPA);

        } else if (usuario.Direcciones.get(0).getIdDireccion() == -1) {
            //Actualizar usuario //probar sp
            //usuarioDAOImplementation.UpdateUsuarioid(usuario);
            int idusuario = usuario.getIdUsuario();
            //         Result result = usuarioJPADAOImplementation.edit(usuario);

            return "redirect:/usuario/Detail/" + idusuario;

        } else if (usuario.Direcciones.get(0).getIdDireccion() == 0) {
            //Añadir Direccion //agregar sp

        } else {
            //Actualizar Direccion //probar sp
            //         Result result = usuarioJPADAOImplementation.edit(usuario);
        }
        return null;

    }

    @GetMapping("CargaMasiva")
    public String CargaMasiva() {
        return "CargaMasiva";
    }

    @PostMapping("CargaMasiva")
    public String CargaMasiva(@ModelAttribute MultipartFile archivo, Model model, HttpSession session) throws IOException {
        String extencion = archivo.getOriginalFilename().split("\\.")[1];

        String path = System.getProperty("user.dir");
        String pathArchivo = "src\\main\\resources\\archivos";
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        String rutaabsoluta = path + "/" + pathArchivo + "/" + fecha + archivo.getOriginalFilename();

        archivo.transferTo(new File(rutaabsoluta));
//        Path rutaPath = Paths.get(rutaabsoluta);
//
//        Files.createDirectories(rutaPath.getParent());
//
//        Files.copy(archivo.getInputStream(), rutaPath, StandardCopyOption.REPLACE_EXISTING);

//        try {
//            String content = Files.readString(Paths.get(rutaabsoluta));
//            System.out.println(content); // Process the entire content
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        List<Usuario> usuarios = new ArrayList<>();

        if (extencion.equals("txt")) {
            //usuarios = LecturaArchivo(archivo);
            //usuarios=Files.readString(Paths.get(rutaabsoluta));
            //usuarios=LecturaArchivo(content);
            usuarios = LecturaArchivo(new File(rutaabsoluta));
            session.setAttribute("archivoCargaMasiva", rutaabsoluta);

        } else {

            //usuarios = LecturaArchivoExcel(archivo);
            usuarios = LecturaArchivoExcel(new File(rutaabsoluta));
            session.setAttribute("archivoCargaMasiva", rutaabsoluta);

        }

        List<ErrorCarga> errores = ValidarDatos(usuarios);

        if (errores != null || errores.isEmpty()) {
            //Retornar vista sin errores
            //model.addAttribute("Iserror", false);
            model.addAttribute("listaErrores", errores);

        } else {
            //retornar la lista de errores a la vista
            //model.addAttribute("Iserror",true);
            model.addAttribute("listaErrores", errores);

        }

        return "CargaMasiva";

    }

//    public List<Usuario> LecturaArchivoExcel(MultipartFile archivo) {
    public List<Usuario> LecturaArchivoExcel(File archivo) {
        List<Usuario> usuarios = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo)) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Usuario usuario = new Usuario();
                usuario.setNombre(row.getCell(0).toString());
                usuario.setApellidoPaterno(row.getCell(1).toString());
                usuario.setApellidoMaterno(row.getCell(2).toString());
                usuario.setTelefono(row.getCell(3).toString());
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

//                String date=row.getCell(4).getDateCellValue().toString();                
//                Date fecha=formato.parse(date);   
//                
//                usuario.setFechanacimiento(fecha);
//                usuario.setFechanacimiento(row.getCell(4).getLocalDateTimeCellValue().toString().split("T")[0]);
                String fecha = row.getCell(4).toString();
                Date fechaN = formato.parse(fecha);

                usuario.setFechanacimiento(fechaN);

                usuario.setUsername(row.getCell(5).toString());
                usuario.setEmail(row.getCell(6).toString());
                usuario.setSexo(row.getCell(7).toString());
                usuario.setCelular(row.getCell(8).toString());

                usuario.setCurp(row.getCell(9).toString());
                usuario.setPassword(row.getCell(10).toString());

                usuario.Rol = new Rol();
//                double idR=row.getCell(11).getNumericCellValue();
//                int valorIdR=(int) idR;
//                usuario.Rol.setIdRol(valorIdR);
                //int idR=(int) row.getCell(11).toString();
                int idr = Integer.parseInt(row.getCell(11).toString());

                usuario.Rol.setIdRol(idr);

                usuarios.add(usuario);
            }
        } catch (Exception ex) {
            System.out.println("error: " + ex);
            return usuarios;
        }

        return usuarios;

    }

//    public List<Usuario> LecturaArchivo(MultipartFile archivo) {
//    public List<Usuario> LecturaArchivo(String content){
    public List<Usuario> LecturaArchivo(File archivo) {

        List<Usuario> usuarios = new ArrayList<>();

//        try (InputStream inputStream = archivo.getInputStream(); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
//        try (BufferedReader bufferedReader = new BufferedReader(new StringReader(content))) {
        try (InputStream inputStream = new FileInputStream(archivo); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            bufferedReader.readLine();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] datos = line.split("\\|");

                Usuario usuario = new Usuario();
                usuario.setNombre(datos[0]);
                usuario.setApellidoPaterno(datos[1]);
                usuario.setApellidoMaterno(datos[2]);
                usuario.setTelefono(datos[3]);

                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date fecha = formato.parse(datos[4]);

                usuario.setFechanacimiento(fecha);

                usuario.setUsername(datos[5]);
                usuario.setEmail(datos[6]);
                usuario.setSexo(datos[7]);
                usuario.setCelular(datos[8]);
                usuario.setCurp(datos[9]);
                usuario.setPassword(datos[10]);

                usuario.Rol = new Rol();
                usuario.Rol.setIdRol(Integer.parseInt(datos[11]));

                usuarios.add(usuario);
            }

        } catch (Exception ex) {
            return usuarios;
        }

        return usuarios;
    }

    public List<ErrorCarga> ValidarDatos(List<Usuario> usuarios) {

        List<ErrorCarga> erroresCarga = new ArrayList<>();
        int LineaError = 0;

        for (Usuario usuario : usuarios) {
            List<ObjectError> errors = new ArrayList<>();
            LineaError++;

            BindingResult bindingResult = validatorService.validateObjects(usuario);
            if (bindingResult.hasErrors()) {
                errors.addAll(bindingResult.getAllErrors());
            }

            if (usuario.Rol != null) {
                BindingResult bindingRol = validatorService.validateObjects(usuario.Rol);
                if (bindingRol.hasErrors()) {
                    errors.addAll(bindingRol.getAllErrors());
                }
            }

            for (ObjectError error : errors) {
                FieldError fieldError = (FieldError) error;
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.Linea = LineaError;
                errorCarga.Campo = fieldError.getField();
                errorCarga.Descripcion = fieldError.getDefaultMessage();
                erroresCarga.add(errorCarga);
            }
        }
        return erroresCarga;
    }

    @GetMapping("/CargaMasiva/procesar")
    public String ProcesarArchivo(HttpSession sesion, RedirectAttributes redirectAttributes) {
        String path = sesion.getAttribute("archivoCargaMasiva").toString();

        File archivo = new File(path);

        String extension = archivo.getName().substring(archivo.getName().lastIndexOf('.') + 1);;

        List<Usuario> usuarios = new ArrayList<>();
        if (extension.equals("txt")) {
            usuarios = LecturaArchivo(archivo);

        } else {
            usuarios = LecturaArchivoExcel(archivo);
        }

        Result result = usuarioDAOImplementation.AddCargaMasiva(usuarios);
        if (result.Correct) {
            result.object = "Se realizo el registro";
        } else {
            result.object = "No se realizo la operación";
        }

        redirectAttributes.addFlashAttribute("result", result);

        return "redirect:/usuario/CargaMasiva";
        //sesion.removeAttribute("archivoCargaMasiva");
    }

    @PostMapping("/search")
    public String BuscarUsuarios(@ModelAttribute("UsuariosBusqueda") Usuario usuario, Model model) {

        model.addAttribute("usuarioBusqueda", new Usuario());

        model.addAttribute("Roles", RolDAOImplementation.getAll().Objects);

        model.addAttribute("Usuarios", usuarioDAOImplementation.BusquedaUsuarioDireccionAll(usuario).Objects);
        return "Usuario";
    }

}
