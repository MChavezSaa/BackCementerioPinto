package com.backendcementeriode.pinto.controllers;


import com.backendcementeriode.pinto.models.Entity.Cliente;
import com.backendcementeriode.pinto.models.Entity.Difunto;
import com.backendcementeriode.pinto.models.Entity.Funcionario;
import com.backendcementeriode.pinto.models.Service.classImpl.DifuntoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin(origins = "*")
@RestController
public class DifuntoController {

    @Autowired
    private DifuntoServiceImpl difuntoService;

    private final Logger log = LoggerFactory.getLogger(DifuntoController.class);


    ////-------------- Listar Difuntos ---------------------////
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/listDifuntos", method = RequestMethod.GET)
    public List<Difunto> findAll(){
        List<Difunto> all = difuntoService.findAll();
        return all;
    }
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/distincDifunto", method = RequestMethod.GET)
    public List<Object> distincDifunto(){
        List<Object> all = difuntoService.difuntosNotIn();
        System.out.println(all.toString());
        return all;
    }

    ////-------------- Guardar Clientes ---------------------////
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/saveDifunto")
    @ResponseStatus(value = CREATED)
    public ResponseEntity<?> create(@RequestBody Difunto difunto){
        difunto.setEstadoDifunto("Activo");
        Difunto difunto1= null;
        Map<String,Object> response =new HashMap<String, Object>();

        try {
            difunto1=difuntoService.save(difunto);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al realizar el insert en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El difunto ha sido creado con éxito!");
        response.put("Cliente",difunto1);

        return new ResponseEntity<Map<String,Object>>(response, OK);


    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findDifunto/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        Difunto difunto=null;
        Map<String,Object> response =new HashMap<String, Object>();  //Map para guardar los mensajes de error y enviarlos, Map es la interfaz y HashMap es la implementacion

        try {                                      //se maneja el error de manera mas completa con try catch, en caso de que no pueda acceder a la base de datos
            difunto=difuntoService.findById(id);
        }catch(DataAccessException e){
            response.put("mensaje","Error al realizar la consulta en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR); //el tipo de error es porque se produce en la base de datos y no es not_found
        }

        if(difunto==null) {
            response.put("mensaje","El difunto con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(difunto,HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value ="/updateDifunto/{id}")
    public ResponseEntity<?> update(@RequestBody Difunto difunto, @PathVariable Long id) {
        Difunto difuntoActual=difuntoService.findById(id);
        Difunto difuntoUpdated=null;

        Map<String,Object> response =new HashMap<String, Object>();

        if(difuntoActual==null) {
            response.put("mensaje","No se pudo editar, el difunto con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try {

            difuntoActual.setRut_Difunto(difunto.getRut_Difunto());
            difuntoActual.setNombres_Difunto(difunto.getNombres_Difunto());
            difuntoActual.setApellidoP_Difunto(difunto.getApellidoP_Difunto());
            difuntoActual.setApellidoM_Difunto(difunto.getApellidoM_Difunto());
            difuntoActual.setGenero_Difunto(difunto.getGenero_Difunto());
            difuntoActual.setFecha_Nacimiento_Difunto(difunto.getFecha_Nacimiento_Difunto());
            difuntoActual.setFecha_Defuncion(difunto.getFecha_Defuncion());
            difuntoActual.setFecha_Inscripcion_Difunto(difunto.getFecha_Inscripcion_Difunto());
            difuntoActual.setFecha_Entierro(difunto.getFecha_Entierro());
            difuntoActual.setNombreC_Padre(difunto.getNombreC_Padre());
            difuntoActual.setNombreC_Madre(difunto.getNombreC_Madre());

            difuntoActual.setSacramento1(difunto.isSacramento1());
            difuntoActual.setSacramento2(difunto.isSacramento2());
            difuntoActual.setSacramento3(difunto.isSacramento3());
            difuntoActual.setSacramento4(difunto.isSacramento4());

            difuntoActual.setCertificado_Defuncion(difunto.isCertificado_Defuncion());
            difuntoActual.setFotocopia_Carnet(difunto.isFotocopia_Carnet());


            difuntoUpdated = difuntoService.save(difuntoActual);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al actualizar el difunto en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El difunto ha sido actualizado con éxito!");
        response.put("difunto",difuntoUpdated);

        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/DeleteDifunto/{id}",  method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Map<String,Object> response =new HashMap<String, Object>();
        try {
            difuntoService.deletebyID(id);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al reduccir el difunto de la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El difunto fue reduccido con éxito!");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }

}

////-------------- Subir Certificado Defuncion ---------------------////
    /*@PostMapping("/DifuntoUpload")
    public ResponseEntity<?>upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) throws IOException {
        Map<String, Object> response = new HashMap<>();
        Difunto difunto = difuntoService.findById(id);

        if(archivo.isEmpty()){
            String nombreArchivo = UUID.randomUUID()+"_"+archivo.getOriginalFilename().replace(" ","" );

            Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();

            log.info(rutaArchivo.toString());

            try{
                Files.copy(archivo.getInputStream(), rutaArchivo );
            }catch (Exception e){
                response.put("Mennsaje", "Error al subir el certificado");
                response.put("error",e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String nombreArchivoanterior= difunto.getCertificado_Defuncion();

            if(nombreArchivoanterior != null && nombreArchivoanterior.length()>0 ){
                Path rutaArchivoAnterior = Paths.get("uploads").resolve(nombreArchivoanterior).toAbsolutePath();
                File archivoAnterior = rutaArchivoAnterior.toFile();
                if (archivoAnterior.exists() &&  archivoAnterior.canRead()){
                    archivoAnterior.delete();
                }
            }

            difunto.setCertificado_Defuncion(nombreArchivo);
            difuntoService.save(difunto);
            response.put("Difunto", difunto);
            response.put("Mennsaje", "Has subido correctamente el certificado "+ nombreArchivo);
        }
        return new ResponseEntity<Map<String,Object>>(response, CREATED);
    }

    @GetMapping("/uploads/img/{nombreArchivo :.+}")
    public ResponseEntity<Resource> verArchivo(@PathVariable String nombreArchivo) throws MalformedURLException {

        Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
        Resource recurso = null;

        log.info(rutaArchivo.toString());

        try {
            recurso = new UrlResource(rutaArchivo.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (!recurso.exists() && recurso.isReadable()) {
            throw new RuntimeException("No se pudo cargar el archivo "+ nombreArchivo);
        }
        HttpHeaders cabecera = new HttpHeaders();
        //forzar descarga de archivo
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ recurso.getFilename()+"\"");


        return new ResponseEntity<Resource> (recurso, cabecera, HttpStatus.OK);
    }*/


