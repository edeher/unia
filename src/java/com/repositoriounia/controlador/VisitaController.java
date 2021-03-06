/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.repositoriounia.controlador;

import com.repositoriounia.dao.DAOException;
import com.repositoriounia.dao.VisitaDAO;
import com.repositoriounia.dao.VisitaDAOFactory;
import com.repositoriounia.modelo.Publicacion;
import com.repositoriounia.modelo.Visita;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mi Laptop
 */
@WebServlet(name = "VisitaController", urlPatterns = {"/VisitaController"})
public class VisitaController extends HttpServlet {
    private Visita objVi;
    private VisitaDAOFactory fabricate;
    private VisitaDAO daote;

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
        response.setContentType("text/html;charset=UTF-8");
        String accion=request.getParameter("accion");
        fabricate=new VisitaDAOFactory();
        daote=fabricate.metodoDAO();
        switch(accion)
        {
            case "ObtenerTodos":
                ObtenerTodos(request, response);
                break;
            case "top5":
                top5(request, response);
                break;
            case "topxid":
                topxid(request, response);
                break;
            case "topxid2":
                topxid2(request, response);
                break;

            case "top5_ultimoxmes":
                top5_ultimoxmes(request, response);
                break;
            case "topxidxmes":
                topxidxmes(request, response);
                break;
            case "topxidxmes2":
                topxidxmes2(request, response);
                break;
            case "visitastotaxpoanio":
                visitastotaxpoanio(request, response);
                break;
            case "publicacionestotaxpoanio":
                publicacionestotaxpoanio(request, response);
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
            Logger.getLogger(VisitaController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(VisitaController.class.getName()).log(Level.SEVERE, null, ex);
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

    private void ObtenerTodos(HttpServletRequest request, HttpServletResponse response) throws IOException, DAOException {
       Visita[] visi = daote.leertodo();         
        JsonObjectBuilder objbuilder = Json.createObjectBuilder();  
        JsonArrayBuilder  arrayVisita = Json.createArrayBuilder();        
        JsonArrayBuilder  arrayDatosVisita; 
       for (Visita visi1 : visi) {
            //System.out.println(solicitud.toString());            
            arrayDatosVisita = Json.createArrayBuilder();
            arrayDatosVisita.add(visi1.getIdVisita());
            arrayDatosVisita.add(visi1.getArchivoPublicacion().getPublicacion().getTitulo());
            arrayDatosVisita.add(visi1.getArchivoPublicacion().getDescripcion().getNom());
            arrayDatosVisita.add(visi1.getFecha().toString());
           
           arrayVisita.add(arrayDatosVisita);
        }
        objbuilder.add("data",arrayVisita);
        JsonObject obj = objbuilder.build();
        response.setContentType("application/json");
       
        try (PrintWriter pw = new PrintWriter(response.getOutputStream())) {
            pw.println(obj.toString()); 
        }
    }

    private void top5(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException {
        Visita[] visi = daote.top5();
        JsonArrayBuilder  arraybuilder = Json.createArrayBuilder();  
        JsonObjectBuilder objbuilder = Json.createObjectBuilder();                 
        for (Visita visi1 : visi) {
              objbuilder.add("category",visi1.getArchivoPublicacion().getPublicacion().getTitulo().trim());
              objbuilder.add("value",visi1.getCantidad());           
              arraybuilder.add(objbuilder);           
        }
        objbuilder.add("top5",arraybuilder);
        JsonObject obj = objbuilder.build();
        response.setContentType("application/json");
       
        try (PrintWriter pw = new PrintWriter(response.getOutputStream())) {
            pw.println(obj.toString()); 
        }
    }

    private void topxid(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException {
        int idPublicacion=Integer.parseInt(request.getParameter("idPublicacion") );
        System.out.println("idPublicacion"+idPublicacion);
         Visita[] visi = daote.topxid(idPublicacion);
        JsonArrayBuilder  arraybuilder = Json.createArrayBuilder();  
        JsonObjectBuilder objbuilder = Json.createObjectBuilder();                 
        for (Visita visi1 : visi) {
              objbuilder.add("value",visi1.getCantidad());   
              objbuilder.add("name",visi1.getArchivoPublicacion().getDescripcion().getNom());
                      
              arraybuilder.add(objbuilder);           
        }
        objbuilder.add("top",arraybuilder);
        JsonObject obj = objbuilder.build();
        response.setContentType("application/json");
       
        try (PrintWriter pw = new PrintWriter(response.getOutputStream())) {
            pw.println(obj.toString()); 
        }
    }

    private void topxid2(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException {
      int idPublicacion=Integer.parseInt(request.getParameter("idPublicacion") );
        System.out.println("idPublicacion"+idPublicacion);
         Visita[] visi = daote.topxid(idPublicacion);
        JsonArrayBuilder  arraybuilder = Json.createArrayBuilder();  
        JsonObjectBuilder objbuilder = Json.createObjectBuilder();                 
        for (Visita visi1 : visi) {
              objbuilder.add("value",visi1.getCantidad());   
              objbuilder.add("name",visi1.getArchivoPublicacion().getDescripcion().name());
                      
              arraybuilder.add(objbuilder);           
        }
        objbuilder.add("top",arraybuilder);
        JsonObject obj = objbuilder.build();
        response.setContentType("application/json");
       
        try (PrintWriter pw = new PrintWriter(response.getOutputStream())) {
            pw.println(obj.toString()); 
        }
    }

    

    private void top5_ultimoxmes(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException {
         int mes=Integer.parseInt(request.getParameter("mes"));
         System.out.println("mes en servlet "+mes);
        Visita[] visi = daote.top5_ultimoxmes(mes);
        JsonArrayBuilder  arraybuilder = Json.createArrayBuilder();  
        JsonObjectBuilder objbuilder = Json.createObjectBuilder();                 
        for (Visita visi1 : visi) {
              objbuilder.add("category",visi1.getArchivoPublicacion().getPublicacion().getTitulo().trim());
              objbuilder.add("value",visi1.getCantidad());           
              arraybuilder.add(objbuilder);           
        }
        objbuilder.add("top5",arraybuilder);
        JsonObject obj = objbuilder.build();
        response.setContentType("application/json");
       
        try (PrintWriter pw = new PrintWriter(response.getOutputStream())) {
            pw.println(obj.toString()); 
        }
    }

    private void topxidxmes(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException {
       int idPublicacion=Integer.parseInt(request.getParameter("idPublicacion") );
       int mes=Integer.parseInt(request.getParameter("mes") );
        System.out.println("idPublicacion"+idPublicacion);
         Visita[] visi = daote.topxidxmes(idPublicacion, mes);
        JsonArrayBuilder  arraybuilder = Json.createArrayBuilder();  
        JsonObjectBuilder objbuilder = Json.createObjectBuilder();                 
        for (Visita visi1 : visi) {
              objbuilder.add("value",visi1.getCantidad());   
              objbuilder.add("name",visi1.getArchivoPublicacion().getDescripcion().getNom());
                      
              arraybuilder.add(objbuilder);           
        }
        objbuilder.add("top",arraybuilder);
        JsonObject obj = objbuilder.build();
        response.setContentType("application/json");
       
        try (PrintWriter pw = new PrintWriter(response.getOutputStream())) {
            pw.println(obj.toString()); 
        }
    }

    private void topxidxmes2(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException {
       int idPublicacion=Integer.parseInt(request.getParameter("idPublicacion") );
       int mes=Integer.parseInt(request.getParameter("mes") );
        System.out.println("idPublicacion"+idPublicacion);
         Visita[] visi = daote.topxidxmes(idPublicacion, mes);
        JsonArrayBuilder  arraybuilder = Json.createArrayBuilder();  
        JsonObjectBuilder objbuilder = Json.createObjectBuilder();                 
        for (Visita visi1 : visi) {
              objbuilder.add("value",visi1.getCantidad());   
              objbuilder.add("name",visi1.getArchivoPublicacion().getDescripcion().name());
                      
              arraybuilder.add(objbuilder);           
        }
        objbuilder.add("top",arraybuilder);
        JsonObject obj = objbuilder.build();
        response.setContentType("application/json");
       
        try (PrintWriter pw = new PrintWriter(response.getOutputStream())) {
            pw.println(obj.toString()); 
        } 
    }

    private void visitastotaxpoanio(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException {
        Visita[] visi = daote.visitastotalesxAnio();
        JsonArrayBuilder  arraybuilder = Json.createArrayBuilder();  
        JsonObjectBuilder objbuilder = Json.createObjectBuilder();                 
        for (Visita visi1 : visi) {
              objbuilder.add("mes",visi1.getArchivoPublicacion().getPublicacion().getTitulo().trim());
              objbuilder.add("value",visi1.getCantidad());           
              arraybuilder.add(objbuilder);           
        }
        objbuilder.add("totalxanio",arraybuilder);
        JsonObject obj = objbuilder.build();
        response.setContentType("application/json");
       
        try (PrintWriter pw = new PrintWriter(response.getOutputStream())) {
            pw.println(obj.toString()); 
        }
    }

    private void publicacionestotaxpoanio(HttpServletRequest request, HttpServletResponse response) throws DAOException, IOException {
       Visita[] visi = daote.publicacionestotalesxAnio();
        JsonArrayBuilder  arraybuilder = Json.createArrayBuilder();  
        JsonObjectBuilder objbuilder = Json.createObjectBuilder();                 
        for (Visita visi1 : visi) {
              objbuilder.add("mes",visi1.getArchivoPublicacion().getPublicacion().getTitulo().trim());
              objbuilder.add("value",visi1.getCantidad());           
              arraybuilder.add(objbuilder);           
        }
        objbuilder.add("totalxanio",arraybuilder);
        JsonObject obj = objbuilder.build();
        response.setContentType("application/json");
       
        try (PrintWriter pw = new PrintWriter(response.getOutputStream())) {
            pw.println(obj.toString()); 
        }
    }

}
