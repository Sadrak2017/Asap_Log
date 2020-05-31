
$( document ).ready(function() {

	divRecisao = $("#CheckRecisao").css("display","none");
	alert = $('#alertRecisao').css("display","none");
	reativa = $("#reativa").css("display","none");

	$("#update").click(function(event){    
	    ativaAlert(true);
	});	
	
	$("#delete").click(function(event){		
		if ($("#_inativoCheck").prop("checked")) {
	      ativaAlert(true);
	      $("#_inativo").val("0");
	    } else {
	      $("#_inativo").val("1");
	      ativaAlert(false);
	      $("#_inativoCheck").attr('type', 'checkbox');
	      event.preventDefault();	
	    }
	});
	

	$("#newApolice, #modalSearch").click(function(event){
	    event.preventDefault();
	    ajaxGet();
	});
	  
	
	$("#modalUpdateDelete, #modalSearch").click(function(event){
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
	          $('#selectClient, #_selectClient').empty();
	          var custList = "";
	          $.each(result.data, function(i, html){
	        	html += '<option value="' + html.id + '" >'
	        	   + html.name + ' -  CPF:'+ html.cpf + '</option>';
	            $('#selectClient, #_selectClient').append(html)
	          });
	        }else{
	          $("#selectClient, #_selectClient").html("<strong>Error</strong>");
	        }
	      },
	      error : function(e) {
	        $("#selectClient, #_selectClient").html("<strong>Error</strong>");
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
	          var status = "Ativo";
	          $.each(result.data, function(i, html){
	        	  if(html.inativo === true){
	        		  status = "INATIVO";
	        	  }else{
	        		  status = "ATIVO";
	        	  }
	        	  html += '<option value="' + html.id + '" >'+
	        	   '  Cód.: '+ html.nrApolice.toUpperCase() + 
	        	   ' - '+
	        	   status+
	        	   ' ('+html.dtInicio+')'+
	        	   '</option>';
	            $('#contratos').append(html)
	          });
	        }else{
	          $("#contratos").html("<strong>Error</strong>");
	        }
	      },
	      error : function(e) {
	        $("#contratos").html("<strong>Error</strong>");
	      }
	   });  
	}
	
	$('#_selectClient').change(
		function() {
	      $('#resultado').val("");
		  $.getJSON(window.location + "/../consulta/"+$(this).val(), {
		    }, function(result) {
		      setDadoscontrato(result);
		      id = result.data[0].id; 
		      if(result.data.length == 1){
		    	  $.getJSON(window.location + "/../consultaContrato/"+id, {
				    }, function(result) {
				  	  let valor = result.data.vlrApolice.toLocaleString("pt-BR", { style: "currency" , currency:"BRL"}); 
			    	  let string = "Valor do contrato: " + valor + "\n" +
						 "Placa do veículo: " + result.data.placa+ "\n" +
						 "Início do contrato: " + result.data.dtInicio + " Fim: "+result.data.dtFim + "\n" +
						 "Status atual: "+result.data.status;
			    	  $('#resultado').val(string);	    	  
			      });
		      }
	      });
		}
	);
	
	$('#_contratos').change(
		function() {
		  $('#resultado').val("");
		  $.getJSON(window.location + "/../consultaContrato/"+$(this).val(), {
		    }, function(result) {
		  	  let valor = result.data.vlrApolice.toLocaleString("pt-BR", { style: "currency" , currency:"BRL"}); 
	    	  let string = "Valor do contrato: " + valor + "\n" +
				 "Placa do veículo: " + result.data.placa+ "\n" +
				 "Início do contrato: " + result.data.dtInicio + " Fim: "+result.data.dtFim + "\n" +
				 "Status atual: "+result.data.status;
	    	  $('#resultado').val(string);	    	  
	      });
		}
	);
		
	function setDadoscontrato(result){
	    if(result.status == "Done"){
	      $('#_contratos').empty();
	      var status = "";
	      $("#lbContratos").text("("+result.data.length +") Contratos encontratos");
	      $.each(result.data, function(i, html){
	    	 if(html.inativo === true){
	    	   status = "INATIVO";
	    	 }else{
	    	   status = "ATIVO";
	    	 }
	    	html += '<option value="' + html.id + '" >'+
	    	   '  Cód.: '+ html.nrApolice.toUpperCase() + 
	    	   ' - '+
	    	   status+
	    	   ' ('+html.dtInicio+')'+
	    	   '</option>';
	        $('#_contratos').append(html)
	      });
	    }else{
	      $("#_contratos").html("<strong>Error</strong>");
	    }
    }
	
	$('#contratos').change(
		function() {	
		  $.getJSON(window.location + "/../buscar/"+ $(this).val(), {
		    }, function(result) {
		        setVal(result.data, result.inner, "_");
		        validaButton(result.data.inativo);
		    });
			ativaAlert(true);
		}
	);
	
	$('#findCliente').click(
		function(event) {
		  $.getJSON(window.location + "/../like/"+ $('#inputSearch').val(), {
		    }, function(result) {
		        setValApolice(result.data, "_r");
		    });
	        ativaAlert(true);
		}
	);

	function validaButton(habilita){
     if(habilita){		
		$("#update").css("display","none");
		$("#delete").css("display","none");
		$("#reativa").css("display","block");
		 $("#_inativo").val("1");
     }else{
    	$("#update").css("display","block");
 		$("#delete").css("display","block");
 		$("#reativa").css("display","none");
     }	
	}	

	function ativaAlert(ValidaRecisao, ContratoNull){
		if(ValidaRecisao){
		  divRecisao = $("#CheckRecisao").css("display","none");
		  alert = $('#alertRecisao').css("display","none");
		}else{
		  divRecisao = $("#CheckRecisao").css("display","block");
	      alert = $('#alertRecisao').css("display","block");
		}
		
	}
})

function setValApolice(data, aux){
  $("#"+aux+"name").val(data.name).trigger('change');
  $("#"+aux+"city").val(data.city).trigger('change');
  $("#"+aux+"uf").val(data.uf).trigger('change');
  $("#"+aux+"dtInicio").val(data.dtInicio).trigger('change');
  $("#"+aux+"dtFim").val(data.dtInicio).trigger('change');
  $("#"+aux+"placa").val(data.placa).trigger('change');
}


function setVal(contrato, cliente, aux){
  $("#"+aux+"name").val(cliente.name).trigger('change');
  $("#"+aux+"uf").val(cliente.uf).trigger('change');
  $("#"+aux+"vlrApolice").val(contrato.vlrApolice);
  $("#"+aux+"dtIncio").val(contrato.dtInicio);
  $("#"+aux+"dtFim").val(contrato.dtFim);
  $("#"+aux+"placa").val(contrato.placa);
  $("#"+aux+"status").val(contrato.status);
}
