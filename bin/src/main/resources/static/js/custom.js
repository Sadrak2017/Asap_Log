$( document ).ready(function() {
  
	$("#inputSearch").keyup(function() {
		
	});
	
	$("#update").click(function(event){
		$(this).val("EDITAR");
	});
	
	$("#delete").click(function(event){
		$(this).val("EXCLUIR");
	});
	
	$("#deleteClient").click(function(event){
	    event.preventDefault();
	    ajaxGet();
	});
	  
	  
	// DO GET
	function ajaxGet(){
	    $.ajax({
	      type : "GET",
	      url : window.location + "/../buscarTodos",
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
	
	$('#selectClient').change(
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
		        setVal(result.data, "_r");
		    }); 
		}
	);

	
})
	
	
	function setVal(data, aux){
	  $("#"+aux+"name").val(data.name).trigger('change');
	  $("#"+aux+"city").val(data.city).trigger('change');
	  $("#"+aux+"uf").val(data.uf).trigger('change');
	  $("#"+aux+"cpf").val(data.cpf).trigger('change');
	  $("#"+aux+"contrato").val("SEM CONTRATO ");
	}
	
	
