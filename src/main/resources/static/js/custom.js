$( document ).ready(function() {
	$("#alertRecisao").css("display", "none");
	function closeAlert(noneOrBlock){
	  $("#alertRecisao").css("display", noneOrBlock);
	}
	
	$("#update").click(function(event){
		$("#_contrato").val("EDITAR");
	});
	
	$("#delete").click(function(event){
		if($("#_contrato").val()>0){
			closeAlert("block");
		    event.preventDefault(); // cancela operação
		}else{
		   $("#_contrato").val("EXCLUIR");
		   closeAlert("none");
		}
	});
	
	$("#deleteClient").click(function(event){
	    event.preventDefault();
	    ajaxGet();
	});
	
	function ajaxDelete() {
	  $.getJSON(window.location + "/../excluir/"+ $('#selectClient').val(), {
	    }, function(result) {
	    	if(result.status !=  null){
	    		location.reload();
	    	}
	    }); 
	}
	  
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
		    	closeAlert("none");
		    	ativos = 0;
		    	index = 0;
		        setVal(result.data, "_");
		        if(result.inner != "undefined") {
		          $('#contrato').empty();
		      	  $("#_contrato").val("");
		          $.each(result.inner, function(i, html){
		        	$("#lbContratos").text("("+result.inner.length+") Contratos ");
		        	html += '<option value="' + html.id + '" >'
		        	   + 'Cód.: '+ html.nrApolice.toUpperCase()
		        	   + ' - '
		        	   + html.status
		        	   + ' -  Veículo: '
		        	   + html.placa.toUpperCase() 
		        	   +'</option>';
		            $('#contrato').append(html);
		        	if(result.inner[index].inativo === false){
			          ativos++;
			        }
			        $("#_contrato").val(ativos);
			          index++;
		          });	  
		        }else{
		          $("#contrato").html("<option>Nenhuma apólice relacionado ao cliente!</option>");
		        }
		    }); 
		}
	);
	
	$('#findCliente').click(function(event){
	    event.preventDefault();
	    $.ajax({
	      type : "GET",
	      url : window.location + "/../ilike/" + new String($('#inputSearch').val())+" ",
	      success: function(result){
	        $('#inputSearch').val("");
	        $('#_rSelectClient').empty();
	        if(result.data.length > 1){
	           $.each(result.data, function(i, info){
	        	  info += '<option value="' + info.id + '" >'
	        	    + info.name + ' -  CPF:'+ info.cpf + '</option>';
	              $('#_rSelectClient').append(info);
	           });
	        }
	        setDados(result.data[0]);	         
	       } 
	    });  
	});
	
	$('#_rSelectClient').change(
		function() {
		  $("#resultado").text("");
		  $.getJSON(window.location + "/../buscar/"+ $(this).val(), {
		    }, function(result) {
		      setDados(result.data);
	      });
		}
	);

	$("#cpf, #_cpf").inputmask({
		  mask: ['999.999.999-99'],
		  keepStatic: true
	});
	
})

function setDados(data){
  $("#resultado").text(
	"Nome:   " + data.name + "\n " +
	"CPF:    " + data.cpf  + "\n " +
	"Cidade: " + data.city + "\n " +
	"Placa:  " + data.placa + "\n " +
	"UF:     " + data.uf
  );
}

function setVal(data, aux){
  $("#"+aux+"name").val(data.name).trigger('change');
  $("#"+aux+"city").val(data.city).trigger('change');
  $("#"+aux+"uf").val(data.uf).trigger('change');
  $("#"+aux+"cpf").val(data.cpf).trigger('change');
  $("#"+aux+"contrato").val("SEM CONTRATO ");
}
