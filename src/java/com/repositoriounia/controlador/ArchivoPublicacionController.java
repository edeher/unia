/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.repositoriounia.controlador;

import com.repositoriounia.dao.ArchivoPublicacionDAO;
import com.repositoriounia.dao.ArchivoPublicacionDAOFactory;
import com.repositoriounia.dao.DAOException;
import com.repositoriounia.dao.VisitaDAO;
import com.repositoriounia.dao.VisitaDAOFactory;
import com.repositoriounia.modelo.ArchivoPublicacion;
import com.repositoriounia.modelo.DescripcionArchivo;
import com.repositoriounia.modelo.Publicacion;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import static org.apache.jasper.tagplugins.jstl.core.Out.output;

/**
 *
 * @author Mi Laptop
 */
@WebServlet(name = "ArchivoPublicacionController", urlPatterns = {"/ArchivoPublicacionController"})
@MultipartConfig
public class ArchivoPublicacionController extends HttpServlet {
    private ArchivoPublicacion archipu;
    private ArchivoPublicacionDAOFactory fabricate;
    private ArchivoPublicacionDAO daote;
    private PrintWriter out;
    private VisitaDAOFactory fabricate1;
    private VisitaDAO daote1;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DAOException {
  
        String accion = request.getParameter("accion");
        
        fabricate = new ArchivoPublicacionDAOFactory();
        daote = fabricate.metodoDAO();
        fabricate1=new VisitaDAOFactory();
        daote1=fabricate1.metodoDAO();
        
        System.out.println("Archivopublicacion controller");
        System.out.println("==============================");
        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            System.out.println(" enum" + enumeration.nextElement());
        }
        ArchivoPublicacion archipu = new ArchivoPublicacion();
        switch (accion) {
            case "cargarArchivo":                
                cargarArchivo(request, response);  
                out = response.getWriter();
                break;
            case "verArchivo":
                System.out.println("verArchivo.....");
                verArchivo(request, response);
                break;
            case "visitarArchivo":
                System.out.println("verArchivo.....");
                visitarArchivo(request, response);
                break;
            case "ObtenerArchivos":
                System.out.println("ObtenerArchivos.....");
                String json = ObtenerArchivos(request, response);
                response.setContentType("application/json");
                out = response.getWriter();
                out.println(json);
                break;
            case "eliminarArchivo":
                eliminarArchivo(request,response);
                break;
            case "5":
                break;
            case "6":
                break;
            case "7":
                break;
            case "8":
                break;
            case "9":
                break;
 
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DAOException ex) {
            Logger.getLogger(ArchivoPublicacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DAOException ex) {
            Logger.getLogger(ArchivoPublicacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    

    private void cargarArchivo(HttpServletRequest request, HttpServletResponse response) throws IOException, DAOException, ServletException {
        
        archipu = new ArchivoPublicacion();
        System.out.println("cargando archivo..en metodo..");
        Part filePart = request.getPart("archivo");
        //recepcion de elemento, parte o plantilla
       
        System.out.println("recepcion");
        InputStream imput = filePart.getInputStream();//Obtiene el contenido de esta parte como un InputStream
          //flujo de entrada de bytes
         // siempre debe proporcionar un método que devuelve el siguiente byte de entrada.
        System.out.println("creacion de variable");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        //flujo de salida en el que se escriben los datos en una matriz de bytes
        //crece automáticamente como se escriben datos en ella. 
        //Los datos pueden ser recuperados utilizando toByteArray () y toString () . 
        
        System.out.println("creacion de byte array");
        byte[] buffer = new byte[20777215];
        
         System.out.println("escribiendo el archivo");
        for (int length = 0; (length = imput.read(buffer)) > 0;) {
            output.write(buffer, 0, length);
            System.out.println("escribiendo el archivo");
        }        
        archipu.setArchivo(output.toByteArray());
        archipu.getPublicacion().setIdPublicacion(Integer.parseInt(request.getParameter("idpublicacion")));
        archipu.setUrlLocal(request.getParameter("urllocal"));
        archipu.setUrlWeb(request.getParameter("urlweb"));
        archipu.setDescripcion(DescripcionArchivo.valueOf(request.getParameter("descripcion"))); 
        ArchivoPublicacion archi =daote.crearleer(archipu);
        
        try (PrintWriter pw = new PrintWriter(response.getOutputStream())) {
                 if(archi==null){
                      pw.println(0); 
                 }else
                 {
                      pw.println(1); 
                 }
                 
           
        }
    }

    private void verArchivo(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException {
       
        int codigo = Integer.parseInt(request.getParameter("idArchivo"));
        
        OutputStream pdfsa;
        try (InputStream pdf = daote.ArchivoPublico(codigo)) {
            pdfsa = response.getOutputStream();
            byte[] buffer2 = new byte[20777215];
            for (;;) {
                int nbytes = pdf.read(buffer2);
                if (nbytes == -1) {
                    break;
                }
                pdfsa.write(buffer2, 0, nbytes);
            }
            response.setContentType("application/pdf");
        }
        pdfsa.flush();
        pdfsa.close();
    }

    private String ObtenerArchivos(HttpServletRequest request, HttpServletResponse response) throws IOException, DAOException {
         int idPublicacion=Integer.parseInt(request.getParameter("codigo"));
          System.out.println("codigo "+idPublicacion);
        ArchivoPublicacion[] puv = daote.leertodoidpublicacion(idPublicacion);
        
          for(ArchivoPublicacion usuv1:puv)
	  {
	      
	      System.out.println(" "+usuv1.toString());
	  
	  }
        
        JsonObjectBuilder objbuilder = Json.createObjectBuilder();  
        JsonArrayBuilder  arrayArchivoPublicacion = Json.createArrayBuilder();        
        JsonArrayBuilder  arrayDatosArchivoPublicacion; 
        
       for (ArchivoPublicacion publi : puv) {
            //System.out.println(solicitud.toString());            
            arrayDatosArchivoPublicacion = Json.createArrayBuilder();
            arrayDatosArchivoPublicacion.add(publi.getIdArchivoPublicacion());
            arrayDatosArchivoPublicacion.add(publi.getDescripcion().getNom());
            
          
            arrayArchivoPublicacion.add(arrayDatosArchivoPublicacion);
        }
        objbuilder.add("data", arrayArchivoPublicacion);
        JsonObject obj = objbuilder.build();        
        return obj.toString();
       
        
             
        
    }

    private void eliminarArchivo(HttpServletRequest request, HttpServletResponse response) throws DAOException {
       int codigo=Integer.parseInt(request.getParameter("idArchivo"));
       
       daote.eliminar(codigo);
       
       
    }

    private void visitarArchivo(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException {
       int codigo = Integer.parseInt(request.getParameter("idArchivo"));
        daote1.crear(codigo);
        OutputStream pdfsa;
        try (InputStream pdf = daote.ArchivoPublico(codigo)) {
            pdfsa = response.getOutputStream();
            byte[] buffer2 = new byte[20777215];
            for (;;) {
                int nbytes = pdf.read(buffer2);
                if (nbytes == -1) {
                    break;
                }
                pdfsa.write(buffer2, 0, nbytes);
            }
            response.setContentType("application/pdf");
        }
        pdfsa.flush();
        pdfsa.close();
    }

}
