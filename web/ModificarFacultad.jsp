<%-- 
    Document   : crearSolicitud
    Created on : 21/04/2016, 11:46:47 PM
    Author     : Mi Laptop
--%>


<%@page import="com.repositoriounia.modelo.Facultad"%>
<%@page import="com.repositoriounia.dao.FacultadDAO"%>
<%@page import="com.repositoriounia.dao.FacultadDAOFactory"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <%
        FacultadDAOFactory fabricate=new FacultadDAOFactory();
       FacultadDAO daote=fabricate.metodoDAO();
        int idArea=Integer.parseInt(request.getParameter("codigo"));
       Facultad facu=daote.leerxid(idArea);
        
    %>
    <body>
        
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
            </button>
            <h4 class="modal-title" id="myModalLabel">Facultad N° <%=facu.getIdFacultad() %> </h4>

        </div>
        <div class="modal-body">     

            <form class="form-horizontal form-label-left input_mask" id="modificaform" enctype="multipart/form-data">

                <input type="hidden" name="idFacultad" value="<%=facu.getIdFacultad() %>" />



                <div class="col-md-12 col-sm-12 col-xs-12 form-group has-feedback">
                    <input type="text" class="form-control has-feedback-left" readonly="readonly" value="Area de Investigacion">
                    <span class="fa fa-user form-control-feedback left" aria-hidden="true"></span>
                </div>                               

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Descripcion</label>
                    <div class="col-md-9 col-sm-9 col-xs-12">
                        <input name="descripcion" type="text" class="form-control" value="<%=facu.getDescripcion() %>">
                    </div>
                </div>

               

                <div class="ln_solid"></div>
            </form>                          
        </div>
      <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
          <button type="button" class="btn btn-primary" id="btnguardar" >Guardar Cambios</button>
      </div>
    </body>
  <!--LIBRERIAS NECESARIAS PARA EL SCRIPT*-->
    <script src="js/jquery.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
    <!-------------------------------------------------------------------->
    <script type="text/javascript">
        $(document).ready( function (){   
            
        });

        $('#btnguardar').click(function(){        
            var formdata=new FormData($("#modificaform")[0]);
            $.ajax({
                url:"FacultadController?accion=modificarFacultad",
                type:"post",
                contentType:false,
                data:formdata,
                processData:false,
                cache:false})
            .always(function(){
                actualizar();
                ocultarmodal();
                alerta("Facultad modificada",true);
            });  
        });
    </script>
    
</html>

        

