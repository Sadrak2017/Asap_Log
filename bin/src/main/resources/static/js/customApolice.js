 $(document).ready(function(){
		 $('#placaVc').mask('000-0000');
	 });

$( document ).ready(function() {

	$("#inputSearch").keyup(function() {
		
	});

	
	$("#update").click(function(event){
		$(this).val("EDITAR");
	});
	
	$("#delete").click(function(event){
		$(this).val("EXCLUIR");
	});
	
	$("#newApolice").click(function(event){
	    event.preventDefault();
	    ajaxGet();
	});
	  
	
	$("#modalUpdateDelete").click(function(event){
	    event.preventDefault();
	    ajaxGetApolices();
	});

	
	// DO GET
	function ajaxGet(){
	    $.ajax({
	      type : "GET",
	      url : "/../clientes/buscarTodos",
	      success: function(result){
	        if(result.status == "Done"){
	          $('#selectClient').empty();
	          var custList = "";
	          $.each(result.data, function(i, html){
	        	html += '<option value="' + html.id + '" >'
	        	   + html.name + ' -  CPF:'+ html.cpf + '</option>';
	            $('#selectClient').append(html)
	          });
	          console.log("Success: ", result);
	        }else{
	          $("#selectClient").html("<strong>Error</strong>");
	          console.log("Fail: ", result);
	        }
	      },
	      error : function(e) {
	        $("#selectClient").html("<strong>Error</strong>");
	        console.log("ERROR: ", e);
	      }
	   });  
	}
	
	// DO GET
	function ajaxGetApolices(){
	    $.ajax({
	      type : "GET",
	      url : "/../apolices/buscarTodos",
	      success: function(result){
	        if(result.status == "Done"){
	          $('#contratos').empty();
	          var custList = "";
	          $.each(result.data, function(i, html){
	        	  console.log(html.nrApolice)
	        	html += '<option value="' + html.id + '" >'+
	        	   '  Nr°: '+ html.nrApolice + ' - Ativo'+'</option>';
	            $('#contratos').append(html)
	          });
	          console.log("Success: ", result);
	        }else{
	          $("#contratos").html("<strong>Error</strong>");
	          console.log("Fail: ", result);
	        }
	      },
	      error : function(e) {
	        $("#contratos").html("<strong>Error</strong>");
	        console.log("ERROR: ", e);
	      }
	   });  
	}
	
	$('#contratos').change(
		function() {
		  $.getJSON(window.location + "/../buscar/"+ $(this).val(), {
		    }, function(result) {
		        setVal(result.data, "_");
		    }); 
		}
	);
	
	$('#findCliente').click(
		function(event) {
		  $.getJSON(window.location + "/../like/"+ $('#inputSearch').val(), {
		    }, function(result) {
		        setValApolice(result.data, "_r");
		    }); 
		}
	);

	
})
	function setValApolice(data, aux){
	  $("#"+aux+"name").val(data.name).trigger('change');
	  $("#"+aux+"city").val(data.city).trigger('change');
	  $("#"+aux+"uf").val(data.uf).trigger('change');
	  $("#"+aux+"dtInicio").val(data.dtInicio).trigger('change');
	  $("#"+aux+"dtFim").val(data.dtInicio).trigger('change');
	  $("#"+aux+"placa").val(data.placa).trigger('change');
	}
	
	
	function setVal(data, aux){
	  $("#"+aux+"name").val(data.name).trigger('change');
	  $("#"+aux+"city").val(data.city).trigger('change');
	  $("#"+aux+"uf").val(data.uf).trigger('change');
	  $("#"+aux+"cpf").val(data.cpf).trigger('change');
	  $("#"+aux+"contrato").val("SEM CONTRATO ");
	}
	
	
