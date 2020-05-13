// Only needed for working example
var myCSV = "Species,Identifiant\r\n";
myCSV += "Species A,320439\r\n";
myCSV += "Species B,349450\r\n";
myCSV += "Species C,43435904\r\n";
myCSV += "Species D,320440\r\n";
myCSV += "Species E,349451\r\n";
myCSV += "Species F,43435905\r\n";
console.log(myCSV);

// Begin jQuery Code
$(function() {
  function processData(allText) {
    var record_num = 2; // or however many elements there are in each row
    var allTextLines = allText.split(/\r\n|\n/);
    var lines = [];
    var headings = allTextLines.shift().split(',');
    while (allTextLines.length > 0) {
      var tobj = {},
        entry;
      entry = allTextLines.shift().split(',');
      /*
      Normally we'd read the headers into the object.
      Since we will be using Autocomplete, it's looking for an array of objects with 'label' and 'value' properties.
      tobj[headings[0]] = entry[0];
      tobj[headings[1]] = entry[1];
      */
      tobj['label'] = entry[0];
      tobj['value'] = entry[1];
      lines.push(tobj);
    }
    return lines;
  }
  var lists = [];
  /*
  In your script you will get this content from the CSV File
  // Get the CSV Content
  $.get("reference.csv", function(data) {
    lists = processData(data);
  });
  */
  // For working example 
  lists = processData(myCSV);

  $("#species").autocomplete({
    minLength: 3,
    source: lists,
    focus: function(event, ui) {
      $("#species").val(ui.item.label);
      return false;
    },
    select: function(event, ui) {
      $("#species").val(ui.item.label);
      $("#identifiant").val(ui.item.value);
      return false;
    }
  });
});
