$(document).ready(function(){
	jQuery("#placaVc").inputmask({mask: ['AAA-9999','AAA9A99']}); //suportar o novo formato de placa
	jQuery("#_placa, #placa").inputmask({mask: ['AAA-9999','AAA9A99']}); // suportar o novo formato de placa
	jQuery("#vlrApolice, #_vlrApolice").inputmask('decimal', {
         'alias': 'numeric',
         'groupSeparator': '.',
         'autoGroup': true,
         'digits': 2,
         'radixPoint': ".",
         'digitsOptional': false,
         'allowMinus': false,
         'prefix': 'R$ ',
         'placeholder': '',
          onBeforeMask: function (value, opts) {
             return value;
          },
          removeMaskOnSubmit: true,
	});
});

